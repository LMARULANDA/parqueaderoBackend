package co.com.ceiba.parqueadero.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import co.com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

public class VehiculoTest {
	
	private static final String PLACA = "FHJ315";
	private static final String TIPO_VEHICULO = "Carro";
	private static final String CILINDRAJE ="";
	
	@Test
	public void crearVehiculoTest() {
		
		//arrange
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder().
				withPlaca(PLACA).
				withTipoVehiculo(TIPO_VEHICULO).
				withCilindraje(CILINDRAJE);
		
		Vehiculo vehiculo = vehiculoTestDataBuilder.build();
		assertEquals(PLACA,vehiculo.getPlaca());
		assertEquals(TIPO_VEHICULO,vehiculo.getTipoDeVehiculo());
		assertEquals(CILINDRAJE,vehiculo.getCilindraje());
		
	}

}
