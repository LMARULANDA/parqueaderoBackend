package co.com.ceiba.parqueadero.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.ConvertirLocalDate;
import co.com.ceiba.parqueadero.util.RegistroException;


@Service
public class VigilanteServiceImpl implements VigilanteService {
	
	//puede ir en utils en constantes.Por ahora dejarlo asi
	public static final char LETRA_INICIAL_PLACA = 'A';
	protected static final String[] DIAS_ESPECIALES = {"SUNDAY","MONDAY"};
	public static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "No puede ingresar porque no está en un dia hábil";
	

	@Autowired
	protected VehiculoRepository vehiculoRepository;
	@Autowired
	protected RegistroRepository registroRepository;
	
	ConvertirLocalDate localDateTime = new ConvertirLocalDate();
	
	public VigilanteServiceImpl(VehiculoRepository vehiculoRepository, RegistroRepository registroRepository) {
		this.vehiculoRepository = vehiculoRepository;
		this.registroRepository = registroRepository;
	}


	@Override
	public Vehiculo save(Vehiculo vehiculo) {
		return this.vehiculoRepository.save(vehiculo);
	}
	
	
	@Override
	public Registro registrarIngreso(Vehiculo vehiculo) {
		
		Registro registro = new Registro();
		LocalDate  date = LocalDate.now();
	
		if(!puedeIngresar(vehiculo.getPlaca(),date)) {
			//revisar este throw.
			throw new RegistroException(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
		}
		registro.setVehiculo(vehiculo);
		registro.setHoraEntrada(localDateTime.convertirLocalDateTimeADate());
		return this.registroRepository.save(registro);
	}
	
	
	public boolean puedeIngresar(String placa,LocalDate date) {
		boolean puedeIngresar = true;
		if(esPlacaEspecial(placa)) {
			puedeIngresar = validarIngreso(date);
		}
		return puedeIngresar;
	}   

	
	public Boolean validarIngreso(LocalDate date) {
		boolean puedeIngresar = false;
		DayOfWeek diaSemana = date.getDayOfWeek();
		String dia = diaSemana.name();
		for( int i = 0 ;i < DIAS_ESPECIALES.length;i++) {
			if(dia.equals(DIAS_ESPECIALES[i])) {
				puedeIngresar = true;
				
			}
			
		}
		
		return puedeIngresar;
	}
	
	public boolean esPlacaEspecial(String placa) {
		boolean esPlacaEspecial = false;
		char letraInicial = placa.charAt(0);
		if(letraInicial == LETRA_INICIAL_PLACA) {
			esPlacaEspecial = true;
		}
		return esPlacaEspecial;
	}

	
	@Override
	public Registro registrarSalida(Vehiculo vehiculo) {
		Registro registro = this.registroRepository.findOne(vehiculo.getId());
		LocalDateTime horaSalida = LocalDateTime.now();
		registro.setHoraSalida(horaSalida);
		return this.registroRepository.save(registro);
	}


	@Override
	public List<Registro> consultarVehiculos() {
		
		return this.registroRepository.findAll();
	}
	
	




}
