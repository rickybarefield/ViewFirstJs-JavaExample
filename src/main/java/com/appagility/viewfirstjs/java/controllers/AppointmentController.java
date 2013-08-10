package com.appagility.viewfirstjs.java.controllers;

import com.appagility.viewfirstjs.java.atmosphere.AtmosphereUtils;
import com.appagility.viewfirstjs.java.model.Appointment;
import com.appagility.viewfirstjs.java.repositories.AppointmentRepository;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 *
 * TODO This class may not be able to use (and probably shouldn't) return or take entities:
 * TODO
 * TODO    consider the case where some properties are not mentioned in json, plus need to automate the application
 * TODO    of an update
 *
 */
@Controller
public class AppointmentController
{
    @Autowired
    private AppointmentRepository appointmentRepository;

    private Broadcaster appointmentBroadcaster;

    @PostConstruct
    public void createBroadcaster() {

        appointmentBroadcaster = BroadcasterFactory.getDefault().lookup("appointment-broadcaster", true);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/appointments/{id}")
	public @ResponseBody Appointment get(@PathVariable Long id)
	{
	    return appointmentRepository.findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/appointments")
	public @ResponseBody Iterable<Appointment> getAll(final AtmosphereResource atmosphereResource)
	{
        AtmosphereUtils.suspend(atmosphereResource);
        appointmentBroadcaster.addAtmosphereResource(atmosphereResource);

        return appointmentRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/appointments")
	public @ResponseBody Appointment create(@RequestBody Appointment appointment)
	{
	    appointmentRepository.save(appointment);
		return appointment;
	}
	
    @RequestMapping(method = RequestMethod.PUT, value = "/appointments/{id}")
	public @ResponseBody Appointment update(@RequestBody Appointment appointment, @PathVariable Long id)
	{
        Appointment appointmentPersisted = appointmentRepository.findOne(id);
        //TODO Set non null fields
	    return appointmentPersisted;
	}
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/appointments/{id}")
    public void delete(@PathVariable Long id)
    {
        appointmentRepository.delete(id);
    }
}
