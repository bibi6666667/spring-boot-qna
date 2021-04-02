package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.dto.AnswerDto;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String createAnswer(@PathVariable Long questionId, AnswerDto answerDto, HttpSession session) {
        answerService.save(questionId, answerDto, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{answerId}/updateForm")
    public String answerUpdatePage(@PathVariable Long answerId, Model model, HttpSession session) {
        Answer answer = answerService.getAnswer(answerId, getUserFromSession(session));
        model.addAttribute("answers", new AnswerDto(answer));
        return "qna/answerUpdateForm";
    }

    @PutMapping("/{answerId}/updateForm")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, AnswerDto updatedAnswerDto, HttpSession session) {
        answerService.update(answerId, updatedAnswerDto, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        answerService.delete(answerId, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }
}
