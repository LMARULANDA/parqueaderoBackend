package co.com.ceiba.parkinglot.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")
@Access(AccessType.FIELD)
public class VehicleEntity extends ParentEntity {

	private static final long serialVersionUID = 5007294535142484696L;
	
	
	@Column(name="license_plate", nullable = false, length = 255)
	 private String licensePlate;
	
	@Column(name="type_vehicle", nullable = false, length = 255)
	private String typeOfVehicle;
	
	@Column(name="displacement", nullable = true, length = 255)
	 private String displacement;

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}
	
	

}
