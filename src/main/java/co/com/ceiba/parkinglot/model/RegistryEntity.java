package co.com.ceiba.parkinglot.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="registry")
@Access(AccessType.FIELD)
public class RegistryEntity extends ParentEntity {

	private static final long serialVersionUID = 7462975128096900921L;
	
	@ManyToOne
	@JoinColumn(name="id_vehiculo", referencedColumnName="id")
	private VehicleEntity vehicle;
	
	
	@Column(name="checkin_time", nullable = false )
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkinTime;
	
	@Column(name="checkout_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkoutTime;
	
	@Column(name="total_price", nullable = true)
	private Float totalPrice;
	
	public Date getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	public Date getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
