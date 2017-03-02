package com.aby.util;

public class MyNumber {
	
	/**
	 * 将字符串解析为整数
	 * @param str
	 * @return
	 */
	public static int str2int(String str){
		double d = Double.parseDouble(str);			// 因为有可能是浮点数
		return (int) d;
	}
}
