package com.alen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件名处理
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class FileNameUtils {
	public static boolean checkIsImage(String imgStr) {
		boolean flag = false;
		if (imgStr != null) {
			if (imgStr.equalsIgnoreCase(".gif")
					|| imgStr.equalsIgnoreCase(".jpg")
					|| imgStr.equalsIgnoreCase(".jpeg")
					|| imgStr.equalsIgnoreCase(".png")
					|| imgStr.equalsIgnoreCase(".bmp")) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getNewFileName(String extName) {
		Random r = new Random();
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTimeStr = sDateFormat.format(new Date());
		String newFileName = nowTimeStr + rannum + extName;
		return newFileName;
	}
}
