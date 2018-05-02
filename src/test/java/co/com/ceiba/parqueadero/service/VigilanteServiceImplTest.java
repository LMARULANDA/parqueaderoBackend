package co.com.ceiba.parqueadero.service;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
	public void registrarIngreso() {
		
		//Arrange
		
		//act
		
		//assert
		
		
	}

}
