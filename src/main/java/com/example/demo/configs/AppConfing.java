package com.example.demo.configs;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.AccountRole;
import com.example.demo.accounts.AccountService;
import com.example.demo.common.AppProperties;
import com.example.demo.events.Event;
import com.example.demo.events.EventRepository;

@Configuration
public class AppConfing {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Autowired
			AccountService accountService;
			
			@Autowired
			EventRepository eventRepositry;
			
			@Autowired
			AppProperties appProperties;
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				IntStream.range(1, 30).forEach(i -> generateEvent(i));
//				generateAccount("admin@email.com", "admin", Set.of(AccountRole.ADMIN, AccountRole.USER));
//				generateAccount("user@email.com", "user", Set.of(AccountRole.USER));
				
				generateAccount(appProperties.getAdminUsername(), 
						appProperties.getAdminPassword(), 
						Set.of(AccountRole.ADMIN, AccountRole.USER));
				generateAccount(appProperties.getUserUsername(), 
						appProperties.getUserPassword(), 
						Set.of(AccountRole.USER));
				
			}
			
			private Account generateAccount(String email, String password, Set<AccountRole> roles) {
				Account account = Account.builder()
						.email(email)
						.password(password)
						.roles(roles)
						.build();
				return accountService.saveAccount(account);
			}
			
			private Event generateEvent(int index) {
				Event event = Event.builder()
						.name("event" + index)
						.description("test event")
						.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
						.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
						.beginEventDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
						.endEventDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
						.basePrice(100)
						.maxPrice(200)
						.limitOfEnrollment(100)
						.location("강남역 D2 스타텁 팩토리")
						.build();
				
				return this.eventRepositry.save(event);
			}
		};
	}

}
