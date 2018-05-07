package co.com.ceiba.parqueadero.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;

public class VigilanteServiceImplTest {
	
	private VehiculoRepository vehiculoRepository;
	private RegistroRepository registroRepository;
	
	
	@Before
	public void setup() {
		vehiculoRepository = mock(VehiculoRepository.class);
		registroRepository = mock(RegistroRepository.class);
	}
	
	@Test
	public void validarIngresoPlacaEspecialDiaHabil() {
		//Arrange
		LocalDate localDate = LocalDate.of(2018, 05, 07);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		//Assert
		 assertTrue(vigilanteServiceImpl.validarIngreso("ABC",localDate));
	}
	
	
	@Test
	public void validarIngresoPlacaEspecialDiaNoHabil() {
		//Arrange
		LocalDate localDate = LocalDate.of(2018, 05, 8);
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		//Assert
		 assertFalse(vigilanteServiceImpl.validarIngreso("ABC",localDate));
	}
	
}

