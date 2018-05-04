package co.com.ceiba.parqueadero.service;


import java.util.List;

import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;


public interface VigilanteService {

	Vehiculo save(Vehiculo vehiculo);

	Registro registrarIngreso(Vehiculo vehiculo);

	Registro registrarSalida(Vehiculo vehiculo);

	List<Registro> consultarVehiculos();

	Vehiculo findById(Long id);
	
}
