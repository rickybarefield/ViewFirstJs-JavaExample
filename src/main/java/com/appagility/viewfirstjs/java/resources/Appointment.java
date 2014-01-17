package com.appagility.viewfirstjs.java.resources;

import com.appagility.j2ee.websocket.dispatcher.Id;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.appagility.viewfirstjs.java.repositories.AppointmentRepository;
import com.appagility.viewfirstjs.java.repositories.AppointmentRepositoryFactory;

import java.util.Date;

@WebSocketResource(name = "Appointment", repositoryFactory = AppointmentRepositoryFactory.class)
public class Appointment
{
    private Long id;
    private String title;
    private Date date;

    @Id
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

}
