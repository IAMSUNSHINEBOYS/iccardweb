package com.alen.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码处理
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class PwdUtils {
	private final static String hashAlgorithmName = "MD5";
	private final static int hashIterations = 1024;
	public final static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWo7TwRjUzXbysaV5sKh11WhNvJe9uANyIcw95ffENNQmgNNr1KV7FE9bKumdS9kwKm4Y0k+DOiocLnBO0yNK7Rg44jIt1HYO130qejDo3aVM7QmZUtGo2RSwsRt0GMtDmBlXtA37FKUP44ZuKuD09v14uhPUHPjfAUJ1FxvpkDQIDAQAB";
	private final static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANajtPBGNTNdvKxpXmwqHXVaE28l724A3IhzD3l98Q01CaA02vUpXsUT1sq6Z1L2TAqbhjST4M6KhwucE7TI0rtGDjiMi3Udg7XfSp6MOjdpUztCZlS0ajZFLCxG3QYy0OYGVe0DfsUpQ/jhm4q4PT2/Xi6E9Qc+N8BQnUXG+mQNAgMBAAECgYEAwvPytoKuc7diVZ9nGguushZcrIniTSIPLQiFN66CsaJI2fDoJ+GpqIt6fqyMiLgZHVFhtoPSnwcH3i2nYH8/lxwV6jDISMSSKyTWH5zfzT/dn7bMBSo3u/BRifS3qXFSPXQL0TFL9YdUC2hDZz2FPR12BzQjU9bgLQpUnGHR8KkCQQDsOMFKfkh4zWP5pPoIseA7TT2JBBoHqcHFp4CSub6wLnTGBMItuzqoJoMY5FfQWBGQq+IpGPrqCxSoymOy5Kw/AkEA6JxaJfUnJvCwf42OnoajD82njf/+fZh+Km2RBXki/CnXKHOeZaZWDFZYh54cbT0kzt+OLNWFFwcku9CMkj0MswJBAK/dsUpcqmgFwiDiiqReiaqc3utbemWqJPGau0aBs05Hx/iFImwZsJnv0jp+BkK+zpT+4unmByioB0GDLKbLSNECQQDnQxNpa4vKbj+8APwLZeZWfuuYBSx6qBK/kgE9xMrgk6SjRhf0DeS9DVelNbAjd0fSGXcb4gkY9PQzektX1TR7AkBsLJJ4zLZ6e+lvOSdIRZn4ciiYsc+6HWws2qpkM47KGcU+RtbDvJTxvhB6Yv97IMXvhL0WzB5tMgNDqZi1qocg";

	/**
	 * 获取MD5密码
	 *
	 * @param pwd
	 *            密码
	 * @return MD5加密后密码
	 */
	public static String getPwd(String pwd) {
		String salt = RandomUtils.get8Random();
		ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
		Object result = new SimpleHash(hashAlgorithmName, pwd, credentialsSalt,
				hashIterations);
		return salt + result;
	}

	/**
	 * 获取解密后的密码
	 *
	 * @param rsaPwd
	 *            rsa密码
	 * @return 密码
	 */
	public static String getRealPwd(String rsaPwd) throws Exception {
		byte[] decodedData = RSA.decryptByPrivateKey(rsaPwd, privateKey);
		String pwd = new String(decodedData, RSA.CHARSET);
		return pwd;
	}

	/**
	 * 校验密码
	 *
	 * @param pwd
	 *            密码
	 * @param md5Pwd
	 *            MD5加密后密码
	 * @return
	 */
	public static boolean verifyPwd(String pwd, String md5Pwd) {
		String salt = md5Pwd.substring(0, 8);
		ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
		Object result = new SimpleHash(hashAlgorithmName, pwd, credentialsSalt,
				hashIterations);
		return md5Pwd.equals(salt + result);
	}

	/**
	 * 校验RSA密码
	 *
	 * @param rsaPwd
	 *            RSA加密后密码
	 * @param md5Pwd
	 *            加密后密码
	 * @return
	 */
	public static boolean verifyRSAPwd(String rsaPwd, String md5Pwd)
			throws Exception {
		String pwd = getRealPwd(rsaPwd);
		return verifyPwd(pwd, md5Pwd);
	}
}
