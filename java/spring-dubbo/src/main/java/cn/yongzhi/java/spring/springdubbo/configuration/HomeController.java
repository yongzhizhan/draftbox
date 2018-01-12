package cn.yongzhi.java.spring.springdubbo.configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(value = "home", description = "swagger home")
public class HomeController {
    @ApiOperation(value = "swagger页面", response = String.class, tags={ "home", })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
