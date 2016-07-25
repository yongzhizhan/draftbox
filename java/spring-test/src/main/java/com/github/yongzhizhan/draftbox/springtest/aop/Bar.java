    package com.github.yongzhizhan.draftbox.springtest.aop;

    import org.springframework.beans.factory.annotation.Autowired;

    public class Bar implements IBar {
        @Autowired
        Dep dep;

        @Override
        public String perform(final String message) {
            System.out.println("run bar " + message);
            return dep.perform("aspect");
        }
    }
