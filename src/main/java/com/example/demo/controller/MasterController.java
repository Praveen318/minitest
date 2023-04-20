package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.AuthReq;
import com.example.demo.dto.MasterData;
import com.example.demo.dto.QuestionsData;
import com.example.demo.dto.StudentProfileData;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.TestRuleRepository;
import com.example.demo.service.MasterService;
import com.example.demo.service.StudentService;
import com.example.demo.entity.Questions;
import com.example.demo.entity.TestRule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/master")
public class MasterController {

	@Autowired
	MasterService masterservice;
	@Autowired
	StudentService studentservice;
	@Autowired
	QuestionsRepository questionsRepository;
	@Autowired
	TestRuleRepository testRuleRepository;
	@Autowired
	MasterService masterService;

	@PostMapping("/Login")
	public String authenticateAndGetToken(@Valid @RequestBody AuthReq authReq) {
//		Authentication authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
//		if (authentication.isAuthenticated()) {
//			User user = userRepository.findByEmail(authReq.getEmail()).orElse(null);
//			String token = jwtService.generateToken(authReq.getEmail());
//			user.setToken(token);
//			userRepository.save(user);
//			return token;
//		}
//		// return jwtService.generateToken(authReq.getEmail());}
//		else
//			throw new CustomException("No authorities given to user");
		return masterService.authenticateAndGetToken(authReq);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('Master')")
	public String add(@RequestBody MasterData masterData) {
//		User user = Convertor.userdetailstouser(masterData);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		try {
//		userRepository.save(user);
//		return "added";
//		}catch (DataIntegrityViolationException ex) {
//			return "email already exist";
//		}
		return masterService.add(masterData);
	}

	@PostMapping("/Logout")
	@PreAuthorize("hasAuthority('Master')")
	public String Logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
//		if (userRepository.findByToken(token) != null) {
//			User user = userRepository.findByToken(token);
//			user.setToken(null);
//			userRepository.save(user);
//			return "Logged_out";
//		} else
//			throw new CustomException("User Logged out");
		return masterService.Logout(token);
	}

	// add question
	@PostMapping("/addQuestion")
	@PreAuthorize("hasAuthority('Master')")
	public Questions addQuestion(@RequestBody QuestionsData questionsData) {
//		return questionsRepository.save(question);
		return masterService.addQuestion(questionsData);
	}

	// findAllQuestion
	@GetMapping("/findAllQuestions")
	@PreAuthorize("hasAuthority('Master')")
	public List<Questions> getAllQuestion() {
//		return questionsRepository.findAll();
		return masterService.getAllQuestion();
	}

	// updatequestion
	@PutMapping("/updateQuestion/{id}")
	@PreAuthorize("hasAuthority('Master')")
	public Questions updateQuestion(@PathVariable int id, @RequestBody QuestionsData questionsData) {
//		Questions existingquestion=questionsRepository.findById(id).orElse(null);
//		if(existingquestion!=null) {
//			existingquestion.setCorrectAnswer(question.getCorrectAnswer());
//			existingquestion.setOptionD(question.getOptionD());
//			existingquestion.setOptionC(question.getOptionC());
//			existingquestion.setOptionB(question.getOptionB());
//			existingquestion.setOptionA(question.getOptionA());
//			existingquestion.setQuestions(question.getQuestions());
//			return questionsRepository.save(existingquestion);			
//		}
//		else
//			throw new CustomException("NoQuestionfoundtobeReplaced");
		return masterService.updateQuestion(id, questionsData);
	}

	// settest
	@PostMapping("/setTest/{testTime}")
	@PreAuthorize("hasAuthority('Master')")
	public TestRule setTest(@PathVariable int testTime, @RequestBody int numberOfQuestions) {
//		if(numberOfQuestions<=questionsRepository.count()) {
//			TestRule testRule=testRuleRepository.findById(1).orElseGet(null);
//			testRule.setTestTime(testTime);
//			testRule.setNumberOfQuestions(numberOfQuestions);
//			testRuleRepository.save(testRule);
//			return testRule;			
//		}
//		else
//			throw new CustomException("question limit exceed");
		return masterService.setTest(testTime, numberOfQuestions);
	}

	// getAllStudent
	@GetMapping("/findAllStudent")
	@PreAuthorize("hasAuthority('Master')")
	public List<StudentProfileData> getAllStudent() {
//		return userRepository.findByRoles("Student");
		return masterService.getAllStudent();
	}

	// grant permission
	@PostMapping("/grantPermission/{id}")
	@PreAuthorize("hasAuthority('Master')")
	public StudentProfileData grantPermission(@PathVariable int id) {
//		User user=userRepository.findById(id).orElse(null);
//		if(user!=null) {
//			if(user.getPermission().equals("Revoked")) {
//				user.setPermission("Granted");
//				userRepository.save(user);
//				return user; 
//			}
//			else
//				throw new CustomException("user already granted permission");			
//		}
//		else
//			throw new CustomException("no user found");

		return masterService.grantPermission(id);
	}
}
// resetstudenttimer
//	@PostMapping("/resetstudenttimer/{id}")
//	@PreAuthorize("hasAuthority('Master')")
//	public User resetstudenttimer(@PathVariable int id)
//	{
//		User user=userRepository.findById(id).orElse(null);
//		if(user!=null) {
//			user.setStartTime(null);
//			userRepository.save(user);
//			return user;
//		}
//		else
//			throw new CustomException("no user found");
//
//	}
