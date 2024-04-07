package com.example.QuizApp.controller;import com.example.QuizApp.model.Question;import com.example.QuizApp.service.QuestionService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.util.List;@RestController@RequestMapping("question")public class QuestionController {    @Autowired    QuestionService questionService;    @RequestMapping("getAllQuestions")    public ResponseEntity<List<Question>> getAllQuestions() {        return questionService.getAllQuestions();    }    @RequestMapping("/category/{category}")    public ResponseEntity<List<Question>> getQuestionsByDifficultyLevel(@PathVariable String category) {        return questionService.getQuestionsByCategory(category);    }    @PostMapping("addQuestion")    public ResponseEntity<String> addQuestion(@RequestBody Question question) {        return questionService.addQuestion(question);    }    @DeleteMapping("deleteQuestion")    public ResponseEntity<String> deleteQuestion(@RequestBody Question question) {        return questionService.deleteQuestion(question);    }    @PutMapping("updateQuestion")    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {        return questionService.updateQuestion(question);    }}