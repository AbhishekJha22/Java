package com.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class Users implements Serializable {
	
	@Id
	private String username;
	private String password;
	private boolean enabled;
	
	public Users() {
		
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.password = passwordEncoder.encode(password);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	/*
	 * public Users(String username, String password, boolean enabled) {
	 * this.username = username;
	 * 
	 * PasswordEncoder passwordEncoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); this.password =
	 * passwordEncoder.encode("{bcrypt}" + password); this.enabled = enabled; }
	 */
	
	
}
