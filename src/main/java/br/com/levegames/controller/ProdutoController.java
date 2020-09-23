package br.com.levegames.controller;

import br.com.levegames.dao.ConsoleDAO;
import br.com.levegames.dao.ImagemProdutoDAO;
import br.com.levegames.dao.PerguntaRespostaProdutoDAO;
import br.com.levegames.dao.ProdutoDAO;
import br.com.levegames.model.Console;
import br.com.levegames.model.ImagemProduto;
import br.com.levegames.model.PerguntaRespostaProduto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping("/Backoffice/Produtos/{id}")
  public ModelAndView exibirAlterarProduto(@PathVariable("id") int id) {

    ModelAndView mv = new ModelAndView("backoffice-produtos-alterar");
    ProdutoDAO produtoDao = new ProdutoDAO();
    Produto p = produtoDao.getProdutos(id);

    ConsoleDAO consoleDao = new ConsoleDAO();
    List<Console> consoles = consoleDao.getConsoles();

    ImagemProdutoDAO imagensProdutoDAO = new ImagemProdutoDAO();
    List<ImagemProduto> listaImagens = imagensProdutoDAO.getImagensProduto(id);

    PerguntaRespostaProdutoDAO perguntasRespostasProdutoDAO = new PerguntaRespostaProdutoDAO();
    List<PerguntaRespostaProduto> listaPerguntasRespostas = perguntasRespostasProdutoDAO.getPerguntasRespostasProduto(id);

    mv.addObject("produto", p);
    mv.addObject("listaImagens", listaImagens);
    mv.addObject("listaPerguntasRespostas", listaPerguntasRespostas);
    mv.addObject("consoles", consoles);

    return mv;
  }

  @PutMapping("/Backoffice/Produtos/{id}")
  public ModelAndView alterarProduto(
          @PathVariable("id") int id,
          @ModelAttribute(value = "produto") Produto p,
          @RequestParam("imagem") String[] imagens,
          @RequestParam("pergunta") String[] perguntas,
          @RequestParam("resposta") String[] respostas) {

    ProdutoDAO produtoDao = new ProdutoDAO();
    produtoDao.alterarProduto(p);

    ImagemProdutoDAO imagemProdutoDao = new ImagemProdutoDAO();
    imagemProdutoDao.deletarImagensProduto(p.getId());

    PerguntaRespostaProdutoDAO perguntasRespostasProdutoDao = new PerguntaRespostaProdutoDAO();
    perguntasRespostasProdutoDao.deletarPerguntasRespostasProduto(p.getId());

    imagemProdutoDao.salvarImagensProduto(p.getId(), imagens);
    perguntasRespostasProdutoDao.salvarPerguntasRespostasProduto(p.getId(), perguntas, respostas);

    ModelAndView mv = new ModelAndView("backoffice-home");

    return mv;
  }

  @PostMapping("/Backoffice/Produtos/Novo")
  public ModelAndView adicionarProduto(
          @ModelAttribute(value = "produto") Produto p,
          @RequestParam("imagem") String[] imagens,
          @RequestParam("pergunta") String[] perguntas,
          @RequestParam("resposta") String[] respostas) {

    ProdutoDAO produtoDao = new ProdutoDAO();
    produtoDao.salvarProduto(p);

    int produto_id = produtoDao.getUltimoProduto();

    ImagemProdutoDAO imagemProdutoDao = new ImagemProdutoDAO();
    imagemProdutoDao.salvarImagensProduto(produto_id, imagens);

    PerguntaRespostaProdutoDAO perguntasRespostasProdutoDao = new PerguntaRespostaProdutoDAO();
    perguntasRespostasProdutoDao.salvarPerguntasRespostasProduto(produto_id, perguntas, respostas);

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
