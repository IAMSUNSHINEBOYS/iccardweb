package com.alen.utils;

import org.apache.log4j.Logger;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class LogUtils {

   /* static {
        //String connectdir = System.getProperty("user.dir") + File.separator + "properties" + File.separator + "log4j.properties";
        String connectdir = "D:\\project\\ideaworkspace\\iccard\\iccard-manager\\iccard-manager-web\\src\\main\\resources\\properties\\log4j.properties";
        System.err.println("connectdir:"+connectdir);
        PropertyConfigurator.configure(connectdir);
    }

    public static void main(String[] args) {
        INFO.info("info测试");
        TEST.info("test测试");
    }*/

    /**
     * INFO输出
     */
    public static Logger INFO = Logger.getLogger("info");
    /**
     * ERROR输出
     */
    public static Logger ERROR = Logger.getRootLogger();
    /**
     * 控制台输出
     */
    public static Logger CONSOLE = Logger.getLogger("stdout");


    /**
     * 自定义位置日志测试
     */
    public static Logger TEST = Logger.getLogger("test");

}
