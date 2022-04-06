package br.senai.sp.escolaguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.escolaguide.model.TipoEscola;

public interface TipoRepository extends PagingAndSortingRepository<TipoEscola, Long>{
	
@Query("SELECT e FROM TipoEscola e WHERE  e.palavrasChave LIKE %:p%")	
public List<TipoEscola> buscarKeyWord(@Param("p") String palavrasChave);

public List<TipoEscola> findAllByOrderByNomeAsc();
}
