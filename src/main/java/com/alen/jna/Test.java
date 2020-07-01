package com.alen.jna;

import java.util.Properties;

/**
 * @author LK
 * @version 1.0
 * @date 2020/6/26 18:32
 */
public class Test {

    public static void main(String[] arc){
     /*   Properties props = System.getProperties();
        String bits=String.valueOf(props.get("sun.arch.data.model"));
        System.err.println(bits);*/
        //int aa = MyCLibrary.INSTANCE.OMT_SYSBeep();
        //int jna = MyCLibrary.INSTANCE.fnHongHaiDllCard();

        //System.err.println(aa);

        //int a = MyCLibrary.INSTANCE.add(5,5);

        int omt = MyCLibrary.INSTANCE.OMT_SYSBeep();
        System.err.println("---"+omt);
    }
}
