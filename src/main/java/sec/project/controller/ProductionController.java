package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;
import sec.project.repository.MessageRepository;

@Controller
public class ProductionController {
    
    @RequestMapping(value="/production", method = RequestMethod.GET)
    public String adminMapping() {
        return "production";
    }
}