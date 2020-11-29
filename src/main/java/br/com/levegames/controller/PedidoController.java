package br.com.levegames.controller;

import br.com.levegames.dao.ConsoleDAO;
import br.com.levegames.dao.ImagemProdutoDAO;
import br.com.levegames.dao.PedidoResumidoDAO;
import br.com.levegames.dao.PerguntaRespostaProdutoDAO;
import br.com.levegames.dao.ProdutoDAO;
import br.com.levegames.model.Cliente;
import br.com.levegames.model.Console;
import br.com.levegames.model.ImagemProduto;
import br.com.levegames.model.PedidoResumido;
import br.com.levegames.model.PerguntaRespostaProduto;
import br.com.levegames.model.Produto;
import br.com.levegames.model.ProdutoCarrinho;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PedidoController {

  @GetMapping("/Meus-pedidos")
  public ModelAndView mostrarTela(HttpServletRequest request) {

    HttpSession sessao = request.getSession();

    Cliente c = (Cliente) sessao.getAttribute("cliente");
    
    
    ModelAndView mv = new ModelAndView("meus-pedidos");
    PedidoResumidoDAO pedidoResumidoDao = new PedidoResumidoDAO();
    
    List<PedidoResumido> pedidos = pedidoResumidoDao.getPedidos(c.getId());
    mv.addObject("pedidos", pedidos);
    return mv;
  }
  
  

  
  @GetMapping("/Meus-pedidos/{id}")
  public ModelAndView exibirDetalhesPedido(@PathVariable("id") int id, HttpServletRequest request) {

    ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();

    ProdutoDAO produtoDao = new ProdutoDAO();
    ImagemProdutoDAO imagemDao = new ImagemProdutoDAO();

    Produto p = produtoDao.getProdutos(id);
    List<ImagemProduto> img = imagemDao.getImagensProduto(id);

    produtoCarrinho.setId(id);
    produtoCarrinho.setNome(p.getNome());
    produtoCarrinho.setUrl_imagem(img.get(0).getUrl_imagem());
    produtoCarrinho.setQtde(1);
    produtoCarrinho.setPreco(p.getPreco());

    HttpSession sessao = request.getSession();
    List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) sessao.getAttribute("carrinho-compras");
    if (carrinho == null) {
      carrinho = new ArrayList<ProdutoCarrinho>();
    }
    carrinho.add(produtoCarrinho);
    sessao.setAttribute("carrinho-compras", carrinho);

    ModelAndView mv = new ModelAndView("redirect:/Carrinho");
    
    
    

    mv.addObject("cep", sessao.getAttribute("cep"));
    
    mv.addObject("carrinho", carrinho);
    return mv;
  }


}
