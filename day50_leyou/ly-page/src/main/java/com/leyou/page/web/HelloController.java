package com.leyou.page.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String toHello(Model model){
        model.addAttribute("msg","hello,thymeleaf!");

        //普通字符串被当成视图名称，结合前缀和后缀寻找视图 引入thymeleaf后会自动找templates下的hello.html
        return "hello";
    }
}
