package com.bfjournal.coord.functional;

import com.bfjournal.coord.model.Event;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

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
    public void canCreateAndRetrieveNewEvents() throws URISyntaxException {
        // create event
        Event event = new Event("My Event!");
        given().
                contentType(JSON).
                body(event).
                when().
                post(eventsEndpoint()).
                then().
                header("Location", is(eventsEndpoint() + "/0"));

        // lookup event and make sure it's as expected
        get(eventsEndpoint() + "/0").
                then().
                contentType(JSON).
                body("name", is(event.getName()));
    }

    @Test
    public void canRetrieveAllEvents() throws URISyntaxException {
        Event[] events = new Event[]{new Event("One"), new Event("Two")};

        for (Event event : events) {
            given().
                    contentType(JSON).
                    body(event).
                    when().
                    post(eventsEndpoint());
        }

        get(eventsEndpoint()).
                then().
                contentType(JSON).
                body("events", iterableWithSize(2)).
                body("events[0].name", is(events[0].getName())).
                body("events[1].name", is(events[1].getName()));
    }

    @Test
    public void canDeleteAnEvent() throws URISyntaxException {
        // create event
        String eventUrl = given().
                contentType(JSON).
                body(new Event("To be deleted")).
                when().
                post(eventsEndpoint()).
                thenReturn().
                getHeader("Location");

        // verify resource was created
        get(eventUrl).then().assertThat().statusCode(is(200));

        // delete resource
        delete(eventUrl).then().assertThat().statusCode(200);

        // verify resource was deleted
        get(eventUrl).then().assertThat().statusCode(is(404));
    }

    private URI eventsEndpoint() throws URISyntaxException {
        return new URI("http://localhost:" + port + "/events");
    }

    private String sourceFolder(final String path) throws Exception {
        File testRoot = new File(ClassLoader.getSystemResource("crumb").toURI()).getParentFile().getParentFile().getParentFile();
        return new File(testRoot, "src/" + path).getAbsolutePath();
    }

}