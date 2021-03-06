package com.bfjournal.coord.web;

import com.bfjournal.coord.model.Event;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/events")
public class AppController {

    private List<Event> events = new ArrayList<>();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        event.setId(events.size());
        events.add(event);

        // set Location header in response to new resource URI
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newEventUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(event.getId())
                .toUri();
        responseHeaders.setLocation(newEventUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event[]> getAllEvents() {
        return ok(events.toArray(new Event[events.size()]));
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEventById(@PathVariable("id") int id) {
        if (id < 0 || id >= events.size()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ok(events.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEventById(@PathVariable("id") int id) {
        if (id < 0 || id >= events.size()) {
            return notFound().build();
        }
        events.remove(id);
        return ok().build();
    }
}