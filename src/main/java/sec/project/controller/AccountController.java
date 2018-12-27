package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Message;
import sec.project.repository.MesageRepository;

@Controller
public class AccountController {

    @Autowired
    private MesageRepository messageRepository;

    @RequestMapping("*")
    public String main() {
        return "redirect:/user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String loadForm(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "user";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String message) {
        messageRepository.save(new Message(message));
        return "redirect:/user";
    }

}
