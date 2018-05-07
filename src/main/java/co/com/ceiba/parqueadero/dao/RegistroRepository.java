package co.com.ceiba.parqueadero.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.ceiba.parqueadero.model.Registro;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

	@Query("select r from Registro r where r.vehiculo.id = ?1 and r.horaSalida is null ")
	Registro findByVehiculo(Long id);

	@Query("select r from Registro r where r.horaSalida is null")
	List<Registro> findAllVehiculosIngresados();
	
	@Query("select count(r)  from Registro r where r.vehiculo.tipoDeVehiculo =?1 and r.horaSalida is null")
	Integer countVehiculosIngresados(String tipoVehiculo);
	
}
