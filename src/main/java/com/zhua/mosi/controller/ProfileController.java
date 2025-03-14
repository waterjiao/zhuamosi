package com.zhua.mosi.controller;

import com.zhua.mosi.Service.QuestionService;
import com.zhua.mosi.dto.PaginationDTO;
import com.zhua.mosi.mapper.UserMapper;
import com.zhua.mosi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                        HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        if ("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","回复");
        }
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies !=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findBytoken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user==null) return "redirect:/";


//        List<QuestionDTO> questionList = questionService.list();
//        model.addAttribute("questions",questionList);

        PaginationDTO paginationList = questionService.listPaging(user.getId(),page,size);
        model.addAttribute("paginations" ,paginationList);
        return "profile";
    }
}
