package br.senai.sp.escolaguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.senai.sp.escolaguide.model.TipoEscola;
import br.senai.sp.escolaguide.repository.TipoRepository;

@Controller
public class TipoController {
	@Autowired
	private TipoRepository repository;

	@RequestMapping("formEscola")
	private String formAdm() {
		return "tiposEscolas/formTipo";
	}

	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	private String salvarTipo(@Valid TipoEscola escola) {
		repository.save(escola);
		return "redirect:listarEscolas/1";
	}

	@RequestMapping("listarEscolas/{totalElem}/{page}")
	public String listar(Model model, @PathVariable("page") int page, @PathVariable("totalElem") int totalElem) {
		
		// cria um pageble co seis elementos por pagina ordenando os objetos pelo nome
		// de forma ascendente
		PageRequest pageble = PageRequest.of(page - 1,totalElem, Sort.by(Sort.Direction.ASC, "nome"));
		// cria a pagina atual atraves do repository
		Page<TipoEscola> pagina = repository.findAll(pageble);
		// descobrir o total de paginas
		int totalPages = pagina.getTotalPages();
		// cria uma lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		// adicoina as variaveis na model
		model.addAttribute("escolas", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("totalElemento",totalElem);
		model.addAttribute("numPages", pageNumbers);
		

		// retornar para o HTML da lista
		return "tiposEscolas/listaEscolas";
	}

	


	@RequestMapping("alterarEscola")
	public String alterarAdm(Model model, Long id) {
		TipoEscola escola = repository.findById(id).get();
		model.addAttribute("escolas", escola);
		return "forward:formEscola";
	}

	@RequestMapping("excluirEscola")
	public String excluirAdm(Long id) {
		repository.deleteById(id);
		return "redirect:listarEscolas/1";
	}

	@RequestMapping("buscarChave")
	public String buscarChave(String palavrasChave, Model model) {
		model.addAttribute("escolas", repository.buscarKeyWord("%" + palavrasChave + "%"));
		return "tiposEscolas/listaEscolas";
	}

}
