package br.senai.sp.escolaguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.escolaguide.model.Escola;

public interface EscolaRepository extends PagingAndSortingRepository<Escola, Long>{
	public List<Escola> findByTipoId(Long idTipo);
	
	public List<Escola> findByWifi(boolean wifi);
	public List<Escola> findByBairro(String bairro);
	
	/*
	 * @Query("Select e From Escola e Where e.fotos is not null") public
	 * List<Escola> findByFotos(@Param("e") String foto);
	 */

}
