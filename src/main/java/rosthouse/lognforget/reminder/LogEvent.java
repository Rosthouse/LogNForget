/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget.reminder;

import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class LogEvent extends Event {

    public static EventType<LogEvent> LOG_EVENT = new EventType(ANY, LogEvent.class.getName());
    private String text;

    public LogEvent(String text) {
        super(LOG_EVENT);
        this.text = text;
    }

    public LogEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public String getText() {
        return text;
    }

}
