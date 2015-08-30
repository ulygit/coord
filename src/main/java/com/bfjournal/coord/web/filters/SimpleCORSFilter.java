package com.bfjournal.coord.web.filters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;

/**
 * Adding Cross-Origin Resource Sharing (CORS) filter to the application
 * Created by grant on 8/12/15.
 */
@ConfigurationProperties(prefix = "filter.cors")
@Component
public class SimpleCORSFilter implements Filter {

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    private List<String> allowedOrigins = asList("*");
    private List<String> allowedMethods = asList("POST", "GET", "OPTIONS", "DELETE");
    private int maxAge = 3600;
    private List<String> allowedHeaders = asList("Origin", "X-Requested-With", "Content-Type", "Accept");

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", join(", ", allowedOrigins));
        response.setHeader("Access-Control-Allow-Methods", join(", ", allowedMethods));
        response.setHeader("Access-Control-Max-Age", valueOf(maxAge));
        response.setHeader("Access-Control-Allow-Headers", join(", ", allowedHeaders));
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }
}
