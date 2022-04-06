package br.senai.sp.escolaguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.escolaguide.model.Escola;

public interface EscolaRepository extends PagingAndSortingRepository<Escola, Long>{

}
