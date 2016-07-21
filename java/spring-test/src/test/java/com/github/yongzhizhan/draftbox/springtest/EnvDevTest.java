package com.github.yongzhizhan.draftbox.springtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**web-config.xml")
@ActiveProfiles("dev")
@WebAppConfiguration
public class EnvDevTest {
    private MockMvc mockMvc;

    @Autowired
    ITest test;

    @Autowired
    Environment env;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testDefault(){
        for(String profileName : env.getActiveProfiles())
            System.out.println(profileName);

        //perform current active bean
        test.perform();
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void demo() throws Exception {
        mockMvc.perform(get("/demo/test").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json;charset=UTF-8"))
               .andExpect(content().json("{'foo':'bar'}"));
    }
}