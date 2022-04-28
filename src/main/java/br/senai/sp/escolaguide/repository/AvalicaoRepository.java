package br.senai.sp.escolaguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.escolaguide.model.Avaliacao;
import br.senai.sp.escolaguide.model.Escola;

public interface AvalicaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	public List<Avaliacao> findByEscId(Long idEscola);
}
