package br.senai.sp.escolaguide.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.escolaguide.annotation.Privado;
import br.senai.sp.escolaguide.annotation.Publico;
import br.senai.sp.escolaguide.model.Erro;
import br.senai.sp.escolaguide.model.Escola;
import br.senai.sp.escolaguide.model.Usuario;
import br.senai.sp.escolaguide.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository rep;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario user) {
		try {
			// salvar o usuaroio no BD
			rep.save(user);
			// retorna codigo 201 com a URL para cesso no location e usuario inserido no
			// corpo da resposta
			return ResponseEntity.created(URI.create("/" + user.getId())).body(user);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constrain: Registro já Existe");
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constrain: Registro já Existe");
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Privado
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findUser(@PathVariable("idUser") Long idUser) {
		// busca o restaurante
		Optional<Usuario> usuario = rep.findById(idUser);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario user, @PathVariable("id") Long id){
		//valida o ID
		if(id != user.getId()) {
			throw new RuntimeException("Id Inválido");
		}
		//salva o usuario no BD
		rep.save(user);
		//criar um cabeçalho Http
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("/api/usuario"));
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}
	@Privado
	@RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
	public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Long id){
		rep.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
