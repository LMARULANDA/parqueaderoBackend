package co.com.ceiba.parqueadero.testdatabuilder;

import java.util.Date;

import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;

public class RegistroTestDataBuilder {
	
	private static final Vehiculo VEHICULO = new Vehiculo();
	private static final Date HORA_ENTRADA = new Date();
	private static final Date HORA_SALIDA = null ;
	private static final float PAGO = 0;

	
	private Vehiculo vehiculo;
	private Date horaEntrada;
	private Date horaSalida;
	private float pago;
	
	public RegistroTestDataBuilder() {
		this.vehiculo = VEHICULO;
		this.horaEntrada = HORA_ENTRADA;
		this.horaSalida = HORA_SALIDA;
		this.pago = PAGO;
	}

	
	public RegistroTestDataBuilder withVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}

	public RegistroTestDataBuilder withHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
		return this;
	}

	public RegistroTestDataBuilder withHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
		return this;
	}
	public RegistroTestDataBuilder withPago(float pago) {
		this.pago = pago;
		return this;
	}
	public Registro build() {
		return new Registro(this.vehiculo,this.horaEntrada,this.horaSalida, this.pago);
	}


}
