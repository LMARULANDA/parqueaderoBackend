package co.com.ceiba.parqueadero.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.model.dto.ParqueaderoDTO;
import co.com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;
import junit.framework.Assert;

public class VigilanteServiceImplTest {
	
	private VehiculoRepository vehiculoRepository;
	private RegistroRepository registroRepository;
	
	
	@Before
	public void setup() {
		vehiculoRepository = mock(VehiculoRepository.class);
		registroRepository = mock(RegistroRepository.class);
	}
	
	@Test
	public void esPlacaEspecial() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
					withPlaca("AIS575").
					withTipoVehiculo("Carro").
					build();	
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		boolean esPlacaEspecial =vigilanteServiceImpl.esPlacaEspecial(vehiculo.getPlaca());		
		//Assert
		assertTrue(esPlacaEspecial);	
	}
	
	@Test 
	public void validarIngresoDiasHabiles() {
		//Arrange
		LocalDate localDate = LocalDate.of(2018, 05, 13);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		boolean validarIngreso = vigilanteServiceImpl.validarIngreso(localDate);
		//Assert
		assertTrue(validarIngreso);	
	}
	
	@Test
	public void validarIngresoDiasNoHabiles() {
		//Arrange
				LocalDate localDate = LocalDate.of(2018, 05, 8);
				//Act
				VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
				boolean validarIngreso = vigilanteServiceImpl.validarIngreso(localDate);
				//Assert
				assertFalse(validarIngreso);	
	}
	
	
	@Test
	public void calcularValorAPagarCarro() throws ParseException {
		//Arrange
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaE = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaE);
		
		String fechaS = "03/05/2018 23:00:10";
		Date fechaSalida = dateformat.parse(fechaS);
	
		float pagoEsperado = (float) 8000.0;
		
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		float pago =vigilanteServiceImpl.calcularValorAPagarCarro(fechaEntrada, fechaSalida);
		
		//Assert
		assertEquals(pagoEsperado, pago, 0.0);
			
	}
	
	@Test
	public void calcularValorAPagarMoto() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("EIS57B").
				withTipoVehiculo("moto").
				withCilindraje(600).
				build();	
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaE = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaE);
		
		String fechaS = "03/05/2018 11:00:10";
		Date fechaSalida = dateformat.parse(fechaS);
	
		float pagoEsperado = (float) 1000.0;
		
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		float pago = vigilanteServiceImpl.calcularValorAPagarMoto(fechaEntrada, fechaSalida, vehiculo.getCilindraje());
		
		//Assert
		assertEquals(pagoEsperado, pago, 0.0);
			
	}
	
	@Test
	public void actualizarDisponibilidadIngreso() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("EIS577").
				withTipoVehiculo("carro").
				build();
	
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.actualizarDisponibilidadIngreso(vehiculo.getTipoDeVehiculo());
		//Assert
		
	}
	
	@Test
	public void actualizarDisponibilidadRetiro() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("EIS577").
				withTipoVehiculo("carro").
				build();
	
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.actualizarDisponibilidadRetiro(vehiculo.getTipoDeVehiculo());
		//Assert
		
	}
	
	
}

