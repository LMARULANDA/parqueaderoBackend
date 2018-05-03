package co.com.ceiba.parqueadero.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.model.dto.ParqueaderoDTO;
import co.com.ceiba.parqueadero.util.ConvertirDate;
import co.com.ceiba.parqueadero.util.RegistroException;


@Service
public class VigilanteServiceImpl implements VigilanteService {
	
	//puede ir en utils en constantes.Por ahora dejarlo asi
	public static final char LETRA_INICIAL_PLACA = 'A';
	protected static final String[] DIAS_ESPECIALES = {"SUNDAY","MONDAY"};
	public static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "No puede ingresar porque no está en un dia hábil";
	public static final String NO_HAY_PARQUEADEROS_DISPONIBLES = "No puede ingresar porque no hay un parqueadero disponible para Carros";
	
	
	public static final String CARRO = "carro";
	public static final String MOTO = "moto";
	public static final float VALOR_HORA_CARRO = 1000;
	public static final float VALOR_HORA_MOTO = 500;
	public static final float VALOR_DIA_CARRO = 8000;
	public static final float VALOR_DIA_MOTO = 600;
	public static final int HORAS_MINIMAS_DIA = 9;
	public static final int CILINDRAJE_MAYOR_MOTO = 500;
	public static final float VALOR_ADICIONAL_CILINDRAJE = 2000;
	
	//public static final int CANTIDAD_MAXIMA_CARROS = 20;
	//public static final int CANTIDAD_MAXIMA_MOTOS = 10;

	@Autowired
	protected VehiculoRepository vehiculoRepository;
	@Autowired
	protected RegistroRepository registroRepository;
	
	ConvertirDate date = new ConvertirDate();
	
	
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
		LocalDate localDate = LocalDate.now();
		
		if(verificarDisponibilidad(vehiculo.getTipoDeVehiculo())) {
				
			if(!puedeIngresar(vehiculo.getPlaca(),localDate)) {

				throw new RegistroException(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
			}
			LocalDateTime localDateTime = LocalDateTime.now();
			registro.setVehiculo(vehiculo);
			registro.setHoraEntrada(date.convertirLocalDateTimeADate(localDateTime));
			actualizarDisponibilidadIngreso(vehiculo.getTipoDeVehiculo());
					
		}
		return this.registroRepository.save(registro);
				
	}
	
	
	@Override
	public Registro registrarSalida(Vehiculo vehiculo) {
		Registro registro = this.registroRepository.findByVehiculo(vehiculo.getId());
		LocalDateTime localDateTime = LocalDateTime.now();
		registro.setHoraSalida(date.convertirLocalDateTimeADate(localDateTime));
		registro.setPago(calcularValorAPagar(registro));
		
		
		float pago;
		if(vehiculo.getTipoDeVehiculo() == CARRO) {
			pago = calcularValorAPagarCarro(registro.getHoraEntrada(),registro.getHoraSalida());
		}
		else {
			pago = calcularValorAPagarMoto(registro.getHoraEntrada(),registro.getHoraSalida(),registro.getVehiculo().getCilindraje());
		}
		
		registro.setPago(pago);
		actualizarDisponibilidadRetiro(vehiculo.getTipoDeVehiculo());
		
		return this.registroRepository.save(registro);
	}
	
