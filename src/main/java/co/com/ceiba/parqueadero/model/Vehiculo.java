package co.com.ceiba.parqueadero.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="vehiculo")
@Access(AccessType.FIELD)
public class Vehiculo extends ParentEntity {

	private static final long serialVersionUID = 5007294535142484696L;
	
	
	@Column(name="placa", nullable = false, length = 20)
	 private String placa;
	
	@Column(name="tipo_vehiculo", nullable = false, length = 20)
	private String tipoDeVehiculo;
	
	@Column(name="cilindraje", nullable = true, length = 50)
	 private String cilindraje;
	
	public Vehiculo() {
		
	}
	public Vehiculo(String placa, String tipoDeVehiculo, String cilindraje) {
		super();
		this.placa = placa;
		this.tipoDeVehiculo = tipoDeVehiculo;
		this.cilindraje = cilindraje;
	}

	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipoDeVehiculo() {
		return tipoDeVehiculo;
	}

	public void setTipoDeVehiculo(String tipoDeVehiculo) {
		this.tipoDeVehiculo = tipoDeVehiculo;
	}

	public String getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(String cilindraje) {
		this.cilindraje = cilindraje;
	}



}
