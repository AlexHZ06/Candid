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

@Component
@Order(1)
public class jwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public jwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("auth");
        if(jwt == null){

            if(request.getRequestURI().contains("public")){

                filterChain.doFilter(request,response);
                return;

            }
            else{

                response.sendError(401);
                return;

            }

        }
        else{

            JwtObject jwtObject = jwtService.decodeJwt(jwt);

            if((jwtObject.getExpired() && !jwtObject.getInvalid()) && request.getRequestURI().contains("public")){

                request.setAttribute("userId", jwtObject.getClaims().getSubject());
                filterChain.doFilter(request,response);
                return;

            }
            else{

                if(!jwtObject.getInvalid() && !jwtObject.getExpired()){

                    request.setAttribute("userId", jwtObject.getClaims().getSubject());
                    filterChain.doFilter(request,response);
                    return;

                }
                else{

                    response.sendError(401);

                }

            }

        }

    }
}
