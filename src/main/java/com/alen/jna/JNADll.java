package com.alen.jna;


import com.sun.jna.Library;

/**
 * @author LK
 * @version 1.0
 * @date 2020/6/26 17:51
 */
public interface JNADll extends Library {

    /**
     *  蜂鸣器
     */
    void OMT_SYSBeep();

}
