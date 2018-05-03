package co.com.ceiba.parqueadero.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConvertirLocalDate {

	public Date convertirLocalDateTimeADate() {
		LocalDateTime horaActual = LocalDateTime.now();
		Instant instant = horaActual.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

}
