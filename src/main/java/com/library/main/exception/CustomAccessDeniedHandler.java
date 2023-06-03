package com.library.main.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication()!=null){
//            throw new ValidationException(List.of(new ErrorVO("denied","access_denied")));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(new ErrorVO("DENIED","ACCESS_DENIED").toString());
        }

    }
}
