package com.bfjournal.coord.functional;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CoordFunctionalTest {

    private int port = 0;

    @Before
    public void setup() throws Exception {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase(sourceFolder("main/webapp/"));

        Server container = new Server(0);
        container.setHandler(context);

        container.start();

        port = ((ServerConnector) container.getConnectors()[0]).getLocalPort();
    }

    @Test
    public void canCreateAndRetrieveNewEvents() throws URISyntaxException, InterruptedException {
        // create event
        String url = "http://localhost:" + port + "/events";
        given().formParam("name", "Ulises").when().post(new URI(url))
                .then().body("events[0].name", is("Lokesh"));
        // lookup event and make sure it's as expected
    }

    private String sourceFolder(final String path) throws Exception {
        File testRoot = new File(ClassLoader.getSystemResource("crumb").toURI()).getParentFile().getParentFile().getParentFile();
        return new File(testRoot, "src/" + path).getAbsolutePath();
    }

}