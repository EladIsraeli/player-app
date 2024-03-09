package com.example.playersapp.common.health;

import com.example.playersapp.utils.health.ApplicationReadinessService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Order(1)
public class ReadinessFilter implements Filter {

    private final ApplicationReadinessService readinessService;

    @Autowired
    public ReadinessFilter(ApplicationReadinessService readinessService) {
        this.readinessService = readinessService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!readinessService.isReady()) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().write("Application is initializing, please retry later.");
            return;
        }
        chain.doFilter(request, response);
    }
}
