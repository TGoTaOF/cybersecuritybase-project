package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Message;
import sec.project.repository.AccountRepository;
import sec.project.repository.MessageRepository;

@Controller
public class AccountController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String main() {
        return "redirect:/user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String loadForm() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user.equals("admin")) {
            return "redirect:/admin";
        }
        Account acc = accountRepository.findByUsername(user);
        return "redirect:/user/" + acc.getUsername();
    }
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String loadForm(Model model, @PathVariable String id) {
        model.addAttribute("currentUser", accountRepository.findByUsername(id));
        model.addAttribute("messages", messageRepository.findAll());
        return "user";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String message) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        messageRepository.save(new Message(message, user));
        return "redirect:/user";
    }

}
