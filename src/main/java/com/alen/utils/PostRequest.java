package com.alen.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * HTTP请求
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class PostRequest {
	/**
	 * 字符编码
	 */
	private static final String CHARSET = "UTF-8";

	public static KeyStore getKeyStore(String password, String keyStorePath)
			throws Exception {
		FileInputStream is = null;
		// 实例化密钥库
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			// 获得密钥库文件流
			is = new FileInputStream(keyStorePath);
			// 加载密钥库
			ks.load(is, password.toCharArray());
			// 关闭密钥库文件流
			return ks;
		} finally {
			IOClose.close(is);
		}
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password, String keyStorePath)
			throws Exception {
		// 实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLSv1");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		// 获得SSLSocketFactory
		return ctx;
	}

	/**
	 * 获得SSLSocketFactory.
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory
	 * @throws Exception
	 */
	public static SSLContext getSSLContext(String password,
			String keyStorePath, String trustStorePath) throws Exception {
		// 实例化密钥库
		KeyManagerFactory keyManagerFactory = KeyManagerFactory
				.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		// 获得密钥库
		KeyStore keyStore = getKeyStore(password, keyStorePath);
		// 初始化密钥工厂
		keyManagerFactory.init(keyStore, password.toCharArray());

		// 实例化信任库
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		// 获得信任库
		KeyStore trustStore = getKeyStore(password, trustStorePath);
		// 初始化信任库
		trustManagerFactory.init(trustStore);
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLSv1");
		// 初始化SSL上下文
		ctx.init(keyManagerFactory.getKeyManagers(),
				trustManagerFactory.getTrustManagers(), new SecureRandom());
		// 获得SSLSocketFactory
		return ctx;
	}

	public static String httpsPost(String httpUrl, String param)
			throws Exception {
		return httpsPost(httpUrl, param, CHARSET);
	}

	public static String httpsPost(String httpUrl, String param, String charset)
			throws Exception {
		HttpsURLConnection urlCon = null;
		OutputStream os = null;
		BufferedReader in = null;
		try {
			urlCon = (HttpsURLConnection) (new URL(httpUrl)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",
					String.valueOf(param.getBytes(CHARSET).length));
			urlCon.setUseCaches(false);
			os = urlCon.getOutputStream();
			os.write(param.getBytes(charset));
			os.flush();
			in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream(), charset));
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} finally {
			IOClose.close(os, in);
			if (urlCon != null)
				urlCon.disconnect();
		}
	}

	public static String httpPost(String httpsUrl, String param)
			throws Exception {
		return httpPost(httpsUrl, param, CHARSET);
	}

	public static String httpPost(String httpsUrl, String param, String charset)
			throws Exception {
		HttpURLConnection urlCon = null;
		OutputStream os = null;
		BufferedReader in = null;
		try {
			urlCon = (HttpURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length",
					String.valueOf(param.getBytes(CHARSET).length));
			urlCon.setUseCaches(false);
			os = urlCon.getOutputStream();
			os.write(param.getBytes(charset));
			os.flush();
			in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream(), charset));
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} finally {
			IOClose.close(os, in);
			if (urlCon != null)
				urlCon.disconnect();
		}
	}
}
