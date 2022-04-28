package br.senai.sp.escolaguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.escolaguide.model.Escola;
import br.senai.sp.escolaguide.model.Usuario;

public interface UsuarioRepository extends  PagingAndSortingRepository<Usuario, Long> {

}
