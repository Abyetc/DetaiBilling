package com.aby.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class MyTime{
	public static final int beginTime = 0;
	public static final int endTime = 235959;

	private int hour;
	private int minute;
	private int second;
	
	/**
	 * 将通话时长转换为实际计费的分钟数
	 * @param time
	 */
	public static int getMin(LocalTime time){
		int h = time.getHour();
		int m = time.getMinute();
		int s = time.getSecond();
		int result = 60 * h + m;
		if (s != 0) {
			result += 1;
		}
		return result;
	}
	
	/**
	 * 将"时:分:秒"转换为一个整数“时分秒”
	 * @param time
	 * @return
	 */
	public static int getTimeNum(LocalTime time){
		int h = time.getHour();
		int m = time.getMinute();
		int s = time.getSecond();
		return h * 10000 + m * 100 + s;
	}
	
	public MyTime(int time){
		hour = time / 10000;			// 小时
		minute = (time / 100) % 100;		// 分钟
		second = time % 100;				// 秒
		
	}
	
	/**
	 * 在一个时间（六位数表示的时分秒）上加上指定的分钟数
	 * @param time	给定的时间
	 * @param mins	需要加的分钟数
	 * @return		加上之后的结果
	 */
	public static int addMins(int time, int mins){
		int h = time / 10000;			// 小时
		int m = (time / 100) % 100;		// 分钟
		int s = time % 100;				// 秒
		
		m += mins;
		int tmp = m / 60;	// 进位
		m %= 60;
		h += tmp;
		h %= 24;
		
		return h * 10000 + m * 100 + s;
	}
	
	public static int countMinutes(int time1, int time2){
		if (time1 > time2) {
			int t = time1;
			time1 = time2;
			time2 = t;
		}
		
		MyTime myTime1 = new MyTime(time1);
		MyTime myTime2 = new MyTime(time2);
		
		int result = 0;
		if (myTime1.second < myTime2.second) {
			result++;
		}
		
		result += myTime2.hour * 60 + myTime2.minute - (myTime1.hour * 60 + myTime1.minute);
		return result;
	}
	
	public static void main(String[] args) {
		int t = 5930;
		t = MyTime.addMins(t, 20);
		System.out.println(t);
	}
}
