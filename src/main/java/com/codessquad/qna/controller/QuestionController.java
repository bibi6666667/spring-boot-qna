package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.dto.QuestionDto;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.utils.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String viewMain(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "index";
    }

    @GetMapping("/form")
    public String qnaInputPage(HttpSession session) {
        isLoginUser(session);
        return "qna/questionInputForm";
    }

    @PostMapping("/form")
    public String newQuestion(QuestionDto questionDto, HttpSession session) {
        questionService.save(questionDto, getUserFromSession(session));
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String viewQuestion(@PathVariable Long questionId, Model model) {
        Question question = questionService.findById(questionId);
        model.addAttribute("question", new QuestionDto(question));
        return "qna/questionDetail";
    }

    @GetMapping("/{questionId}/updateForm")
    public String qnaUpdatePage(@PathVariable Long questionId, Model model, HttpSession session) {
        Question question = questionService.verifyQuestion(questionId, getUserFromSession(session));
        model.addAttribute("question", new QuestionDto(question));
        return "qna/questionUpdateForm";
    }

    @PutMapping("/{questionId}/updateForm")
    public String updateQuestion(@PathVariable Long questionId, QuestionDto updatedQuestionDto, HttpSession session) {
        questionService.update(questionId, updatedQuestionDto, getUserFromSession(session));
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId, Model model, HttpSession session) {
        boolean result = questionService.delete(questionId, getUserFromSession(session));
        if (!result) {
            Question question = questionService.findById(questionId);
            model.addAttribute("question", question);
            model.addAttribute("errorMessage", ErrorMessage.DELETE_FAILED);
            return "qna/questionDetail";
        }
        return "redirect:/";
    }
}
