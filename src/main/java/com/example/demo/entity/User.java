package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String email;
	private String password;
	private String roles;
	private String permission;
	private String marks;
	private LocalDateTime startTime;
	private String token;
	private int current_question;
	private int correct;
	private int wrong;
	private int unattempted;
	private List<Character> givenAnswers;
	private List<Integer> testQuestions;
	private List<Character> correctAnswers;

}