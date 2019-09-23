package com.zhua.mosi.Service;

import com.zhua.mosi.dto.PaginationDTO;
import com.zhua.mosi.dto.QuestionDTO;
import com.zhua.mosi.mapper.QuestionMapper;
import com.zhua.mosi.mapper.UserMapper;
import com.zhua.mosi.model.Question;
import com.zhua.mosi.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private QuestionMapper questionMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public PaginationDTO listPaging(Integer page, Integer size) {
        Integer totalPage;
        if(questionMapper.count()%size==0) totalPage = questionMapper.count()/size;
        else totalPage = questionMapper.count()/size+1;
        if(page>totalPage) page=totalPage;
        if(page<1) page=1;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listPaging(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalPage,page,size);

        return paginationDTO;
    }
}
