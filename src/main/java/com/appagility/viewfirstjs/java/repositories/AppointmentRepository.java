package com.appagility.viewfirstjs.java.repositories;

import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.viewfirstjs.java.resources.Appointment;

import java.util.Collection;

public class AppointmentRepository extends SubscribingRepository<Long, Appointment>
{
    @Override
    protected Appointment create(Appointment appointment)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Collection<Appointment> readAll()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
