package br.com.levegames.controller;

import br.com.levegames.dao.ClienteDAO;
import br.com.levegames.model.ProdutoCarrinho;
import br.com.levegames.dao.ConsoleDAO;
import br.com.levegames.dao.EnderecoDAO;
import br.com.levegames.dao.ImagemProdutoDAO;
import br.com.levegames.dao.PerguntaRespostaProdutoDAO;
import br.com.levegames.dao.ProdutoDAO;
import br.com.levegames.dao.VendaDAO;
import br.com.levegames.dao.VendaProdutoDAO;
import br.com.levegames.model.Cliente;
import br.com.levegames.model.Console;
import br.com.levegames.model.Endereco;
import br.com.levegames.model.ImagemProduto;
import br.com.levegames.model.MeioPagamento;
import br.com.levegames.model.PerguntaRespostaProduto;
import br.com.levegames.model.Produto;
import br.com.levegames.model.Venda;
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
public class CheckoutController {

  @GetMapping("/Checkout")
  public ModelAndView mostrarTela(HttpServletRequest request) {

    ModelAndView mv = null;
    EnderecoDAO enderecosDao = new EnderecoDAO();

    HttpSession sessao = request.getSession();
    if (sessao.getAttribute("cliente") == null) {

      mv = new ModelAndView("redirect:/Login");

    } else {

      Cliente c = (Cliente) sessao.getAttribute("cliente");
      Endereco e = (Endereco) sessao.getAttribute("endereco");
      List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) sessao.getAttribute("carrinho-compras");
      double total = (Double) sessao.getAttribute("total");
      MeioPagamento pagamento = (MeioPagamento) sessao.getAttribute("pagamento");

      mv = new ModelAndView("checkout");
      mv.addObject("cliente", c);
      mv.addObject("endereco", e);
      mv.addObject("carrinho", carrinho);
      mv.addObject("pagamento", pagamento);
      mv.addObject("total", sessao.getAttribute("total"));
    }

    return mv;

  }

  @PostMapping("/Checkout")
  public ModelAndView finalizarCompra(HttpServletRequest request) {

    ModelAndView mv = null;
    EnderecoDAO enderecosDao = new EnderecoDAO();
    VendaDAO vendaDao = new VendaDAO();
    VendaProdutoDAO vendaProdutoDao = new VendaProdutoDAO();

    HttpSession sessao = request.getSession();

    Cliente c = (Cliente) sessao.getAttribute("cliente");
    MeioPagamento pagamento = (MeioPagamento) sessao.getAttribute("pagamento");
    Endereco e = (Endereco) sessao.getAttribute("endereco");
    List<ProdutoCarrinho> carrinho = (List<ProdutoCarrinho>) sessao.getAttribute("carrinho-compras");
    Double total = (Double) sessao.getAttribute("total");

    Venda v = new Venda();
    v.setCliente_id(c.getId());
    v.setEndereco_id(e.getId());
    if (pagamento.getMeio_pagamento().equals("boleto")) {
      v.setMeio_pagamento_id(1);
    } else {
      v.setMeio_pagamento_id(2);
    }
    v.setStatus_id(1);
    v.setTotal(total);
    v.setObs(pagamento.getQtd_parcelas());

    vendaDao.salvarVenda(v);
    int venda_id = vendaDao.getUltimaVenda();
    vendaProdutoDao.salvarVendaProdutos(venda_id, carrinho);
    v.setId(venda_id);
    mv = new ModelAndView("venda-finalizada");
    mv.addObject("venda", v);
    mv.addObject("pagamento", pagamento);
    mv.addObject("total", sessao.getAttribute("total"));

    return mv;

  }

}
