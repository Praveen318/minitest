package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestions {

	private String questions;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
}
