package org.langsford.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by trevyn on 1/17/15.
 */
@Controller
@RequestMapping("/")
public class HomeController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("viewName", "home");
        return mav;    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView mav = new ModelAndView("about");
        mav.addObject("viewName", "about");
        return mav;
    }

    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public ModelAndView work() {
        ModelAndView mav = new ModelAndView("work");
        mav.addObject("viewName", "work");
        return mav;
    }
}
