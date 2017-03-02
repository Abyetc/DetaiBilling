package com.aby.row;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 电信原始通话详单类（原始数据类型）
 * @author aby
 *
 */
public class CallingRow {
	private LocalDate date;	// 年月日		
	private LocalTime time;		// 时分秒
	private String type;	// 类型：本地通话、省内通话、国内通话等
	private LocalTime duration;		// 通话时长也用这个格式表示，如00:02:30表示通话时长为2分30秒
	private String callType;		// 主叫或者被叫
	private String oppositeNum;		// 对方号码
	private int fee;				// 费用
	
	public CallingRow(LocalDate date, LocalTime time, String type, LocalTime duration, String callType,
			String oppositeNum, int free) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.duration = duration;
		this.callType = callType;
		this.oppositeNum = oppositeNum;
		this.fee = free;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getOppositeNum() {
		return oppositeNum;
	}

	public void setOppositeNum(String oppositeNumType) {
		this.oppositeNum = oppositeNumType;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int free) {
		this.fee = free;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("时间： " + date.toString() + " " + time.toString() + "\n");
		stringBuilder.append("对方号码： " + oppositeNum + "\n");
		stringBuilder.append("类型： " + type + "\n");
		stringBuilder.append("方式： " + callType + "\n");
		stringBuilder.append("时长： " + duration + "\n");
		stringBuilder.append("费用： " + fee + "\n");
		return stringBuilder.toString();
	}
}
