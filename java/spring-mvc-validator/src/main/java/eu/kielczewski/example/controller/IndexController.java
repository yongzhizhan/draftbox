package eu.kielczewski.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@SuppressWarnings("UnusedDeclaration")
@Validated
public class IndexController {

    @Value("${example.message}")
    private String message;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "validString", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String validString(
            @RequestParam(value = "str", defaultValue = "")
            @Valid
            @Size(min = 1, max = 3)
            String vStr){

        return vStr;
    }
}
