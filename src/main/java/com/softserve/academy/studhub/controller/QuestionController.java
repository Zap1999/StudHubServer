package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private IQuestionService questionService;


    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getAllQuestions() {

        return questionService.sortByAge();
    }


    @GetMapping("/{id}")
    public Question showQuestionPage(@PathVariable Integer id) {
        return questionService.findById(id);
    }

    @GetMapping("/search/{keywords}")
    public List<Question> getSearched(@PathVariable String[] keywords, Pageable pageable) {
        return questionService.search(keywords, pageable).getContent();
    }


    @GetMapping("/tagged/{tags}")
    public List<Question> getAllSortByTags(@PathVariable String[] tags, Pageable pageable) {
        return questionService.sortByTags(tags, pageable);
    }

    @PutMapping("/{questionId}/edit")
    //@PreAuthorize("@questionServiceImpl.findById(#questionId).getUser().getUsername() == principal.username")
    public Question editQuestion(@PathVariable Integer questionId, @RequestBody Question question) {

        return questionService.update(questionId, question);
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('USER')")
    public Question createQuestion(@Valid @RequestBody Question question) {

        //return questionService.save(question, principal);
        return questionService.saveNoUser(question);
    }

    @DeleteMapping("/{questionId}/delete")
    //@PreAuthorize("hasRole('ADMIN') or @questionServiceImpl.findById(#questionId).getUser().getUsername()== principal.username")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer questionId) {

        return ResponseEntity.ok(questionService.deleteById(questionId));
    }


}
