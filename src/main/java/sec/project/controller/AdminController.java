package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AdminController {
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String adminMapping(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        return "admin";
    }
    
    @RequestMapping(value = "/admin/{id}/delete", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id) {
        Account acc = accountRepository.findByUsername(id);
        accountRepository.delete(acc);
        return "redirect:/admin";
    }

}