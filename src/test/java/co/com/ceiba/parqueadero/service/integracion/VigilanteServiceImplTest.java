package co.com.ceiba.parqueadero.service.integracion;


import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import javax.ws.rs.core.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.VigilanteServiceImpl;
import co.com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;


@RunWith(SpringRunner.class)
@DataJpaTest

public class VigilanteServiceImplTest {
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private RegistroRepository registroRepository;
		
	@Test
	public void registrarIngreso() {
		//Arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().
				withPlaca("XYZ123").
				withTipoVehiculo("carro").
				build();
		LocalDate localDate = LocalDate.now();
		
		//Act
		VigilanteServiceImpl vigilanteServiceImpl = new VigilanteServiceImpl(vehiculoRepository,registroRepository);
		vigilanteServiceImpl.registrarIngreso(vehiculo);
		
		//Assert
		assertTrue(vigilanteServiceImpl.validarIngreso(vehiculo.getPlaca(), localDate));
	}
	

}
