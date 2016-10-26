package com.github.yongzhizhan.draftbox.springtest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Demo
 *
 * @author zhanyognzhi
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String demo(@RequestParam(value = "postData", required = false) final String postData) throws Exception {
        return "{'foo':'bar'}";
    }
}
