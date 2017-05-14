package cn.zhanyongzhi.draftbox.pratice.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Logger logger = Logger.getLogger(Config.class);

    //filestorage
    public static final String IS_READ_ONLY_KEY = "filestorage.readonly";
    public static final Boolean IS_READ_ONLY_VAL = false;

    private static Properties properties = new Properties();
    private static String configFilePath = "filestorage.properties";
    private static Config instance = new Config();

    {
        try {
            properties.load(new FileInputStream(new File(configFilePath)));
        } catch (IOException e) {
            logger.error("load properties file failed.", e);
        }
    }

    public static void setPropertiesFilePath(String configFilePath) throws IOException {
        properties.load(new FileInputStream(new File(configFilePath)));
    }

    public static Config getInstance(){
        return instance;
    }

    public String getValue(String key, String defaultVal){
        return properties.getProperty(key, defaultVal);
    }

    public Integer getValue(String key, Integer defaultVal){
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultVal)));
    }

    public boolean getValue(String key, Boolean defaultVal){
        return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultVal)));
    }

    public void setValue(String key, String value){
        properties.setProperty(key, value);
    }
}
