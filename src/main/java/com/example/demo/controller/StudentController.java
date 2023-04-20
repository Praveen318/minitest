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
//		Authentication authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
//		if (authentication.isAuthenticated()) {
//			User user = userRepository.findByEmail(authReq.getEmail()).orElse(null);
//			String token = jwtService.generateToken(authReq.getEmail());
//			user.setToken(token);
//			userRepository.save(user);
//			return token;
		return studentService.authenticateAndGetToken(authReq);
	}
//		// return jwtService.generateToken(authReq.getEmail());}
//		else
//			throw new CustomException("No authorities given to user");
//	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('Master')")
	public String add(@RequestBody StudentData studentData) {
//		User user = Convertor.userdetailstouser(studentData);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		try {
//		userRepository.save(user);
//		return "added";
//		}catch (DataIntegrityViolationException ex) {
//			return "email already exist";
//		}
		return studentService.add(studentData);
	}

	@PostMapping("/Logout")
	@PreAuthorize("hasAuthority('Student')")
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
		return studentService.Logout(token);
	}

	// start test
	@PutMapping("/start")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions start(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		TestRule testRule = testRuleRespository.findById(1).orElse(null);
//		if(user.getPermission().equals("Granted")) {
//			if(user.getStartTime()==null) {
//				user.setStartTime(LocalDateTime.now());
//				Set<Integer> numbers = new HashSet<Integer>();
//		        Random random = new Random();
//		        while (numbers.size() < testRule.getNumberOfQuestions()) {
//		        	int number = random.nextInt((int)questionsRepository.count()-1)+1 ;
//		            numbers.add(number);
//		        }
//		        List<Integer> list = new ArrayList<>(numbers);
//		        List<Character> list1=new ArrayList<Character>();
//		        List<Character> list2=new ArrayList<Character>();
//		        for(int i=0;i<list.size();i++)
//		        {	
//		        	Questions questions=questionsRepository.findById(list.get(i)).orElse(null);
//		        	list1.add(questions.getCorrectAnswer());
//		        	list2.add('E');
//		        }
//		        user.setGivenAnswers(list2);
//		        user.setCorrectAnswers(list1);
//		        user.setTestQuestions(list);
//		        userRepository.save(user);
//		        return questionsRepository.findById(user.getTestQuestions().get(0)).orElse(null);
//			}
//			else
//				return questionsRepository.findById(user.getTestQuestions().get(0)).orElse(null);
//		}
//		else
//			throw new CustomException("Permission not granted");
		return studentService.start(useremail);
	}

	// next
	@GetMapping("/next")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions next(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		TestRule testRule= testRuleRespository.findById(1).orElse(null);
//		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
//		if(t<=testRule.getTestTime()) {
//			int currentIndex=user.getCurrent_question();
//			int index;	
//			index = (currentIndex + 1) % user.getTestQuestions().size();
//			user.setCurrent_question(index);
//			userRepository.save(user);
//			return questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
//		}
//		else {
//			List<Character> list1=user.getGivenAnswers();
//			List<Character> list2=user.getCorrectAnswers();
//			int correct=0,wrong=0,unattempted=0;
//			for (int i = 0; i < list1.size(); i++) {
//		    if (list1.get(i).equals(list2.get(i))) {
//		        correct++;
//		    } else if(list1.get(i).equals('E')){
//		    	unattempted++;
//		    } else
//		        wrong++;
//		    }
//			int marks=(correct*4)-wrong;
//			user.setUnattempted(unattempted);
//			user.setWrong(wrong);
//			user.setCorrect(correct);
//			user.setMarks(Integer.toString(marks));
//			user.setStartTime(null);
//			user.setPermission("Revoked");
//			userRepository.save(user);
//			throw new CustomException("Correct="+correct+";"+"Wrong="+wrong+";"+"Unattempted="+unattempted+"\n"+"Marks="+marks);
//		}
		return studentService.next(useremail);
	}

	// index = (currentIndex - 1 + list.size()) % list.size();
	// previous
	@GetMapping("/previous")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions previous(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		TestRule testRule= testRuleRespository.findById(1).orElse(null);
//		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
//		if(t<=testRule.getTestTime()) {
//			int currentIndex=user.getCurrent_question();
//			int index;	
//			index = (currentIndex - 1 + user.getTestQuestions().size()) % user.getTestQuestions().size();
//			user.setCurrent_question(index);
//			userRepository.save(user);
//			return questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
//		}	
//		else {
//			List<Character> list1=user.getGivenAnswers();
//			List<Character> list2=user.getCorrectAnswers();
//			int correct=0,wrong=0,unattempted=0;
//			for (int i = 0; i < list1.size(); i++) {
//		    if (list1.get(i).equals(list2.get(i))) {
//		        correct++;
//		    } else if(list1.get(i).equals('E')){
//		    	unattempted++;
//		    } else
//		        wrong++;
//		    }
//			int marks=(correct*4)-wrong;
//			user.setUnattempted(unattempted);
//			user.setWrong(wrong);
//			user.setCorrect(correct);
//			user.setMarks(Integer.toString(marks));
//			user.setStartTime(null);
//			user.setPermission("Revoked");
//			userRepository.save(user);
//			throw new CustomException("Correct="+correct+";"+"Wrong="+wrong+";"+"Unattempted="+unattempted+"\n"+"Marks="+marks);
//		}
		return studentService.previous(useremail);
	}

	// save
	@PostMapping("/save/{ch}")
	@PreAuthorize("hasAuthority('Student')")
	public TestQuestions save(HttpServletRequest request, @PathVariable char ch) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		TestRule testRule= testRuleRespository.findById(1).orElse(null);
