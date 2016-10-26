package com.github.yongzhizhan.draftbox.model;

import javax.validation.constraints.Size;

/**
 * 带验证的对象
 * @author zhanyongzhi
 */
public class Foo {
    private String validString;

    @Size(min = 1, max = 5)
    public String getValidString() {
        return validString;
    }

    public void setValidString(final String vValidString) {
        validString = vValidString;
    }
}
