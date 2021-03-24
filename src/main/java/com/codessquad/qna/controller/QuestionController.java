package com.codessquad.qna.controller;

import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping
    public String questionList(Model model) {
        Iterable<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping
    public String newQuestion(Question question) {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeString = dateTime.format(dateTimeFormatter);
        question.setDateTime(dateTimeString);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String qnaInputPage() {
        return "questionInputForm";
    }

    @GetMapping("/{id}")
    public ModelAndView question(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("questionDetail");
        modelAndView.addObject("question", questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new));
        return modelAndView;
    }
}
