package br.com.levegames.controller;

import br.com.levegames.dao.ConsoleDAO;
import br.com.levegames.dao.ProdutoDAO;
import br.com.levegames.model.Console;
import br.com.levegames.model.Produto;
import java.util.logging.Logger;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {

  @GetMapping("/Backoffice/Produtos")
  public ModelAndView mostrarTela() {

    ModelAndView mv = new ModelAndView("backoffice-produtos");
    ProdutoDAO produtoDao = new ProdutoDAO();
    List<Produto> produtos = produtoDao.getProdutos();
    mv.addObject("games", produtos);
    return mv;
  }

  @GetMapping("/Backoffice/Produtos/Novo")
  public ModelAndView exibirCadastro() {

    Produto p = new Produto();
   
    ConsoleDAO consoleDao = new ConsoleDAO();
    List<Console> consoles = consoleDao.getConsoles();
    
    ModelAndView mv = new ModelAndView("backoffice-produtos-novo");
    
    mv.addObject("consoles", consoles);
    mv.addObject("produto", p);

    return mv;
  }

  @PostMapping("/Backoffice/Produtos/Novo")
  public ModelAndView adicionarProduto(@ModelAttribute(value="produto") Produto p) {
    
    ProdutoDAO produtoDao = new ProdutoDAO();
    produtoDao.salvarProduto(p);
    
    
    ModelAndView mv = new ModelAndView("backoffice-home");
    
    return mv;
  }

  @GetMapping("/Backoffice/Produtos/Delete/{id}")
  public ModelAndView removeProduto(@PathVariable("id") long id) {

    ProdutoDAO produtoDao = new ProdutoDAO();
    produtoDao.removeProduto(id);

    ModelAndView mv = new ModelAndView("backoffice-produtos");

    return mv;
    
  }

}