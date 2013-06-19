package com.appagility.viewfirstjs.java.atmosphere;

public class Event
{
    private EventType event;
    private Object entity;

    public Event(EventType event, Object entity)
    {
        super();
        this.event = event;
        this.entity = entity;
    }

    public EventType getEvent()
    {
        return event;
    }

    public Object getEntity()
    {
        return entity;
    }

}
