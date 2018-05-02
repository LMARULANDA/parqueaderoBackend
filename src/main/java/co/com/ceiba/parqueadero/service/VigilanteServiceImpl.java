package co.com.ceiba.parqueadero.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;

@Service
public class VigilanteServiceImpl implements VigilanteService {

	@Autowired
	protected VehiculoRepository vehiculoRepository;

	@Autowired
	protected RegistroRepository registroRepository;

	//Debo de eliminarlo.
	@Override
	public Vehiculo save(Vehiculo vehiculo) {
		return this.vehiculoRepository.save(vehiculo) ;
	}
	//implementacion del metodo registrarIngreso.
	@Override
	public Registro registrarIngreso(Vehiculo vehiculo) {
		Registro registro = new Registro();
		Date fechaActual = new Date();
		registro.setHoraEntrada(fechaActual);
		registro.setVehiculo(vehiculo);
		return this.registroRepository.save(registro);


	}
	@Override
	public Registro registrarSalida(Vehiculo vehiculo) {
		Registro registro = this.registroRepository.findOne(vehiculo.getId());
		Date fechaActual = new Date();
		registro.setHoraSalida(fechaActual);
		return this.registroRepository.save(registro);
	}




}
