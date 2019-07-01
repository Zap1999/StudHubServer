package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionIdOrderByCreationDateAsc(Integer questionId);

}
