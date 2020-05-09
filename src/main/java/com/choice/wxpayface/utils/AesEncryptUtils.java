package com.choice.wxpayface.utils;


import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;*/

public class AesEncryptUtils {
	// 偏移量
	private static final String iv = "0123456789abcdef"; // 16位
	// charSet 字符集编码
	private static final String charsetName = "utf-8";
	//密钥
    public static final String KEY = "fc8a2e9d8a311d67";
    //湘雅医院编码
	public static final String HOSPITAL_CODE_XIANGYA = "812019102478100045";

	/**
	 * 加密
	 *
	 * @param content
	 * @param encryptKey
	 *            Exception
	 */
	@SuppressWarnings("restriction")
	public static String encrypt(String content, String encryptKey){
		try {
			// 参数分别代表 算法名称/加密模式/数据填充方式
			Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = content.getBytes(charsetName);
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(encryptKey.getBytes(charsetName), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(charsetName));
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			content = Base64.encodeToString(encrypted,Base64.DEFAULT);
            content = content.replace("\r\n", "");
			content = content.replace("\n", "");
			// System.out.println("原始密文"+content);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// public static String encrypt(String content, String encryptKey) {
	// if (content == null || encryptKey == null) {
	// throw new NullPointerException("加密内容或加密 key 不能为 null");
	// }
	// if (encryptKey.length() != 16) {
	// throw new RuntimeException("加密的 encryptKey 必须为 16 位");
	// }
	// try {
	// Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
	// cipher.init(Cipher.ENCRYPT_MODE, new
	// SecretKeySpec(encryptKey.getBytes(charsetName), "AES"));
	// byte[] bytes = cipher.doFinal(content.getBytes(charsetName));
	// String result = new sun.misc.BASE64Encoder().encode(bytes);
	// /* 解决 Base64 加密换行问题 */
	// return result;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * 解密
	 *
	 * @param content
	 * @param decryptKey
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static String decrypt(String content, String decryptKey){
		try {
			if (content == null || decryptKey == null) {
				throw new NullPointerException("加密内容或加密 key 不能为 null");
			}
			if (decryptKey.length() != 16) {
				throw new RuntimeException("加密的 encryptKey 必须为 16 位");
			}
            byte[] encrypted1 = new byte[0];
			encrypted1 = Base64.decode(content,Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
			SecretKeySpec keyspec = new SecretKeySpec(decryptKey.getBytes(charsetName), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(charsetName));
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, charsetName);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 * @param String src 加密字符串
	 * @param String key 密钥
	 * @return 加密后的字符串
	 *//*
	public static String encrypt(String src, String key) throws Exception {
		// 判断密钥是否为空
		if (key == null) {
			System.out.print("密钥不能为空");
			return null;
		}

		// 密钥补位
		int plus= 16-key.length();
		byte[] data = key.getBytes("utf-8");
		byte[] raw = new byte[16];
		byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		for(int i=0;i<16;i++)
		{
			if (data.length > i)
				raw[i] = data[i];
			else
				raw[i] = plusbyte[0];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");    // 算法/模式/补码方式
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));

		//return new Base64().encodeToString(encrypted);//base64
		return binary(encrypted, 16); //十六进制
	}

	*//**
	 * 解密
	 * @param String src 解密字符串
	 * @param String key 密钥
	 * @return 解密后的字符串
	 *//*
	public static String decrypt(String src, String key) throws Exception {
		try {
			// 判断Key是否正确
			if (key == null) {
				System.out.print("Key为空null");
				return null;
			}

			// 密钥补位
			int plus= 16-key.length();
			byte[] data = key.getBytes("utf-8");
			byte[] raw = new byte[16];
			byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			for(int i=0;i<16;i++)
			{
				if (data.length > i)
					raw[i] = data[i];
				else
					raw[i] = plusbyte[0];
			}

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			//byte[] encrypted1 = new Base64().decode(src);//base64
			byte[] encrypted1 = toByteArray(src);//十六进制

			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	*//**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 *//*
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);   // 这里的1代表正数
	}

	*//**
	 * 16进制的字符串表示转成字节数组
	 *
	 * @param hexString 16进制格式的字符串
	 * @return 转换后的字节数组
	 **//*
	public static byte[] toByteArray(String hexString) {
		if (hexString.isEmpty())
			throw new IllegalArgumentException("this hexString must not be empty");

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}*/

	@RequiresApi(api = Build.VERSION_CODES.O)
	public static void main(String[] args) throws Exception {
		String key = "fc8a2e9d8a311d67";
		String content = "{\"clientOrderNo\": \"testgww1\", 	\"equipmentCode\": \"QCF420190611031162\", 	\"faceToken\": \"\", 	\"hospitalCode\": \"812018120676200002\", 	\"notifyUrl\": \"\", 	\"payDeadTime\": \"5\", 	\"payOrderAmount\": 1, 	\"payOrderDescription\": \"演示订单描述\", 	\"payOrderName\": \"演示订单名称\", 	\"payOrderSource\": \"mzzj\", 	\"payOrderType\": \"mzzf\", 	\"paySubmitRemark\": \"演示订单备注\", 	\"userIdCardNo\": \"34122419860505611X\", 	\"userMobile\": \"13956906478\", 	\"userRealName\": \"李明\" }";
		System.out.println("开始加密：");
		System.out.println(encrypt(content, key));
//		content = "wKMvB59z+RUdNfzUBeobfA6FwWGh+CPNFYMPc9Ay2zqJAGFSvPWuqo1MfNx4JP1kaT1np9VX61MEQGOuy8xRECu5ZqxNSTthiMqqjLDQVaKVBue3iKFhM5xiue9kGcLd2ELY1D23LyKuADU3cq1yvnbAUy/wksyiM5achrKrLroWJZ9MsFjJdfTxLJ3qrmFUi7NQvOaHWM8P6vv5SOcM+Mn7+67ZfabQV0UTlumQ1lYjbV37mGXUhzVUEglG98qW5sGz89FE5T6gBkMog6QdQzsjRAAUqtCcH8OAe6jobQnBMqJMBAj59zmsMWK3SlpdWkercySMMz0h9Fxl6G+nxPVubqLXolmXvaOEd22qrDyIM+KX+hiGPqwEuQlVQm16uHI2jbWXzXtnGaHW7Nc6xEWU2KrcI7UNw604gnwRP8ZYMEO+Ifdse/V+lOhp9thNS4BAfI/rss0aNEOvvOkP0Ph7IX1lPwO36oONOBn44imxiRcoCH0USReZ8dsTgMmX7TBX/qYmzzLKBOCrfSWJ4qrH161aMYZj7dekW3Q/kKbCMS+7PkNvdg3ffmb3pQ4a";
//		System.out.println(decrypt(content, key));

	}
}