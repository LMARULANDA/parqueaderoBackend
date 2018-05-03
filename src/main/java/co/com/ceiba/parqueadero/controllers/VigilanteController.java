package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;
import java.util.List;

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

	// este no debe de ir como servicio Rest.sino dentro de save.
	// save se puede cambiar por otro nombre
	@RequestMapping(value = "/agregarVehiculo", method = RequestMethod.POST)
	public Vehiculo agregarVehiculo(@RequestBody String vehiculoJson)
			throws JsonParseException, JsonMappingException, IOException {
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);

		this.vigilanteService.save(vehiculo);
		return this.vigilanteService.save(vehiculo);
	}

	@RequestMapping(value = "/registrarIngreso", method = RequestMethod.POST)
	public Registro registrarIngreso(@RequestBody String vehiculoJson)
			throws JsonParseException, JsonMappingException, IOException {
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);

		this.vigilanteService.registrarIngreso(vehiculo);
		return this.vigilanteService.registrarIngreso(vehiculo);

	}

	@RequestMapping(value = "/registrarSalida", method = RequestMethod.POST)
	public Registro registrarSalida(@RequestBody String vehiculoJson)
			throws JsonParseException, JsonMappingException, IOException {
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);

		return this.vigilanteService.registrarSalida(vehiculo);
	}
	
	@RequestMapping(value = "/consultarVehiculos", method = RequestMethod.GET)
	public List<Registro> consultarVehiculos(){
		return this.vigilanteService.consultarVehiculos();
		
	}
}
