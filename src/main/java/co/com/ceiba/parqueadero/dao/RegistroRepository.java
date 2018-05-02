package co.com.ceiba.parqueadero.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.ceiba.parqueadero.model.Registro;


public interface RegistroRepository extends JpaRepository<Registro, Long> {
	
	@SuppressWarnings("unchecked")
	Registro save(Registro registro);

}
