package com.example.demo.controller;

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
import com.example.demo.dto.StudentData;
import com.example.demo.dto.StudentProfileData;
import com.example.demo.dto.TestQuestions;
import com.example.demo.service.JwtService;
import com.example.demo.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/Login")
	public String authenticateAndGetToken(@Valid @RequestBody AuthReq authReq) {
		return studentService.authenticateAndGetToken(authReq);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('Master')")
	public String add(@RequestBody StudentData studentData) {
		return studentService.add(studentData);
	}

	@PostMapping("/Logout")
	@PreAuthorize("hasAuthority('Student')")
	public String Logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		return studentService.Logout(token);
	}

	// start test
	@PutMapping("/start")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions start(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.start(useremail);
	}

	// next
	@GetMapping("/next")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions next(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.next(useremail);
	}

	// previous
	@GetMapping("/previous")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions previous(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.previous(useremail);
	}

	// save
	@PostMapping("/save/{ch}")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions save(HttpServletRequest request, @PathVariable char ch) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.save(useremail, ch);
	}

	// submit
	@PostMapping("/submit")
	@PreAuthorize("hasAuthority('Student')")
	public String submit(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.submit(useremail);
	}

	// profile
	@GetMapping("/getProfile")
	@PreAuthorize("hasAuthority('Student')")
	public StudentProfileData getProfile(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
		return studentService.getProfile(useremail);
	}
}