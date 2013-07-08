package com.appagility.viewfirstjs.java.repositories;

import com.appagility.viewfirstjs.java.model.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>
{
}
