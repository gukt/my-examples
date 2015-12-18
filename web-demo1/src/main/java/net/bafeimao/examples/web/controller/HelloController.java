package net.bafeimao.examples.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ktgu on 15/6/7.
 */
public class HelloController extends AbstractController{
//    @Override
//    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ModelAndView mv = new ModelAndView();
//
//        //添加模型数据 可以是任意的POJO对象
//        mv.addObject("message", "Hello World!");
//
//        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
//        mv.setViewName("hello");
//
//        return mv;
//    }

    // TODO 为什么该方法会调用两次呢？
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();

        //添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "Hello World!!!!");

        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("hello");

        return mv;
    }


}
