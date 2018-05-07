package co.com.ceiba.parqueadero.service;

public class FactoryCelda {
	
	public static Celda establecerCelda(String tipoVehiculo) {
		if( tipoVehiculo == VigilanteServiceImpl.MOTO ) {
			return new CeldaMoto();
		}else {
			return new CeldaCarro();
		}
	}

}
