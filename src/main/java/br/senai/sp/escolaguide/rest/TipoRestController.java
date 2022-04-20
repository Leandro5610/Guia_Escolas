package br.senai.sp.escolaguide.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.senai.sp.escolaguide.annotation.Publico;
import br.senai.sp.escolaguide.model.Escola;
import br.senai.sp.escolaguide.model.TipoEscola;
import br.senai.sp.escolaguide.repository.TipoRepository;

public class TipoRestController {
	/*@Autowired
	private TipoRepository rep;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<TipoEscola> getEscolas(){
		return rep.findAll();
		
	}
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipoEscola> findEscola(@PathVariable("id") Long id){
		//busca o restaurante
		Optional<TipoEscola> escola=rep.findById(id);
		if(escola.isPresent()) {
			return ResponseEntity.ok(escola.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	
	}*/
}
