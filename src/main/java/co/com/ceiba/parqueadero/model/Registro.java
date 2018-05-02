package co.com.ceiba.parqueadero.model;


import java.time.LocalDateTime;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="registro")
@Access(AccessType.FIELD)
public class Registro extends ParentEntity {

	private static final long serialVersionUID = 7462975128096900921L;
	
	@ManyToOne
	@JoinColumn(name="id_vehiculo", referencedColumnName="id")
	private Vehiculo vehiculo;
	
	@Column(name="hora_entrada", nullable = false )
	private LocalDateTime horaEntrada;
	
	@Column(name="hora_salida", nullable = true)
	private LocalDateTime horaSalida;
	
	@Column(name="pago", nullable = true)
	private Float pago;

	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public LocalDateTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalDateTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public LocalDateTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalDateTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public Float getPago() {
		return pago;
	}

	public void setPago(Float precio) {
		this.pago = precio;
	}
	
	
}
