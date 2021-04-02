package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.Answer;
import com.codessquad.qna.model.Question;
import com.codessquad.qna.model.User;
import com.codessquad.qna.model.dto.AnswerDto;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;


@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public AnswerDto save(Long questionId, AnswerDto answerDto, User sessionedUser) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.QUESTION_NOT_FOUND));
        Answer answer = answerDto.toEntity();
        answer.save(sessionedUser, question);
        answerRepository.save(answer);
        return new AnswerDto(answer);
    }

    public void update(Long answerId, AnswerDto updatedAnswerDto, User sessionedUser) {
        Answer answer = getAnswer(answerId, sessionedUser);
        answer.update(updatedAnswerDto.toEntity());
        answerRepository.save(answer);
    }

    public AnswerDto delete(Long answerId, User sessionedUser) {
        Answer answer = getAnswer(answerId, sessionedUser);
        answer.delete();
        answerRepository.save(answer);
        return new AnswerDto(answer);
    }

    public Answer getAnswer(Long answerId, User sessionedUser) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.ANSWER_NOT_FOUND));
        if (!answer.matchWriter(sessionedUser)) {
            throw new IllegalUserAccessException();
        }
        return answer;
    }
}
