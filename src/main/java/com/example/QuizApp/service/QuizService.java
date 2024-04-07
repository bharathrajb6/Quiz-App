package com.example.QuizApp.service;import com.example.QuizApp.dao.QuestionDAO;import com.example.QuizApp.dao.QuizDAO;import com.example.QuizApp.model.Question;import com.example.QuizApp.model.Quiz;import com.example.QuizApp.model.Response;import com.example.QuizApp.wrapper.QuestionWrapper;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Service;import static com.example.QuizApp.utils.CommonUtil.generateRandomNumber;import java.util.ArrayList;import java.util.List;import java.util.Optional;@Servicepublic class QuizService {    @Autowired    QuizDAO quizDAO;    @Autowired    QuestionDAO questionDAO;    public ResponseEntity<String> createQuiz(String category, int noQun, String title) {        List<Question> questionList = questionDAO.getQuestionByLimitAndCategory(noQun, category);        Quiz quiz = new Quiz();        quiz.setId(generateRandomNumber(4));        quiz.setTitle(title);        quiz.setQuestionList(questionList);        quizDAO.save(quiz);        return new ResponseEntity<>("Successfully created", HttpStatus.CREATED);    }    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {        Optional<Quiz> quiz = quizDAO.findById(id);        List<Question> questionListFromDB = quiz.get().getQuestionList();        List<QuestionWrapper> questionWrapperList = new ArrayList<>();        for (Question question : questionListFromDB) {            QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestn(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());            questionWrapperList.add(questionWrapper);        }        return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);    }    public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {        int score = 0;        Quiz quiz = quizDAO.findById(id).get();        List<Question> questionList = quiz.getQuestionList();        int i = 0;        for (Response response : responses) {            if (response.getAnswer().equals(questionList.get(i).getAnswer())) {                score += 1;            }            i++;        }        return new ResponseEntity<>(score, HttpStatus.OK);    }    public ResponseEntity<String> deleteQuiz(int id) {        Quiz quiz = quizDAO.findById(id).get();        quizDAO.delete(quiz);        return new ResponseEntity<>("Quiz is deleted successfully", HttpStatus.OK);    }    public ResponseEntity<List<Quiz>> getAllQuiz() {        return new ResponseEntity<>(quizDAO.findAll(), HttpStatus.OK);    }}