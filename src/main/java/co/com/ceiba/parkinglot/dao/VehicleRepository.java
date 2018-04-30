package co.com.ceiba.parkinglot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parkinglot.model.VehicleEntity;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
	
	
	@SuppressWarnings("unchecked")
	VehicleEntity save(VehicleEntity vehicle);
	
}
