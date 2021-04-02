package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.model.dto.QuestionDto;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(QuestionDto questionDto, User sessionedUser) {
        Question question = questionDto.toEntity();
        question.save(sessionedUser);
        questionRepository.save(question);
    }

    public void update(Long questionId, QuestionDto newQuestionDto, User sessionedUser) {
        Question question = verifyQuestion(questionId, sessionedUser);
        question.update(newQuestionDto.toEntity());
        questionRepository.save(question);
    }

    public boolean delete(Long questionId, User sessionedUser) {
        Question question = verifyQuestion(questionId, sessionedUser);
        boolean result = question.delete();
        if (result) {
            questionRepository.save(question);
        }
        return result;
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
    }

    public List<QuestionDto> findAll() {
        return questionRepository.findAllByDeletedFalse().stream()
                .map(QuestionDto::new)
                .collect(Collectors.toList());
    }

    public Question verifyQuestion(Long id, User sessionedUser) {
        Question question = findById(id);
        if (!question.matchWriter(sessionedUser)) {
            throw new IllegalUserAccessException();
        }
        return question;
    }
}
