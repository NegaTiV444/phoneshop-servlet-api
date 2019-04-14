package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.dosProtection.DosFilterService;
import com.es.phoneshop.model.dosProtection.DosFilterServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosIpFilter implements Filter {

    private DosFilterService dosFilterService = DosFilterServiceImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosFilterService.isIpAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(429);
        }
    }

    @Override
    public void destroy() {
    }
}
