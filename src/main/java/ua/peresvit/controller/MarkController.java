package ua.peresvit.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.entity.Mark;
import ua.peresvit.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.peresvit.service.MessageService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/mark")
public class MarkController {
    @Autowired
    private MarkService markService;
    @Autowired
    private MessageService messageService;

    //go to manage page
    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String goToManagement(Model model) {
        return "admin/mark/markManagement";
    }

    //go to addForm
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String goToAddForm(Model model) {
        Mark mark = new Mark();

        model.addAttribute(mark);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());

        return "admin/mark/addMark";
    }

    // create mark
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createMark(Mark mark, RedirectAttributes model, @RequestParam(name = "file") MultipartFile file) {

        mark.setImageURL(markService.saveFile(mark, file));
        markService.save(mark);

        return "redirect:/admin/mark/";
    }

    // show Mark by id
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public String geMark(@PathVariable("markId") long markId, Model model) {
        if (!model.containsAttribute("markId"))
            model.addAttribute("markId", markService.findOne(markId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/mark/markProfile";
    }

    // show all marks
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllUsers(Model model) {
        List<Mark> marks = markService.findAll();
        model.addAttribute("markList", marks);
        model.addAttribute("mark", new Mark());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/mark/allMarks";
    }

    // edit mark
    @RequestMapping(value = "/edit/{markId}", method = RequestMethod.GET)
    public String editMark(@PathVariable("markId")  long markId, Model model) {

        model.addAttribute("mark", markService.findOne(markId));
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "admin/mark/addMark";
    }

}

