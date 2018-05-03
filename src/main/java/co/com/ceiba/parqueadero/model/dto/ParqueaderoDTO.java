package co.com.ceiba.parqueadero.model.dto;

public class ParqueaderoDTO {
	
	public static final int CANTIDAD_MAXIMA_CARROS = 20;
	public static final int CANTIDAD_MAXIMA_MOTOS = 10;
	
	private int cantidadDisponibleMotos = CANTIDAD_MAXIMA_MOTOS;
	private int cantidadDisponibleCarros = CANTIDAD_MAXIMA_CARROS;
	
	
	public int getCantidadDisponibleMotos() {
		return cantidadDisponibleMotos;
	}
	public void setCantidadDisponibleMotos(int cantidadDisponibleMotos) {
		this.cantidadDisponibleMotos = cantidadDisponibleMotos;
	}
	public int getCantidadDisponibleCarros() {
		return cantidadDisponibleCarros;
	}
	public void setCantidadDisponibleCarros(int cantidadDisponibleCarros) {
		this.cantidadDisponibleCarros = cantidadDisponibleCarros;
	}
	
}


