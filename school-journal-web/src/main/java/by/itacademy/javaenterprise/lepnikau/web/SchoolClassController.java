package by.itacademy.javaenterprise.lepnikau.web;

import by.itacademy.javaenterprise.lepnikau.service.SchoolClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SchoolClassController {

    @Autowired
    private SchoolClassesService schoolClassesService;

    @GetMapping("/school_class")
    @ResponseBody
    public String getSchoolClass(@RequestParam(name = "school_class_id") String id) {
        return schoolClassesService.findSchoolClass(id).toString();
    }
}
