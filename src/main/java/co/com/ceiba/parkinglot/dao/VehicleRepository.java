package co.com.ceiba.parkinglot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parkinglot.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	
	@SuppressWarnings("unchecked")
	Vehicle save(Vehicle vehicle);
	
}
