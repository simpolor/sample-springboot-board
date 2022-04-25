package io.simpolor.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @GetMapping
    public ModelAndView message(ModelAndView mav, HttpServletRequest request, Locale locale) {


        // RequestMapingHandler로 부터 받은 Locale 객체를 출력해 봅니다.
        mav.addObject("clientLocale", locale);

        // localeResolver 로부터 Locale 을 출력해 봅니다.
        mav.addObject("sessionLocale", localeResolver.resolveLocale(request));

        // JSP 페이지에서 EL 을 사용해서 arguments 를 넣을 수 있도록 값을 보낸다.
        mav.addObject("siteCount", messageSource.getMessage("msg.first", null, locale));

        mav.setViewName("message");
        return mav;
    }

}
