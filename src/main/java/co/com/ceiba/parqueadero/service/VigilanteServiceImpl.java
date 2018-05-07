package co.com.ceiba.parqueadero.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.ConvertirDate;
import co.com.ceiba.parqueadero.util.RegistroException;

@Service
public class VigilanteServiceImpl implements VigilanteService {

    public static final char LETRA_INICIAL_PLACA = 'A';
	protected static final String[] DIAS_ESPECIALES = { "SUNDAY", "MONDAY" };
	public static final String NO_PUEDE_INGRESAR_DIA_NO_HABIL = "No puede ingresar porque no esta en un dia habil";
	public static final String NO_HAY_PARQUEADEROS_DISPONIBLES = "No puede ingresar porque no hay un parqueadero disponible para Carros";

	public static final String CARRO = "carro";
	public static final String MOTO = "moto";
	public static final float VALOR_HORA_CARRO = 1000;
	public static final float VALOR_HORA_MOTO = 500;
	public static final float VALOR_DIA_CARRO = 8000;
	public static final float VALOR_DIA_MOTO = 600;
	public static final int HORAS_MINIMAS_DIA = 9;
	public static final int HORAS_MAXIMAS_DIA = 24;
	public static final int CILINDRAJE_MAYOR_MOTO = 500;
	public static final float VALOR_ADICIONAL_CILINDRAJE = 2000;

	public static final int CANTIDAD_MAXIMA_CARROS = 20;
	public static final int CANTIDAD_MAXIMA_MOTOS = 10;
	
	public int cantidadMaximaDisponible = 0;
	public float valorHora = 0;
	public float valorDia = 0;
	

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
	public Vehiculo findById(Long id) {
		return this.vehiculoRepository.findOne(id);
		
	}
	
	@Override
	public Registro registrarIngreso(Vehiculo vehiculo) {
		LocalDate localDate = LocalDate.now();

		if (!verificarDisponibilidad(vehiculo)) {
			throw new RegistroException(NO_HAY_PARQUEADEROS_DISPONIBLES);
		}
		if (!validarIngreso(vehiculo.getPlaca(),localDate)) {

				throw new RegistroException(NO_PUEDE_INGRESAR_DIA_NO_HABIL);
		}
		save(vehiculo);
		Registro registro = new Registro();
		LocalDateTime localDateTime = LocalDateTime.now();
		registro.setVehiculo(vehiculo);
		registro.setHoraEntrada(date.convertirLocalDateTimeADate(localDateTime));	
		return this.registroRepository.save(registro);

	}

	private boolean verificarDisponibilidad(Vehiculo vehiculo) {
		if(vehiculo.getTipoDeVehiculo() == CARRO) {
			cantidadMaximaDisponible = CANTIDAD_MAXIMA_CARROS;
		}else {
			cantidadMaximaDisponible = CANTIDAD_MAXIMA_MOTOS;
		}
		return this.registroRepository.countVehiculosIngresados(vehiculo.getTipoDeVehiculo()) 
				<= cantidadMaximaDisponible;
		
	}

	@Override
	public Registro registrarSalida(Vehiculo vehiculo) {
		Registro registro = this.registroRepository.findByVehiculo(vehiculo.getId());
		LocalDateTime localDateTime = LocalDateTime.now();
		registro.setHoraSalida(date.convertirLocalDateTimeADate(localDateTime));
		registro.setPago(calcularValorAPagar(registro));
		return this.registroRepository.save(registro);
	}

	@Override
	public List<Registro> consultarVehiculos() {
		return this.registroRepository.findAllVehiculosIngresados();
	}

	public boolean validarIngreso(String placa, LocalDate date) {
		return (validarPlaca(placa) && validarDias(date));
	}

	public Boolean validarDias(LocalDate date) {
		boolean diaValido = false;
		DayOfWeek diaSemana = date.getDayOfWeek();
		String dia = diaSemana.name();
		for (int i = 0; i < DIAS_ESPECIALES.length; i++) {
			if (dia.equals(DIAS_ESPECIALES[i])) {
				diaValido = true;
			}
		}
		return diaValido;
	}

