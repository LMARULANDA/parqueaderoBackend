package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.VigilanteService;
import co.com.ceiba.parqueadero.util.RestResponse;


@RestController
public class VigilanteController {

	@Autowired
	protected VigilanteService vigilanteService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/registrarIngreso", method = RequestMethod.POST)
	public void registrarIngreso(@RequestBody String vehiculoJson)
			throws IOException {
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);
		
		this.vigilanteService.registrarIngreso(vehiculo);

	}

	@RequestMapping(value = "/registrarSalida", method = RequestMethod.POST)
	public Registro registrarSalida(@RequestBody String vehiculoJson)
			throws IOException {
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);

		return this.vigilanteService.registrarSalida(vehiculo);
	}
	
	@RequestMapping(value = "/consultarVehiculos", method = RequestMethod.GET)
	public List<Registro> consultarVehiculos(){
		return this.vigilanteService.consultarVehiculos();
		
	}

}
