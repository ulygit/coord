package com.bfjournal.coord.functional;

import com.bfjournal.coord.model.Event;
import com.bfjournal.coord.web.SimpleCORSFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.DispatcherType;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.*;

public class CoordFunctionalTest {

    private int port = 0;

    @Before
    public void setup() throws Exception {
        WebAppContext context = new WebAppContext();
        context.setErrorHandler(null);
        context.setContextPath("/");
        context.setResourceBase(sourceFolder("main/webapp/"));
        context.addFilter(SimpleCORSFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        Server container = new Server(0);
        container.setHandler(context);
        container.start();

        port = ((ServerConnector) container.getConnectors()[0]).getLocalPort();
    }

    @Test
    public void verifyCrossSiteResourceSharingIsSupported() throws URISyntaxException {
        options(eventsEndpoint()).
                then().
                header("Access-Control-Allow-Headers", is("Origin, X-Requested-With, Content-Type, Accept")).
                header("Access-Control-Allow-Methods", is("POST, GET, OPTIONS, DELETE")).
                header("Access-Control-Max-Age", is("3600")).
                header("Access-Control-Allow-Origin", is("*"));
    }

    @Test
    public void canCreateAndRetrieveNewEvents() throws URISyntaxException {
        // create event
        Event event = buildUniqueEvent();
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
                body("name", is(event.getName())).
                body("contacts", contains(event.getContacts()));
    }

    @Test
    public void canRetrieveAllEvents() throws URISyntaxException {
        Event[] events = new Event[]{buildUniqueEvent(), buildUniqueEvent()};

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
                body(buildUniqueEvent()).
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

    private Event buildUniqueEvent() {
        return new Event("Event-" + randomNumeric(5), format("%s@dummy.com", randomAlphabetic(10)));
    }

    private URI eventsEndpoint() throws URISyntaxException {
        return new URI("http://localhost:" + port + "/events");
    }

    private String sourceFolder(final String path) throws Exception {
        File testRoot = new File(ClassLoader.getSystemResource("crumb").toURI()).getParentFile().getParentFile().getParentFile();
        return new File(testRoot, "src/" + path).getAbsolutePath();
    }

}