	public boolean validarPlaca(String placa) {	
		return placa.charAt(0) == LETRA_INICIAL_PLACA;
	}

	public float calcularValorAPagar(Registro registro) {
		float pago;
		if(registro.getVehiculo().getTipoDeVehiculo() == CARRO) {
			valorHora = VALOR_HORA_CARRO;
			valorDia = VALOR_DIA_CARRO;
		} else {
			valorHora = VALOR_HORA_MOTO;
			valorDia = VALOR_DIA_MOTO;
		}
		
		LocalDateTime horaEntrada = date.convertirDateALocalDateTime(registro.getHoraEntrada());
		LocalDateTime horaSalida = date.convertirDateALocalDateTime(registro.getHoraSalida());
		long horasPermanencia = Duration.between(horaEntrada, horaSalida).toHours();
		
		if (horasPermanencia == 0) {
			horasPermanencia = 1;
		}

		if (horasPermanencia < HORAS_MINIMAS_DIA) {
			pago = valorHora * horasPermanencia;
		} else if (horasPermanencia < HORAS_MAXIMAS_DIA) {
			pago = valorDia;
		} else {
			long diasPermanencia = Duration.between(horaEntrada, horaSalida).toDays();
			horasPermanencia = horasPermanencia - (diasPermanencia * HORAS_MAXIMAS_DIA);
			pago = (diasPermanencia * valorDia) + (horasPermanencia * valorHora);
		}
		
		return pago;
	}

	public float calcularValorAPagarCarro(Date fechaEntrada, Date fechaSalida) {
		float pago;
		LocalDateTime horaEntrada = date.convertirDateALocalDateTime(fechaEntrada);
		LocalDateTime horaSalida = date.convertirDateALocalDateTime(fechaSalida);
		long horasPermanencia = Duration.between(horaEntrada, horaSalida).toHours();
		if (horasPermanencia == 0) {
			horasPermanencia = 1;
		}

		if (horasPermanencia < HORAS_MINIMAS_DIA) {
			pago = VALOR_HORA_CARRO * horasPermanencia;
		} else if (horasPermanencia < HORAS_MAXIMAS_DIA) {
			pago = VALOR_DIA_CARRO;
		} else {
			long diasPermanencia = Duration.between(horaEntrada, horaSalida).toDays();
			horasPermanencia = horasPermanencia - (diasPermanencia * HORAS_MAXIMAS_DIA);
			pago = (diasPermanencia * VALOR_DIA_CARRO) + (horasPermanencia * VALOR_HORA_CARRO);
		}

		return pago;
	}

	public float calcularValorAPagarMoto(Date fechaEntrada, Date fechaSalida, int cilindraje) {
		float pago;
		LocalDateTime horaEntrada = date.convertirDateALocalDateTime(fechaEntrada);
		LocalDateTime horaSalida = date.convertirDateALocalDateTime(fechaSalida);
		long horasPermanencia = Duration.between(horaEntrada, horaSalida).toHours();
		if (horasPermanencia == 0) {
			horasPermanencia = 1;
		}
		if (horasPermanencia < HORAS_MINIMAS_DIA) {
			pago = VALOR_HORA_MOTO * horasPermanencia;
		} else if (horasPermanencia < HORAS_MAXIMAS_DIA) {
			pago = VALOR_DIA_MOTO;
		} else {
			long diasPermanencia = Duration.between(horaEntrada, horaSalida).toDays();
			horasPermanencia = horasPermanencia - (diasPermanencia * HORAS_MAXIMAS_DIA);
			pago = (diasPermanencia * VALOR_DIA_MOTO) + (horasPermanencia * VALOR_HORA_MOTO);
		}

		if (cilindraje > CILINDRAJE_MAYOR_MOTO) {
			pago = pago + VALOR_ADICIONAL_CILINDRAJE;
		}
		return pago;
	}

}
