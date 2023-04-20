package com.example.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthReq;
import com.example.demo.dto.StudentData;
import com.example.demo.dto.StudentProfileData;
import com.example.demo.dto.TestQuestions;
import com.example.demo.entity.Questions;
import com.example.demo.entity.TestRule;
import com.example.demo.entity.User;
import com.example.demo.globalExceptionHandler.CustomException;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.TestRuleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class StudentService {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestRuleRepository testRuleRespository;
	@Autowired
	private QuestionsRepository questionsRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		} else
			throw new CustomException("No authorities given to user");
	}

	// add
	public String add(StudentData studentData) {
		User user = Convertor.userdetailstouser(studentData);
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

	// start test
	public TestQuestions start(String useremail) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		TestRule testRule = testRuleRespository.findById(1).orElse(null);
		if (user.getPermission().equals("Granted")) {
			if (user.getStartTime() == null) {
				user.setStartTime(LocalDateTime.now());
				Set<Integer> numbers = new HashSet<Integer>();
				Random random = new Random();
				while (numbers.size() < testRule.getNumberOfQuestions()) {
					int number = random.nextInt((int) questionsRepository.count() - 1) + 1;
					numbers.add(number);
				}
				List<Integer> list = new ArrayList<>(numbers);
				List<Character> list1 = new ArrayList<Character>();
				List<Character> list2 = new ArrayList<Character>();
				for (int i = 0; i < list.size(); i++) {
					Questions questions = questionsRepository.findById(list.get(i)).orElse(null);
					list1.add(questions.getCorrectAnswer());
					list2.add('E');
				}
				user.setGivenAnswers(list2);
				user.setCorrectAnswers(list1);
				user.setTestQuestions(list);
				userRepository.save(user);
				Questions questions = questionsRepository.findById(user.getTestQuestions().get(0)).orElse(null);
				return Convertor.questionstotestquestions(questions);
			} else {
				Questions questions = questionsRepository.findById(user.getTestQuestions().get(0)).orElse(null);
				return Convertor.questionstotestquestions(questions);
			}
		} else
			throw new CustomException("Permission not granted");

	}

	// next
	public TestQuestions next(String useremail) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		TestRule testRule = testRuleRespository.findById(1).orElse(null);
		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
		if (t <= testRule.getTestTime()) {
			int currentIndex = user.getCurrent_question();
			int index;
			index = (currentIndex + 1) % user.getTestQuestions().size();
			user.setCurrent_question(index);
			userRepository.save(user);
			Questions questions = questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
			return Convertor.questionstotestquestions(questions);
		} else {
			List<Character> list1 = user.getGivenAnswers();
			List<Character> list2 = user.getCorrectAnswers();
			int correct = 0, wrong = 0, unattempted = 0;
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).equals(list2.get(i))) {
					correct++;
				} else if (list1.get(i).equals('E')) {
					unattempted++;
				} else
					wrong++;
			}
			int marks = (correct * 4) - wrong;
			user.setUnattempted(unattempted);
			user.setWrong(wrong);
			user.setCorrect(correct);
			user.setMarks(Integer.toString(marks));
			user.setStartTime(null);
			user.setPermission("Revoked");
			userRepository.save(user);
			throw new CustomException("Correct=" + correct + ";" + "Wrong=" + wrong + ";" + "Unattempted=" + unattempted
					+ "\n" + "Marks=" + marks);
		}
	}

	// previous
	public TestQuestions previous(String useremail) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		TestRule testRule = testRuleRespository.findById(1).orElse(null);
		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
		if (t <= testRule.getTestTime()) {
			int currentIndex = user.getCurrent_question();
			int index;
			index = (currentIndex - 1 + user.getTestQuestions().size()) % user.getTestQuestions().size();
			user.setCurrent_question(index);
			userRepository.save(user);
			Questions questions = questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
			return Convertor.questionstotestquestions(questions);
		} else {
			List<Character> list1 = user.getGivenAnswers();
			List<Character> list2 = user.getCorrectAnswers();
			int correct = 0, wrong = 0, unattempted = 0;
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).equals(list2.get(i))) {
					correct++;
				} else if (list1.get(i).equals('E')) {
					unattempted++;
				} else
					wrong++;
			}
			int marks = (correct * 4) - wrong;
			user.setUnattempted(unattempted);
			user.setWrong(wrong);
			user.setCorrect(correct);
			user.setMarks(Integer.toString(marks));
			user.setStartTime(null);
			user.setPermission("Revoked");
			userRepository.save(user);
			throw new CustomException("Correct=" + correct + ";" + "Wrong=" + wrong + ";" + "Unattempted=" + unattempted
					+ "\n" + "Marks=" + marks);
		}
	}

	// save
	public TestQuestions save(String useremail, char ch) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		TestRule testRule = testRuleRespository.findById(1).orElse(null);
		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
		if (t <= testRule.getTestTime()) {
			if (ch == 'A' || ch == 'B' || ch == 'C' || ch == 'D') {
				List<Character> list = user.getGivenAnswers();
				int currentIndex = user.getCurrent_question();
				list.set(currentIndex, ch);
				user.setGivenAnswers(list);
				int index;
				index = (currentIndex + 1) % user.getTestQuestions().size();
				user.setCurrent_question(index);
				userRepository.save(user);
				Questions questions = questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
				return Convertor.questionstotestquestions(questions);
			} else {
				Questions questions = questionsRepository
						.findById(user.getTestQuestions().get(user.getCurrent_question())).orElse(null);
				return Convertor.questionstotestquestions(questions);
			}
		} else {
			List<Character> list1 = user.getGivenAnswers();
			List<Character> list2 = user.getCorrectAnswers();
			int correct = 0, wrong = 0, unattempted = 0;
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).equals(list2.get(i))) {
					correct++;
				} else if (list1.get(i).equals('E')) {
					unattempted++;
				} else
					wrong++;
			}
			int marks = (correct * 4) - wrong;
			user.setUnattempted(unattempted);
			user.setWrong(wrong);
			user.setCorrect(correct);
			user.setMarks(Integer.toString(marks));
			user.setStartTime(null);
			user.setPermission("Revoked");
			userRepository.save(user);
			throw new CustomException("Correct=" + correct + ";" + "Wrong=" + wrong + ";" + "Unattempted=" + unattempted
					+ "\n" + "Marks=" + marks);
		}

	}

	// submit
	public String submit(String useremail) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		List<Character> list1 = user.getGivenAnswers();
		List<Character> list2 = user.getCorrectAnswers();
		int correct = 0, wrong = 0, unattempted = 0;
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).equals(list2.get(i))) {
				correct++;
			} else if (list1.get(i).equals('E')) {
				unattempted++;
			} else
				wrong++;
		}
		int marks = (correct * 4) - wrong;
		user.setUnattempted(unattempted);
		user.setWrong(wrong);
		user.setCorrect(correct);
		user.setMarks(Integer.toString(marks));
		user.setStartTime(null);
		user.setPermission("Revoked");
		userRepository.save(user);
		return ("Correct=" + correct + ";" + "Wrong=" + wrong + ";" + "Unattempted=" + unattempted + "\n" + "Marks="
				+ marks);
	}

	// profile
	public StudentProfileData getProfile(String useremail) {
		User user = userRepository.findByEmail(useremail).orElse(null);
		return Convertor.usertostudentprofiledate(user);
	}
}
