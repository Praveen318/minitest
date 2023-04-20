package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.AuthReq;
import com.example.demo.dto.MasterData;
import com.example.demo.dto.QuestionsData;
import com.example.demo.dto.StudentProfileData;
import com.example.demo.entity.Questions;
import com.example.demo.entity.TestRule;
import com.example.demo.entity.User;
import com.example.demo.globalExceptionHandler.CustomException;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.TestRuleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MasterService {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	StudentService studentservice;
	@Autowired
	QuestionsRepository questionsRepository;
	@Autowired
	TestRuleRepository testRuleRepository;

	// Login
	public String authenticateAndGetToken(AuthReq authReq) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
		if (authentication.isAuthenticated()) {
			User user = userRepository.findByEmail(authReq.getEmail()).orElse(null);
			String token = jwtService.generateToken(authReq.getEmail());
			user.setToken(token);
			userRepository.save(user);
			return token;
		}
		// return jwtService.generateToken(authReq.getEmail());}
		else
			throw new CustomException("No authorities given to user");
	}

	// Add
	public String add(MasterData masterData) {
		User user = Convertor.userdetailstouser(masterData);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			userRepository.save(user);
			return "added";
		} catch (DataIntegrityViolationException ex) {
			return "email already exist";
		}
	}

	// Logout
	public String Logout(String token) {
		if (userRepository.findByToken(token) != null) {
			User user = userRepository.findByToken(token);
			user.setToken(null);
			userRepository.save(user);
			return "Logged_out";
		} else
			throw new CustomException("User Logged out");
	}

	// add question
	public Questions addQuestion(QuestionsData questionsData) {
		Questions question = Convertor.questionDatatoquestions(questionsData);
		return questionsRepository.save(question);
	}

	// findAllQuestion
	public List<Questions> getAllQuestion() {
		return questionsRepository.findAll();
	}

	// updatequestion
	public Questions updateQuestion(int id, QuestionsData questionsData) {
		Questions question = Convertor.questionDatatoquestions(questionsData);
		Questions existingquestion = questionsRepository.findById(id).orElse(null);
		if (existingquestion != null) {
			existingquestion.setCorrectAnswer(question.getCorrectAnswer());
			existingquestion.setOptionD(question.getOptionD());
			existingquestion.setOptionC(question.getOptionC());
			existingquestion.setOptionB(question.getOptionB());
			existingquestion.setOptionA(question.getOptionA());
			existingquestion.setQuestions(question.getQuestions());
			return questionsRepository.save(existingquestion);
		} else
			throw new CustomException("NoQuestionfoundtobeReplaced");
	}

	// settest
	public TestRule setTest(int testTime, int numberOfQuestions) {
		if (numberOfQuestions <= questionsRepository.count()) {
			TestRule testRule = testRuleRepository.findById(1).orElseGet(null);
			testRule.setTestTime(testTime);
			testRule.setNumberOfQuestions(numberOfQuestions);
			testRuleRepository.save(testRule);
			return testRule;
		} else
			throw new CustomException("question limit exceed");
	}

	// getAllStudent
	public List<StudentProfileData> getAllStudent() {
		// List<User> user= ;
		return Convertor.usertostudentprofiledate(userRepository.findByRoles("Student"));
	}

	// grant permission
	public StudentProfileData grantPermission(int id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			if (user.getPermission().equals("Revoked")) {
				user.setPermission("Granted");
				user.setMarks("0");
				user.setStartTime(null);
				user.setCorrect(0);
				user.setWrong(0);
				user.setUnattempted(0);
				return Convertor.usertostudentprofiledate(userRepository.save(user));
			} else
				throw new CustomException("user already granted permission");
		} else
			throw new CustomException("no user found");
	}

}
