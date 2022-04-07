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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.escolaguide.model.Escola;
import br.senai.sp.escolaguide.repository.EscolaRepository;
import br.senai.sp.escolaguide.repository.TipoRepository;

@Controller
public class EscolaController {
	@Autowired
	TipoRepository repository;
	@Autowired
	EscolaRepository esresp;
	
	@RequestMapping("formularioEscola")
	private String form(Model model) {
		model.addAttribute("tipos",repository.findAllByOrderByNomeAsc());
		return "escola/form";
	}
	@RequestMapping(value = "salvarEscola", method = RequestMethod.POST)
	private String salvarEscola(@Valid Escola escola, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		
		//esresp.save(escola);//
		System.out.println(fileFotos.length);
		return "redirect:formularioEscola";
	}
	@RequestMapping("listagemEscolas/{page}")
	public String listarEscolas(Model model, @PathVariable("page") int page) {
		
		// cria um pageble co seis elementos por pagina ordenando os objetos pelo nome
		// de forma ascendente
		PageRequest pageble = PageRequest.of(page - 1,10, Sort.by(Sort.Direction.ASC, "nome"));
		// cria a pagina atual atraves do repository
		Page<Escola> pagina = esresp.findAll(pageble);
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
		model.addAttribute("numPages", pageNumbers);
		

		// retornar para o HTML da lista
		return "escola/listaDeEscolas";
	}
	@RequestMapping("alteraEscola")
	public String alterarEscola(Model model, Long id) {
		Escola escola =	esresp.findById(id).get();
		model.addAttribute("escolas", escola);
		return "forward:formularioEscola";
	}

	@RequestMapping("excluiEscola")
	public String excluirEscola(Long id) {
		esresp.deleteById(id);
		return "redirect:listagemEscolas/1";
	}

}
