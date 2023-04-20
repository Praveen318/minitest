package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.example.demo.dto.MasterData;
import com.example.demo.dto.QuestionsData;
import com.example.demo.dto.StudentData;
import com.example.demo.dto.StudentProfileData;
import com.example.demo.dto.TestQuestions;
import com.example.demo.entity.Questions;
import com.example.demo.entity.User;

public class Convertor {
	private static ModelMapper modelMapper = new ModelMapper();

	public static User userdetailstouser(StudentData userData) {
		User user = modelMapper.map(userData, User.class);
		return user;
	}

	public static User userdetailstouser(MasterData userData) {
		User user = modelMapper.map(userData, User.class);
		return user;
	}

	public static Questions questionDatatoquestions(QuestionsData questionsData) {
		Questions questions = modelMapper.map(questionsData, Questions.class);
		return questions;
	}

	public static TestQuestions questionstotestquestions(Questions questions) {
		TestQuestions testquestions = modelMapper.map(questions, TestQuestions.class);
		return testquestions;
	}

	public static List<StudentProfileData> usertostudentprofiledate(List<User> user) {
		List<StudentProfileData> map = user.stream().map(User -> modelMapper.map(User, StudentProfileData.class))
				.collect(Collectors.toList());
		return map;
	}

	public static StudentProfileData usertostudentprofiledate(User user) {
		StudentProfileData studentProfileData = modelMapper.map(user, StudentProfileData.class);
		return studentProfileData;
	}
}
