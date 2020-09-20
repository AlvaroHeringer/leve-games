
package br.com.levegames.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    
    @GetMapping("/Backoffice/Home")
    public ModelAndView mostrarTela() {
		
	ModelAndView mv = new ModelAndView("backoffice-home");
	return mv;
    }
    
}