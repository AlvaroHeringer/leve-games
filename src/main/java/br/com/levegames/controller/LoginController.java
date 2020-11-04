package br.com.levegames.controller;

import br.com.levegames.model.Cliente;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @GetMapping("/Login")
  public ModelAndView exibirHome() {
    Cliente c = new Cliente();
    ModelAndView mv = new ModelAndView("login");
    mv.addObject("cliente", c);
    return mv;
  }

}
