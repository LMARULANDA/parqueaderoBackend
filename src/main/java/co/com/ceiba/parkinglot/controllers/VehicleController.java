package co.com.ceiba.parkinglot.controllers;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parkinglot.model.Vehicle;
import co.com.ceiba.parkinglot.service.VehicleService;
import co.com.ceiba.parkinglot.util.RestResponse;

@RestController
public class VehicleController {
	
	@Autowired
	protected VehicleService vehicleService;
	
	protected ObjectMapper mapper;
	
	
	@RequestMapping(value = "/saveOrUpdate" , method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String vehicleJson) throws JsonParseException, JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		Vehicle vehicle = this.mapper.readValue(vehicleJson, Vehicle.class);
		
		if(!this.validate(vehicle)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value());
		}
		
		this.vehicleService.save(vehicle);
		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
		
	}
	
	
	private boolean validate(Vehicle vehicle) {
		boolean isValid = true;
		
		if(StringUtils.trimToNull(vehicle.getLicensePlate()) == null) {
			isValid = false;
		}
		return isValid;
		
	
		
	}
}
