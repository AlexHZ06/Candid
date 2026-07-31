package com.nea.candid.api.security.filters;

import com.nea.candid.data.dataObjects.JwtObject;
import com.nea.candid.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(2)
@Component
public class roleFilter extends OncePerRequestFilter {

    public final JwtService jwtService;

    public roleFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("auth");
        JwtObject jwtObject = jwtService.decodeJwt(jwt);

        if(request.getRequestURI().contains("public")){

            filterChain.doFilter(request,response);
            return;

        }

        if(request.getRequestURI().contains("client")){

            if(jwtObject.getClaims().get("role").equals("client")){

                filterChain.doFilter(request,response);
                return;

            }
            else {response.sendError(401); return;}

        }
        else if(request.getRequestURI().contains("photogrpaher")){

            if(jwtObject.getClaims().get("role").equals("photogrpaher")){

                filterChain.doFilter(request,response);
                return;

            }
            else {response.sendError(401); return;}

        }

        filterChain.doFilter(request,response);
        return;

    }
}
