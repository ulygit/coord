package com.bfjournal.coord.web.filters;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * A once-per-request filter that logs request details on arrival and after processing.
 * <p>
 * Created by grant on 8/19/15.
 */
@ConfigurationProperties(prefix = "filter.log")
@Component
public class RequestLogFilter extends AbstractRequestLoggingFilter {

    public RequestLogFilter() {
        super();
        setIncludeQueryString(true);
        setIncludeClientInfo(true);
        setIncludePayload(true);
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }

    /**
     * Writes a log message before the request is processed.
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.debug(message);
    }

    /**
     * Writes a log message after the request is processed.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.debug(message);
    }

    @Override
    public void setIncludeQueryString(boolean includeQueryString) {
        super.setIncludeQueryString(includeQueryString);
    }

    @Override
    public void setIncludeClientInfo(boolean includeClientInfo) {
        super.setIncludeClientInfo(includeClientInfo);
    }

    @Override
    public void setIncludePayload(boolean includePayload) {
        super.setIncludePayload(includePayload);
    }

    @Override
    public void setMaxPayloadLength(int maxPayloadLength) {
        super.setMaxPayloadLength(maxPayloadLength);
    }

}
