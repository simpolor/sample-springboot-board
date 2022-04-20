package io.simpolor.jstree.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(("/menu"))
@RequiredArgsConstructor
public class MenuController {

	@GetMapping
	public ModelAndView menu(ModelAndView mav) {

		mav.setViewName("menu");
		return mav;
	}
}
