package com.appagility.viewfirstjs.java.repositories;

import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.viewfirstjs.java.resources.Appointment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class AppointmentRepository extends SubscribingRepository<Long, Appointment>
{
    private AtomicLong id = new AtomicLong(0);

    public Map<Long, Appointment> appointments = new HashMap<>();

    @Override
    public Appointment create(Appointment item)
    {
        long id = this.id.getAndAdd(1);

        item.setId(id);

        appointments.put(id, item);

        return item;
    }

    @Override
    protected Collection<Appointment> readAll()
    {
        return appointments.values();
    }
}