//		long t = Duration.between(user.getStartTime(), LocalDateTime.now()).toMinutes();
//		if(t<=testRule.getTestTime()) {
//			if(ch=='A'||ch=='B'||ch=='C'||ch=='D') {
//				List <Character> list= user.getGivenAnswers();
//				int currentIndex=user.getCurrent_question();
//				list.set(currentIndex,ch);
//				user.setGivenAnswers(list);
//				int index;	
//				index = (currentIndex + 1) % user.getTestQuestions().size();
//				user.setCurrent_question(index);
//				userRepository.save(user);
//				return questionsRepository.findById(user.getTestQuestions().get(index)).orElse(null);
//			}
//			else
//				return questionsRepository.findById(user.getTestQuestions().
//						get(user.getCurrent_question())).orElse(null);	
//			}
//		else {
//			List<Character> list1=user.getGivenAnswers();
//			List<Character> list2=user.getCorrectAnswers();
//			int correct=0,wrong=0,unattempted=0;
//			for (int i = 0; i < list1.size(); i++) {
//		    if (list1.get(i).equals(list2.get(i))) {
//		        correct++;
//		    } else if(list1.get(i).equals('E')){
//		    	unattempted++;
//		    } else
//		        wrong++;
//		    }
//			int marks=(correct*4)-wrong;
//			user.setUnattempted(unattempted);
//			user.setWrong(wrong);
//			user.setCorrect(correct);
//			user.setMarks(Integer.toString(marks));
//			user.setStartTime(null);
//			user.setPermission("Revoked");
//			userRepository.save(user);
//			throw new CustomException("Correct="+correct+";"+"Wrong="+wrong+";"+"Unattempted="+unattempted+"\n"+"Marks="+marks);
//		}
		return studentService.save(useremail, ch);
	}

	// submit
	@PostMapping("/submit")
	@PreAuthorize("hasAuthority('Student')")
	public String submit(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		List<Character> list1=user.getGivenAnswers();
//		List<Character> list2=user.getCorrectAnswers();
//		int correct=0,wrong=0,unattempted=0;
//		for (int i = 0; i < list1.size(); i++) {
//	    if (list1.get(i).equals(list2.get(i))) {
//	        correct++;
//	    } else if(list1.get(i).equals('E')){
//	    	unattempted++;
//	    } else
//	        wrong++;
//	    }
//		int marks=(correct*4)-wrong;
//		user.setUnattempted(unattempted);
//		user.setWrong(wrong);
//		user.setCorrect(correct);
//		user.setMarks(Integer.toString(marks));
//		user.setStartTime(null);
//		user.setPermission("Revoked");
//		userRepository.save(user);
//		return ("Correct="+correct+";"+"Wrong="+wrong+";"+"Unattempted="+unattempted+"\n"+"Marks="+marks);
		return studentService.submit(useremail);
	}

	// profile
	@GetMapping("/getProfile")
	@PreAuthorize("hasAuthority('Student')")
	public StudentProfileData getProfile(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String token = authHeader.substring(7);
		String useremail = jwtService.extractUseremail(token);
//		User user = userRepository.findByEmail(useremail).orElse(null);
//		return user;
		return studentService.getProfile(useremail);
	}
}