package com.springbootexam.upandrunning_ch6.dao;

import org.springframework.data.repository.CrudRepository;

import com.springbootexam.upandrunning_ch6.model.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long>{
   
}
