package br.senai.sp.escolaguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.escolaguide.annotation.Publico;
import br.senai.sp.escolaguide.model.Escola;
import br.senai.sp.escolaguide.repository.EscolaRepository;


@RequestMapping("/api/escola")
@RestController
public class EscolaRestController {
	@Autowired
	private EscolaRepository repository;
	
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Escola> getEscolas(){
		return repository.findAll();
		
	}
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Escola> findEscola(@PathVariable("id") Long id){
		//busca o restaurante
		Optional<Escola> escola=repository.findById(id);
		if(escola.isPresent()) {
			return ResponseEntity.ok(escola.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	
	}
	
	@Publico
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET)
	public Iterable<Escola> findTipo(@PathVariable("idTipo") Long idTipo){
		return repository.findByTipoId(idTipo);
	}
	@Publico
	@RequestMapping(value = "/internet/{wifi}", method = RequestMethod.GET)
	public Iterable<Escola> findWifi(@PathVariable("wifi") boolean wifi){
		return repository.findByWifi(wifi);
	}	
	@Publico
	@RequestMapping(value = "/endereco/{bairro}", method = RequestMethod.GET)
	public Iterable<Escola> findBairro(@PathVariable("bairro") String bairro){
		return repository.findByBairro(bairro);
	}
	
}
