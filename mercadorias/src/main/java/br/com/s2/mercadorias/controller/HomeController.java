package br.com.s2.mercadorias.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("request")
public class HomeController {

	@RequestMapping("/home.html")
	public ModelAndView pagina(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		ModelAndView model = new ModelAndView("home");
		return model;
	}
}
