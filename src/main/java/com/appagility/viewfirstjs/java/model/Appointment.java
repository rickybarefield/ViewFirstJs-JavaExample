package com.appagility.viewfirstjs.java.model;

import com.appagility.viewfirstjs.java.model.listeners.BroadcastEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(BroadcastEntityListener.class)
public class Appointment extends Identifiable
{
    private String title;
    private Date date;

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
