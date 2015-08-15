package com.bfjournal.coord.functional;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by grant on 8/14/15.
 */
public class ServerLoadListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private int port = -1;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        port = event.getEmbeddedServletContainer().getPort();
    }
}
