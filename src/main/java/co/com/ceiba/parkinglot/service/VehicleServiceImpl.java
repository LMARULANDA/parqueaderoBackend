package co.com.ceiba.parkinglot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkinglot.dao.VehicleRepository;
import co.com.ceiba.parkinglot.model.VehicleEntity;

@Service
public class VehicleServiceImpl implements VehicleService {

	
	@Autowired
	protected VehicleRepository vehicleRepository;

	@Override
	public VehicleEntity save(VehicleEntity vehicle) {
		return this.vehicleRepository.save(vehicle);
	}

	
}
