package co.com.ceiba.parkinglot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parkinglot.model.RegistryEntity;
import co.com.ceiba.parkinglot.model.VehicleEntity;
import co.com.ceiba.parkinglot.service.GuardService;
import co.com.ceiba.parkinglot.util.RestResponse;

public class GuardController {
	
	@Autowired
	protected GuardService guardService;
	
	protected ObjectMapper mapper;
	
	@RequestMapping(value="/saveRegistry", method = RequestMethod.POST)
	public RestResponse saveRegistry(@RequestBody String vehicleJson) {
		this.mapper = new ObjectMapper();
	
		return null;
		
	}
	
	

}
