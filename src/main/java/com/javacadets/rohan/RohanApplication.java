package com.javacadets.rohan;

import com.javacadets.rohan.entities.Admin;
import com.javacadets.rohan.entities.User;
import com.javacadets.rohan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RohanApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(RohanApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Admin admin = new Admin("amalonzo@yondu.com", "andee", "malonzo");
			System.out.println(admin.getTemporaryPassword());
			this.userRepository.save(admin);

//			User user = this.userRepository.findByEmail("amalonzo@yondu.com").orElse(null);
//			System.out.println(user);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
