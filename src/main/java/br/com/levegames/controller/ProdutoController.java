
package br.com.levegames.controller;

import br.com.levegames.dao.ProdutoDAO;
import br.com.levegames.model.Produto;
import java.util.logging.Logger;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {
    
    @GetMapping("/backoffice/produtos")
    public ModelAndView mostrarTela() {
		
	ModelAndView mv = new ModelAndView("backoffice-produtos");
	ProdutoDAO produtoDao = new ProdutoDAO();
	List<Produto> produtos = produtoDao.getProdutos();
	for(Produto p: produtos) {
	    p.toString();
	}
	mv.addObject("games", produtos);
	return mv;
    }
    
    @RequestMapping(value="/backoffice/produtos/{id}", method=RequestMethod.GET)
    public ModelAndView mostrarTelaDelecao() {
		
	ModelAndView mv = new ModelAndView("backoffice-home");
	
	return mv;
    }
    
}