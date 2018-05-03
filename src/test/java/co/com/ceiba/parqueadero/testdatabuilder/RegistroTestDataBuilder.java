package co.com.ceiba.parqueadero.testdatabuilder;

import java.util.Date;

import co.com.ceiba.parqueadero.model.Vehiculo;

public class RegistroTestDataBuilder {
	
	private static final Date horaEntrada = null;
	private static final Date horaSalida = null ;
	private static final float pago = 0;
	
	private static final Vehiculo vehiculo = new Vehiculo();
	public static Vehiculo getVehiculo() {
		return vehiculo;
	}
	public static Date getHoraentrada() {
		return horaEntrada;
	}
	public static Date getHorasalida() {
		return horaSalida;
	}
	public static float getPago() {
		return pago;
	}
	

}
