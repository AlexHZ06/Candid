package com.nea.candid.api.security.filters;

import com.nea.candid.data.dto.JwtJwsBody;
import com.nea.candid.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
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

        String targetUrl = request.getRequestURI();
        String jwt = request.getHeader("auth");
        JwtJwsBody jwtJwsBody = jwtService.decodeJwt(jwt);

        if(targetUrl.contains("client")){

            if(jwtJwsBody.getClaims().get("role").equals("client")) {
                filterChain.doFilter(request, response);
                return;
            }
            else {
                response.sendError(401);
                return;
            }

        }
        else if(targetUrl.contains("photographer")){

            if(jwtJwsBody.getClaims().get("role").equals("photographer")) {
                filterChain.doFilter(request, response);
                return;
            }
            else {
                response.sendError(401);
                return;
            }

        }

        filterChain.doFilter(request, response);

    }
}
