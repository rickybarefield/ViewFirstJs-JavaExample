package com.appagility.viewfirstjs.java.repositories;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.viewfirstjs.java.resources.Appointment;

public class AppointmentRepositoryFactory implements RepositoryFactory<Appointment>
{
    AppointmentRepository repository = new AppointmentRepository();

    @Override
    public AppointmentRepository create()
    {
        return repository;
    }
}
