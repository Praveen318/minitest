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
		return masterService.authenticateAndGetToken(authReq);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('Master')")
	public String add(@RequestBody MasterData masterData) {
		return masterService.add(masterData);
	}

	@PostMapping("/Logout")
	@PreAuthorize("hasAuthority('Master')")
	public String Logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		return masterService.Logout(token);
	}

	// add question
	@PostMapping("/addQuestion")
	@PreAuthorize("hasAuthority('Master')")
	public Questions addQuestion(@RequestBody QuestionsData questionsData) {
		return masterService.addQuestion(questionsData);
	}

	// findAllQuestion
	@GetMapping("/findAllQuestions")
	@PreAuthorize("hasAuthority('Master')")
	public List<Questions> getAllQuestion() {
		return masterService.getAllQuestion();
	}

	// updatequestion
	@PutMapping("/updateQuestion/{id}")
	@PreAuthorize("hasAuthority('Master')")
	public Questions updateQuestion(@PathVariable int id, @RequestBody QuestionsData questionsData) {
		return masterService.updateQuestion(id, questionsData);
	}

	// settest
	@PostMapping("/setTest/{testTime}")
	@PreAuthorize("hasAuthority('Master')")
	public TestRule setTest(@PathVariable int testTime, @RequestBody int numberOfQuestions) {
		return masterService.setTest(testTime, numberOfQuestions);
	}

	// getAllStudent
	@GetMapping("/findAllStudent")
	@PreAuthorize("hasAuthority('Master')")
	public List<StudentProfileData> getAllStudent() {
		return masterService.getAllStudent();
	}

	// grant permission
	@PostMapping("/grantPermission/{id}")
	@PreAuthorize("hasAuthority('Master')")
	public StudentProfileData grantPermission(@PathVariable int id) {

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
