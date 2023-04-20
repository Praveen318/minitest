package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsData {

	@NotBlank
	private String questions;
	@NotBlank
	private String optionA;
	@NotBlank
	private String optionB;
	@NotBlank
	private String optionC;
	@NotBlank
	private String optionD;
	@NotBlank
	private char correctAnswer;

}
