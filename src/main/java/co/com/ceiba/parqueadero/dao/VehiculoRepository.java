package co.com.ceiba.parqueadero.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
		
}
