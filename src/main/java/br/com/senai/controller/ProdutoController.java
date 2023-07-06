package br.com.senai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.senai.model.Produto;
import br.com.senai.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/inicial")
	public String paginaInicial() {
		return "index.html";
	}
	
	@GetMapping("/produto")
	public String listaProdutos(Model model) {
		
		List<Produto> produtos = produtoRepository.findAll();
		model.addAttribute("produtos", produtos);
		return "produtos";
	}
	
	@GetMapping("/formularioDeCadastro")
	public String mostrarFormulario(Produto produto) {
		return "adicionar_produto";
	}
	
	@PostMapping("/adicionarProduto")
	public String adicionaProduto(@Valid Produto produto
			, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/adicionar_produto";
		}
		produtoRepository.save(produto);
		return "redirect:/produto";
	}
	
	@GetMapping("/editar/{id}")
	public String mostrarFormularioAtualizacao(
			@PathVariable("id") long id, Model model) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Identificador não é válido" + id));
				model.addAttribute("produto", produto);
		return "atualizar_produto";
	}
	
	@PostMapping("/atualizar/{id}")
	public String atualizaProduto(@PathVariable("id")
		long id, @Valid Produto produto, BindingResult result, Model model
			) {
		if(result.hasErrors()) {
			produto.setId(id);
			return "atualizar_produto";
		}
		produtoRepository.save(produto);
		return "redirect:/produto";
	}
	
	@GetMapping("/delete/{id}")
	public String deletarProduto(@PathVariable("id") long id, Model model) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Identificador não é válido" + id));
				produtoRepository.delete(produto);
		return "redirect:/produto";
	}

}
