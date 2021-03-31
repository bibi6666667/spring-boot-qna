package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.service.AnswerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@RestController // 리턴 데이터를 json타입으로 변환해줌
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {


    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, Answer answer, HttpSession session) {
        return answerService.save(questionId, answer, getUserFromSession(session));
    }

}
