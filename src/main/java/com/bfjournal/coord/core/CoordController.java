package com.bfjournal.coord.core;

import com.bfjournal.coord.model.Event;
import com.bfjournal.coord.model.EventsList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class CoordController {

    @RequestMapping(value = "/events", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EventsList getAllEvents() {
        EventsList events = new EventsList();

        Event eventOne = new Event(1, "Lokesh");
        Event eventTwo = new Event(2, "Amit");
        Event eventThree = new Event(3, "Kirti");


        events.getEvents().add(eventOne);
        events.getEvents().add(eventTwo);
        events.getEvents().add(eventThree);

        return events;
    }

    @RequestMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEventById(@PathVariable("id") int id) {
        if (id <= 3) {
            Event event = new Event(1, "Lokesh");
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}