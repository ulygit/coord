package com.bfjournal.coord.functional;

import com.bfjournal.coord.CliApplication;
import com.bfjournal.coord.model.Contact;
import com.bfjournal.coord.model.Email;
import com.bfjournal.coord.model.Event;
import com.bfjournal.coord.model.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CliApplication.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CoordFunctionalTest {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(CoordFunctionalTest.class);

    @Value("${local.server.port}")
    int port = 0;

    @Before
    public void setup() throws Exception {
        CliApplication.start(new String[0]);
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
    public void canCreateAndRetrieveEvents() throws URISyntaxException {
        // create event
        for (Event event : asList(buildEmailOnlyEvent(), buildPhoneOnlyEvent(), buildPhoneAndEmailEvent())) {
            String eventUri = given().
                        contentType(JSON).
                        body(event).
                    when().
                        post(eventsEndpoint()).
                    then().
                        extract().header("Location");

            // lookup event and make sure it's as expected
            Event returnedEvent = get(eventUri).
                    then().
                        contentType(JSON).
                        extract().as(Event.class);
            assertThat(format("%s", event), returnedEvent.getName(), is(event.getName()));
            assertThat(format("%s", event), returnedEvent.getContacts(), is(event.getContacts()));
        }
    }

    @Test
    public void canRetrieveAllEvents() throws URISyntaxException {
        Event[] events = new Event[]{buildEmailOnlyEvent(), buildPhoneAndEmailEvent(), buildPhoneOnlyEvent()};

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
                body(".", iterableWithSize(3)).
                body("[0].name", is(events[0].getName())).
                body("[1].name", is(events[1].getName())).
                body("[2].name", is(events[2].getName()));
    }

    @Test
    public void canDeleteAnEvent() throws URISyntaxException {
        // create event
        String eventUrl = given().
                contentType(JSON).
                body(buildEmailOnlyEvent()).
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

    private Event buildPhoneAndEmailEvent() {
        return new Event("Event-" + randomNumeric(5),
                new Contact(new Phone(valueOf(randomNumeric(10)))),
                new Contact(new Email(format("%s@dummy.com", randomAlphabetic(10)))));
    }

    private Event buildPhoneOnlyEvent() {
        return new Event("Event-" + randomNumeric(5), new Contact(new Phone(valueOf(randomNumeric(10)))));
    }

    private Event buildEmailOnlyEvent() {
        return new Event("Event-" + randomNumeric(5), new Contact(new Email(format("%s@dummy.com", randomAlphabetic(10)))));
    }

    private URI eventsEndpoint() throws URISyntaxException {
        return new URI("http://localhost:" + port + "/events");
    }

}