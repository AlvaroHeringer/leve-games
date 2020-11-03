package br.com.levegames.controller;

import br.com.levegames.model.Cliente;
import java.util.logging.Logger;
import java.util.List;
import java.util.Set;
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
public class ClienteController {

  @GetMapping("/Cliente/Novo")
  public ModelAndView exibirCadastro() {
    Cliente c = new Cliente();
    ModelAndView mv = new ModelAndView("cliente-novo");
    mv.addObject("cliente", c);
    return mv;
  }

}
