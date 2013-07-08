package com.appagility.viewfirstjs.java.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
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
