package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Message;
import sec.project.repository.AccountRepository;
import sec.project.repository.MessageRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Account account = new Account();
        account.setUsername("User1");
        account.setPassword(passwordEncoder.encode("letmein"));
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("User2");
        account.setPassword(passwordEncoder.encode("qwerty"));
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("User3");
        account.setPassword(passwordEncoder.encode("123321"));
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("User4");
        account.setPassword(passwordEncoder.encode("12341234"));
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("admin");
        account.setPassword(passwordEncoder.encode("admin"));
        accountRepository.save(account);
        
        Message message = new Message();
        message.setUser("User1");
        message.setMessage("Hello!");
        messageRepository.save(message);
        
        message = new Message();
        message.setUser("User2");
        message.setMessage("Bon jour!");
        messageRepository.save(message);
        
        message = new Message();
        message.setUser("admin");
        message.setMessage("Gutten dag!");
        messageRepository.save(message);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
