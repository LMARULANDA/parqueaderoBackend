package co.com.ceiba.parqueadero.service;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;


import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;
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
	public void esPlacaEspecial() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
					withPlaca("AIS575").
					withTipoVehiculo("Carro").
					withCilindraje("")
					.build();	
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
	
	
	
}

