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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.escolaguide.model.Adimistrador;
import br.senai.sp.escolaguide.repository.AdmRepository;
import br.senai.sp.escolaguide.util.HashUtil;

@Controller
public class AdmController {
	// enjetar dependencia
	@Autowired
	private AdmRepository repository;
	
	@RequestMapping("formAdm")
	private String formAdm() {
		return "adm/formAdministrador";
	}
	@RequestMapping(value = "salvarAdm", method = RequestMethod.POST)
	private String salvarAdm(@Valid Adimistrador adm, BindingResult result, RedirectAttributes attr) {
		//verifica se houve erro na validação do objeto
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro","Verifique os campos");
			return "redirect:listarAdm/1";
		}
		try {
		//salva o administrador
		repository.save(adm);
		attr.addFlashAttribute("mensagemOk","Administrador Cadastrado com Sucesso ID:"+adm.getId());
		
		}catch(Exception e){
			attr.addFlashAttribute("mensagemErro","Ocorreu um Erro a cadastrar o Administrador");
		}
		return "redirect:listarAdm/1";	
	}
	//resquest mapping para listar informando a pagina desejada
	@RequestMapping("listarAdm/{page}")
	public String listar(Model model,@PathVariable("page") int page) {
		//cria um pageble co seis elementos por pagina ordenando os objetos pelo nome de forma ascendente
		PageRequest pageble = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC,"nome"));
		//cria a pagina atual atraves do repository
		Page<Adimistrador> pagina = repository.findAll(pageble);
		//descobrir o total de paginas
		int totalPages = pagina.getTotalPages();
		// cria uma lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for(int i =0; i< totalPages; i++) {
			pageNumbers.add(i+1);
		}
		// adicoina as variaveis na model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("pageAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPages", pageNumbers);
		//retornar para o HTML da lista
		return"adm/listaAdm";
	}
	@RequestMapping("alterarAdm")
	public String alterarAdm(Model model, Long id) {
		Adimistrador adm = repository.findById(id).get();
		model.addAttribute("admins", adm);
		return "forward:formAdm";
	}

	@RequestMapping("excluirAdm")
	public String excluirAdm(Long id) {
		repository.deleteById(id);
		return "redirect:listarAdm/1";
	}
}
