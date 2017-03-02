package com.aby.util;

/**
 * 这里是用来处理：用对方号码判断本次本机和对方的关系（普通号码，亲情号码）
 * 由于没有足够的数据，暂时默认都是普通号码
 * @author aby
 *
 */
public class TelRelation {
	
	/**
	 * 暂时默认所有的号码都是普通号码
	 * @param oppositeNum	对方号码
	 * @return
	 */
	public static String getOppositeNumType(String oppositeNum){
		return "普通号码";
	}
}
