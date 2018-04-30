package co.com.ceiba.parkinglot.service;

import co.com.ceiba.parkinglot.model.VehicleEntity;

public interface VehicleService {
	
	

	/**
	 * Guarda un nuevo vehiculo (carro o moto)
	 * @param vehicle
	 * @return vehiculo guardado
	 */
	VehicleEntity save(VehicleEntity vehicle);

}
