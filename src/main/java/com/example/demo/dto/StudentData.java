package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentData {
	@Email(message = "Enter vaild email id")
	private String email;
	@Pattern(message = "Password must atleast contain a capital letter[A-Z],"
			+ " a small letter(a-z), a special character[!@#&()–[{}]:;',?/*~$^+=<>]"
			+ " and a number[0-9] and must be 6 character long", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
					+ "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,}$")
	private String password;
	private String roles = "Student";
	private String permission = "Revoked";
	private String marks;
	private LocalDateTime startTime = null;
	private String token;
}
