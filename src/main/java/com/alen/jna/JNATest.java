package com.alen.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
 
public class JNATest {
	//继承Library，用于加载库文件  
	public interface Clibrary extends Library{
		Clibrary INSTANCE = (Clibrary) Native.loadLibrary(
                ("D:\\project\\ideaworkspace\\iccardweb\\src\\main\\webapp\\jna\\HongHaiCard.dll"), Clibrary.class);

		/*
		 * 声明一个跟C语言的printf()一样的方法，参数类型要匹配
		 * C语言的printf()方法原型如下：
		 * int __cdecl printf(const char * __restrict__ _Format,...);
		 */
		int OMT_SYSBeep();
	} 
	
	public static void main(String...strings) {
		//调用  
		//System.err.println(Clibrary.INSTANCE.OMT_SYSBeep()+"---"+Cl);
	}
}
