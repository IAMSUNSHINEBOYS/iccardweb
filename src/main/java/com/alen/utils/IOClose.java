package com.alen.utils;

import java.io.Closeable;

/**
 * 文件流关闭
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class IOClose {
	/**
	 * 文件流关闭
	 * 
	 * @param closeable
	 */
	public static void close(Closeable... closeable) {
		if (closeable != null && closeable.length > 0)
			for (Closeable c : closeable)
				if (c != null)
					try {
						c.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	}
}
