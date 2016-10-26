package com.github.yongzhizhan.draftbox.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;

@Component
@Validated
public class Bar {
    public String validString(
            @Size(min = 1, max = 3)
                    String vStr){

        return vStr;
    }
}
