package com.alen.jna;


import com.sun.jna.*;

public interface MyCLibrary extends Library {


    MyCLibrary INSTANCE =(MyCLibrary) Native.loadLibrary("D:\\project\\ideaworkspace\\iccardweb\\src\\main\\webapp\\jna\\OMTCanXuDll.dll",  MyCLibrary.class);

    int  fnHongHaiDllCard();

    int OMT_SYSBeep();

    int add(int a,int b);


}