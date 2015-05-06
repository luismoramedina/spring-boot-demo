package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class HelloController {

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!" + testService.doSomething() + new Date();
    }

}
