package co.com.ceiba.parqueadero.testdatabuilder;

import co.com.ceiba.parqueadero.model.Vehiculo;

public class VehiculoTestDataBuilder {
	
	
	private static final String PLACA = "FHJ315";
	private static final String TIPO_VEHICULO ="Carro";
	private static final int CILINDRAJE = 0;
	
	private String placa;
	private String tipoVehiculo;
	private int cilindraje;
	
	public VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.tipoVehiculo = TIPO_VEHICULO;
		this.cilindraje = CILINDRAJE;
	}
	
	public VehiculoTestDataBuilder withPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder withTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public VehiculoTestDataBuilder withCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}
	public Vehiculo build() {
		return new Vehiculo(this.placa,this.tipoVehiculo,this.cilindraje);
	}

}
