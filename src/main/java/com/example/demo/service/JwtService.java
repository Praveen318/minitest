package com.example.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.globalExceptionHandler.CustomException;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	@Autowired
	private UserRepository userRepository;

	// method to extract username
	public String extractUseremail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// method to extract session expiration time
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// method to extract all claims in a token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	// method to validate session
	private Boolean isTokenExpired(String token) {
		if (userRepository.findByToken(token) != null)
			return false;
		else
			return true;
		// return extractExpiration(token).before(new Date());
	}

	// method to validate token(username and session) and return boolean value
	public Boolean validateToken(String token, UserDetails userDetails) throws CustomException {
		final String usermobile = extractUseremail(token);
		return (usermobile.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// method to map and call create token and return token
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	// method to create token
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// hexadecimal string value
	public static final String SECRET = "34743777217A25432A462D4A614E645266556A586E3272357538782F413F4428";

	// declaring Key for generating JWT tokens
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
