package com.example.demo.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.AccountRole;
import com.example.demo.accounts.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private AccountService accountService;
	
//	@Autowired
//	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void findByUsername() {
		// Given
		String password = "dong";
		String username = "dong@email.com";
		Account account = Account.builder()
				.email("dong@email.com")
				.password("dong")
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
//		this.accountRepository.save(account);
		this.accountService.saveAccount(account);
		
		// When
		UserDetailsService userDetailsService = accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		// Then
		assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
	}
	
//	@Test(expected = UsernameNotFoundException.class)
	@Test
	public void findByUsernameFail() {
		String username = "random@email.com";
		
//		accountService.loadUserByUsername(username);
//		
//		try {
//			accountService.loadUserByUsername(username);
//			fail("supposed to be failed");
//		} catch (UsernameNotFoundException e) {
//			assertThat(e.getMessage()).containsSequence(username);
//		}
		
		// Expected
		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(username));
		
		// When
		accountService.loadUserByUsername(username);
		
	}
	
}
