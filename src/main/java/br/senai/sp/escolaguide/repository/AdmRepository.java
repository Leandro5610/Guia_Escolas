package br.senai.sp.escolaguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.escolaguide.model.Adimistrador;

public interface AdmRepository extends PagingAndSortingRepository<Adimistrador, Long>{
	
}
