package com.appagility.viewfirstjs.java.model.listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import com.appagility.viewfirstjs.java.atmosphere.Event;
import com.appagility.viewfirstjs.java.atmosphere.EventType;
import com.appagility.viewfirstjs.java.atmosphere.JaxbJacksonObjectMapper;
import com.appagility.viewfirstjs.java.model.Appointment;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

//TODO There should be some sort of lifecycle method rather than having to check on use for loading broadcasters
public class BroadcastEntityListener
{
    private Map<Class<?>, Broadcaster> broadcasters = new HashMap<>();

    private JaxbJacksonObjectMapper objectMapper = new JaxbJacksonObjectMapper();

    public BroadcastEntityListener()
    {
    }

    private Broadcaster getBroadcaster(final String name)
    {
        return BroadcasterFactory.getDefault().lookup(name, true);
    }
    
    private void ensureBroadcastersInitialized()
    {
        if(broadcasters.isEmpty())
        {
            initializeBroadcasters();
        }
    }
    
    public void initializeBroadcasters()
    {
        broadcasters.put(Appointment.class, getBroadcaster("appointment-broadcaster"));
    }

    @PostUpdate
    public void onPostUpdate(Object o)
    {
        ensureBroadcastersInitialized();
        broadcast(EventType.UPDATE, o);
    }

    @PostPersist
    public void onPostPersist(Object o)
    {
        ensureBroadcastersInitialized();
        broadcast(EventType.CREATE, o);
    }

    @PostRemove
    public void onPostRemove(Object o)
    {
        ensureBroadcastersInitialized();
        broadcast(EventType.DELETE, o);
    }

    private void broadcast(EventType eventType, Object entity)
    {
        Broadcaster broadcaster = broadcasters.get(entity.getClass());
        try
        {
            if (broadcaster != null)
            {
                broadcaster.broadcast(objectMapper.writeValueAsString(new Event(eventType, entity)));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
