package br.com.s2.mercadorias.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.s2.mercadorias.pojo.Propriedades;
import br.com.s2.mercadorias.service.PropriedadesService;

@Controller
@Scope("request")
public class PropriedadesController{
	
	@Lazy(true)
	@Autowired
	private PropriedadesService propriedadesService;

	@RequestMapping("/prop")
	public ModelAndView pagina(HttpServletResponse response) throws Exception{		
		ModelAndView model = new ModelAndView("prop");

		model.addObject("item_formulario", propriedadesService.ler());
		
		return model;
	}
	
	@RequestMapping("/prop/salvar")
	public ModelAndView salvar(@ModelAttribute("item_formulario") @Valid Propriedades itemFormulario) throws Exception{
		propriedadesService.inserir(itemFormulario);
		
		ModelAndView model = new ModelAndView("prop");
		model.addObject("item_formulario", propriedadesService.ler());
		return model;
	}
}
