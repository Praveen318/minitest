package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfileData {
	private int id;
	private String email;
	private String roles;
	private String permission;
	private String marks;
	private LocalDateTime startTime;
	private int correct;
	private int wrong;
	private int unattempted;
}
