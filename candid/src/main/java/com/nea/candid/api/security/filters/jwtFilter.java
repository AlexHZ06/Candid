package com.nea.candid.api.security.filters;

import com.nea.candid.data.dto.JwtJwsBody;
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

        String Url = request.getRequestURI();

        if(Url.contains("public")){

            filterChain.doFilter(request,response);
            return;

        }

        String jwt = request.getHeader("auth");
        JwtJwsBody jwtJwsBody = jwtService.decodeJwt(jwt);

        if(!jwtJwsBody.getInvalid() && !jwtJwsBody.getExpired()) {

            filterChain.doFilter(request, response);

        }
        else {

            response.sendError(401);

        }

    }
}
