package cn.yongzhi.java.spring.springdubbo;

import cn.yongzhi.java.spring.springdubbo.api.Search;
import cn.yongzhi.java.spring.springdubbo.model.Foo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/demo")
public class SearchDefault implements Search {
    @Override
    @RequestMapping(value = "search", method = RequestMethod.GET, produces = "application/json")
    public Foo search(String keyword) {
        Foo foo = new Foo(1, "http://zhanyongzhi.cn");
        return foo;
    }
}
