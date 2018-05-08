package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.testdatabuilder.RegistroTestDataBuilder;
import co.com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

public class VigilanteServiceImplTest {
	
	private VehiculoRepository vehiculoRepository;
	private RegistroRepository registroRepository;
	
	
	@Before
	public void setup() {
		vehiculoRepository = mock(VehiculoRepository.class);
		registroRepository = mock(RegistroRepository.class);
	}
	
	@Test
	public void validarIngresoPlacaNoEspecial() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("XYZ123").
				withTipoVehiculo("carro").
				build();
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		//Assert
		assertTrue(vigilanteServiceImpl.validarIngreso(vehiculo.getPlaca(), localDate));
		
	}
	
	@Test
	public void validarIngresoPlacaEspecialDiaHabil() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("ABC123").
				withTipoVehiculo("carro").
				build();
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		//Assert
		 assertTrue(vigilanteServiceImpl.validarIngreso(vehiculo.getPlaca(),localDate));
	}
	
	
	@Test
	public void validarIngresoPlacaEspecialDiaNoHabil() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("ABC123").
				withTipoVehiculo("carro").
				build();
		LocalDate localDate = LocalDate.of(2018, 05, 8);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		//Assert
		 assertFalse(vigilanteServiceImpl.validarIngreso(vehiculo.getPlaca(),localDate));
	}
	
	@Test
	public void verificarDisponibilidadCarro() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("LAU310").
				withTipoVehiculo("carro").
				build();
		when(this.registroRepository.countVehiculosIngresados(vehiculo.getTipoDeVehiculo())).thenReturn(8);
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		
		//Asser
		assertTrue(vigilanteServiceImpl.verificarDisponibilidad(vehiculo));
	}
	
	@Test
	public void verificarNoDisponibilidadCarro() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK456").
				withTipoVehiculo("carro").
				build();
		when(this.registroRepository.countVehiculosIngresados(vehiculo.getTipoDeVehiculo())).thenReturn(20);
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		
		//Asser
		assertFalse(vigilanteServiceImpl.verificarDisponibilidad(vehiculo));
	}
	
	@Test
	public void verificarDisponibilidadMoto() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("LAU31L").
				withTipoVehiculo("moto").
				withCilindraje(500).
				build();
		when(this.registroRepository.countVehiculosIngresados(vehiculo.getTipoDeVehiculo())).thenReturn(8);
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		
		//Asser
		assertTrue(vigilanteServiceImpl.verificarDisponibilidad(vehiculo));
	}
	
	@Test
	public void verificarNoDisponibilidadMoto() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK45R").
				withTipoVehiculo("moto").
				withCilindraje(500).
				build();
		when(this.registroRepository.countVehiculosIngresados(vehiculo.getTipoDeVehiculo())).thenReturn(10);
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		
		//Asser
		assertFalse(vigilanteServiceImpl.verificarDisponibilidad(vehiculo));
	}
	
	@Test
	public void calcularValorAPagarCarro1Hora() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK457").
				withTipoVehiculo("carro").
				build();
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaEntradaS = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaEntradaS);
		
		String fechaSalidaS = "03/05/2018 09:30:00";
		Date fechaSalida = dateformat.parse(fechaSalidaS);
		
		Registro registro = new RegistroTestDataBuilder().
				withVehiculo(vehiculo).
				withHoraEntrada(fechaEntrada).
				withHoraSalida(fechaSalida).
				build();
		float pagoEsperado = 1000;		
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
		
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarCarroMenosDe9Horas() throws ParseException {
		//Arrange
				Vehiculo vehiculo = new VehiculoTestDataBuilder().
						withPlaca("OLK457").
						withTipoVehiculo("carro").
						build();
				
				DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fechaEntradaS = "03/05/2018 09:00:00";
				Date fechaEntrada = dateformat.parse(fechaEntradaS);
				
				String fechaSalidaS = "03/05/2018 12:00:00";
				Date fechaSalida = dateformat.parse(fechaSalidaS);
				
				Registro registro = new RegistroTestDataBuilder().
						withVehiculo(vehiculo).
						withHoraEntrada(fechaEntrada).
						withHoraSalida(fechaSalida).
						build();
		
		float pagoEsperado = 3000;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	

	@Test
	public void calcularValorAPagarCarroMasDe9YMenosDe24Horas() throws ParseException {
		//Arrange
				Vehiculo vehiculo = new VehiculoTestDataBuilder().
						withPlaca("OLK457").
						withTipoVehiculo("carro").
						build();
				
				DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fechaEntradaS = "03/05/2018 09:00:00";
				Date fechaEntrada = dateformat.parse(fechaEntradaS);
				
				String fechaSalidaS = "03/05/2018 20:00:00";
				Date fechaSalida = dateformat.parse(fechaSalidaS);
				
				Registro registro = new RegistroTestDataBuilder().
						withVehiculo(vehiculo).
						withHoraEntrada(fechaEntrada).
						withHoraSalida(fechaSalida).
						build();
		
		float pagoEsperado = 8000;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarCarroMasDe24Horas() throws ParseException {
		//Arrange
				Vehiculo vehiculo = new VehiculoTestDataBuilder().
						withPlaca("OLK457").
						withTipoVehiculo("carro").
						build();
				
				DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fechaEntradaS = "03/05/2018 09:00:00";
				Date fechaEntrada = dateformat.parse(fechaEntradaS);
				
				String fechaSalidaS = "04/05/2018 12:00:00";
				Date fechaSalida = dateformat.parse(fechaSalidaS);
				
				Registro registro = new RegistroTestDataBuilder().
						withVehiculo(vehiculo).
						withHoraEntrada(fechaEntrada).
						withHoraSalida(fechaSalida).
						build();
		
		float pagoEsperado = 11000;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarMoto1Hora() throws ParseException {
		//Arrange
				Vehiculo vehiculo = new VehiculoTestDataBuilder().
						withPlaca("OLK45M").
						withTipoVehiculo("moto").
						build();
				
				DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fechaEntradaS = "03/05/2018 09:00:00";
				Date fechaEntrada = dateformat.parse(fechaEntradaS);
				
				String fechaSalidaS = "03/05/2018 09:30:00";
				Date fechaSalida = dateformat.parse(fechaSalidaS);
				
				Registro registro = new RegistroTestDataBuilder().
						withVehiculo(vehiculo).
						withHoraEntrada(fechaEntrada).
						withHoraSalida(fechaSalida).
						build();
		
		float pagoEsperado = 500;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarMotoMenosDe9Horas() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK45M").
				withTipoVehiculo("moto").
				build();
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaEntradaS = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaEntradaS);
		
		String fechaSalidaS = "03/05/2018 12:00:00";
		Date fechaSalida = dateformat.parse(fechaSalidaS);
		
		Registro registro = new RegistroTestDataBuilder().
				withVehiculo(vehiculo).
				withHoraEntrada(fechaEntrada).
				withHoraSalida(fechaSalida).
				build();

		float pagoEsperado = 1500;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
		

	}
	
	@Test
	public void calcularValorAPagarMotoMasDe9YMenosDe24Horas() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK45M").
				withTipoVehiculo("moto").
				build();
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaEntradaS = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaEntradaS);
		
		String fechaSalidaS = "03/05/2018 20:00:00";
		Date fechaSalida = dateformat.parse(fechaSalidaS);
		
		Registro registro = new RegistroTestDataBuilder().
				withVehiculo(vehiculo).
				withHoraEntrada(fechaEntrada).
				withHoraSalida(fechaSalida).
				build();

		float pagoEsperado = 4000;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarMotoMasDe24Horas() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK45M").
				withTipoVehiculo("moto").
				build();
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaEntradaS = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaEntradaS);
		
		String fechaSalidaS = "04/05/2018 12:00:00";
		Date fechaSalida = dateformat.parse(fechaSalidaS);
		
		Registro registro = new RegistroTestDataBuilder().
				withVehiculo(vehiculo).
				withHoraEntrada(fechaEntrada).
				withHoraSalida(fechaSalida).
				build();

		float pagoEsperado = 5500;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	@Test
	public void calcularValorAPagarMotoCilindrajeMayorA500CC() throws ParseException {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("OLK45M").
				withTipoVehiculo("moto").
				build();
		
		DateFormat dateformat= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fechaEntradaS = "03/05/2018 09:00:00";
		Date fechaEntrada = dateformat.parse(fechaEntradaS);
		
		String fechaSalidaS = "03/05/2018 19:00:00";
		Date fechaSalida = dateformat.parse(fechaSalidaS);
		
		Registro registro = new RegistroTestDataBuilder().
				withVehiculo(vehiculo).
				withHoraEntrada(fechaEntrada).
				withHoraSalida(fechaSalida).
				build();

		float pagoEsperado = 6000;
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.calcularValorAPagar(registro);
				
		//Assert
		assertEquals(pagoEsperado,vigilanteServiceImpl.calcularValorAPagar(registro),0.0);
	}
	
	
	
}
