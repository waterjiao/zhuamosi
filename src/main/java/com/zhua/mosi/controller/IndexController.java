package com.zhua.mosi.controller;

import com.zhua.mosi.Service.QuestionService;
import com.zhua.mosi.dto.PaginationDTO;
import com.zhua.mosi.dto.QuestionDTO;
import com.zhua.mosi.mapper.UserMapper;
import com.zhua.mosi.model.Question;
import com.zhua.mosi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies !=null&&cookies.length!=0)
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findBytoken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

//        List<QuestionDTO> questionList = questionService.list();
//        model.addAttribute("questions",questionList);

        PaginationDTO paginationList = questionService.listPaging(page,size);
        model.addAttribute("paginations" ,paginationList);
        return "index";
    }
}
