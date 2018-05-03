package co.com.ceiba.parqueadero.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ConvertirDate {

	public Date convertirLocalDateTimeADate(LocalDateTime localdatetime) {
		Instant instant = localdatetime.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	
	public LocalDateTime convertirDateALocalDateTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	public LocalTime convertirLocalDateTimeALocalTime(LocalDateTime localdatetime) {
		return localdatetime.toLocalTime();
		
	}
	
	public LocalTime convertirDateALocalTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
	}
	
}


