package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.dto.AnswerDto;
import com.codessquad.qna.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserDtoFromSession;

@RestController // 리턴 데이터를 json타입으로 변환해줌
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {


    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("")
    public AnswerDto create(@PathVariable Long questionId, AnswerDto answerDto, HttpSession session) {
        return answerService.save(questionId, answerDto, getUserDtoFromSession(session));
    }

    @DeleteMapping("/{id}")
    public AnswerDto delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        return answerService.delete(id, getUserDtoFromSession(session));
    }
}