	@Override
	public List<Registro> consultarVehiculos() {
		return this.registroRepository.findAllVehiculosIngresados();
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
	
	public float calcularValorAPagar(Registro registro) {
		float pago;
		
		if(registro.getVehiculo().getTipoDeVehiculo() == CARRO) {
			
			pago = calcularValorAPagarCarro(registro.getHoraEntrada(),registro.getHoraSalida());
		}
		else {
			pago =calcularValorAPagarMoto(registro.getHoraEntrada(),registro.getHoraSalida(),registro.getVehiculo().getCilindraje());
		}
		 return pago;
	}
	
	
	public float calcularValorAPagarCarro(Date fechaEntrada, Date fechaSalida) {
		float pago;
		LocalTime horaEntrada = date.convertirDateALocalTime(fechaEntrada);
		LocalTime horaSalida = date.convertirDateALocalTime(fechaSalida);
		long horasPermanencia = Duration.between(horaEntrada, horaSalida).toHours();
		if(horasPermanencia == 0 ) {
			horasPermanencia = 1;
		}
		//System.out.println(horaEntrada);
		//System.out.println(horaSalida);
		//System.out.println(horasPermanencia);
			if(horasPermanencia < HORAS_MINIMAS_DIA) {
				pago = VALOR_HORA_CARRO * horasPermanencia;
			}
			else {
				pago = VALOR_DIA_CARRO;
			}
		//System.out.println(pago);
		return pago;
	}
	
	public float calcularValorAPagarMoto(Date fechaEntrada, Date fechaSalida, int cilindraje) {
		float pago;
		LocalTime horaEntrada = date.convertirDateALocalTime(fechaEntrada);
		LocalTime horaSalida = date.convertirDateALocalTime(fechaSalida);
		long horasPermanencia = Duration.between(horaEntrada, horaSalida).toHours();
		if(horasPermanencia == 0 ) {
			horasPermanencia = 1;
		}
		//System.out.println(horaEntrada);
		//System.out.println(horaSalida);
		//System.out.println(horasPermanencia);
		if(horasPermanencia < HORAS_MINIMAS_DIA) {
			pago = VALOR_HORA_MOTO * horasPermanencia;
		}
		else {
			pago = VALOR_DIA_MOTO;
		}
		
		if(cilindraje == CILINDRAJE_MAYOR_MOTO ) {
			pago = pago + VALOR_ADICIONAL_CILINDRAJE;
		}
		//System.out.println(pago);
		return pago;
	}
	
	public boolean verificarDisponibilidad(String tipoVehiculo) {
		boolean estaDisponible;
		if(tipoVehiculo == CARRO) {
			estaDisponible = consultarDisponibilidadCarro();
		}else {
			estaDisponible = consultarDisponibilidadMoto();
		}
		return estaDisponible;
		
	}
	
	public boolean consultarDisponibilidadCarro() {
		boolean disponible = true;
		ParqueaderoDTO parqueadero = new ParqueaderoDTO();
		int cantidadParqueaderosCarros = parqueadero.getCantidadDisponibleCarros();
		//System.out.println(cantidadParqueaderosCarros);
		if(cantidadParqueaderosCarros == 0) {
			disponible = false;
		}
		return disponible;
		
	}
	
	public boolean consultarDisponibilidadMoto() {
		boolean disponible = true;
		ParqueaderoDTO parqueadero = new ParqueaderoDTO();
		int cantidadParqueaderosCarros = parqueadero.getCantidadDisponibleCarros();
		if(cantidadParqueaderosCarros == 0) {
			disponible = false;
		}
		return disponible;
	}
	
	public void actualizarDisponibilidadIngreso(String tipoVehiculo) {
		ParqueaderoDTO parqueadero = new ParqueaderoDTO();
		
		if(tipoVehiculo == CARRO) {
			int cantidadDisponible = parqueadero.getCantidadDisponibleCarros();
			parqueadero.setCantidadDisponibleCarros(cantidadDisponible -1);
			System.out.println("ingreso" + parqueadero.getCantidadDisponibleCarros());
		}else {
			int cantidadDisponible = parqueadero.getCantidadDisponibleMotos();
			parqueadero.setCantidadDisponibleMotos(cantidadDisponible -1);
			System.out.println("ingreso" + parqueadero.getCantidadDisponibleMotos());
		}
	}
		
		public void actualizarDisponibilidadRetiro(String tipoVehiculo) {
			ParqueaderoDTO parqueadero = new ParqueaderoDTO();
			
			if(tipoVehiculo == CARRO) {
				int cantidadDisponible = parqueadero.getCantidadDisponibleCarros();
				System.out.println("retiro" +parqueadero.getCantidadDisponibleCarros());
				parqueadero.setCantidadDisponibleCarros(cantidadDisponible + 1);
				System.out.println("retiro" +parqueadero.getCantidadDisponibleCarros());
				
			}else {
				int cantidadDisponible = parqueadero.getCantidadDisponibleMotos();
				parqueadero.setCantidadDisponibleMotos(cantidadDisponible + 1);
				System.out.println("retiro" +parqueadero.getCantidadDisponibleMotos());
			}
		
	}
	
}
