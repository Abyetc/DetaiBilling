package com.aby.data;

import java.time.LocalTime;

import com.aby.row.CallingRow;
import com.aby.util.MyTime;
import com.aby.util.TelRelation;

public class CallingEvent {
	private String packageName;
	private String type;	// 通话类型：本地通话等
	private String callType;	// 是否是主叫
	private String oppositeNumType;		// 对方号码类型：普通号码，亲情号码
	private int dur;		// 通话时长
	private int beginTime;	// 通话起始时间
	private int fee;		// 费用
	private String description;		// 这个通话的相关描述信息，用于在免费资源用尽的时候记录一下
	
	public CallingEvent(String packageName, String type, String callType, String oppositeNumType, int dur, int beginTime, int fee, String description) {
		super();
		this.packageName = packageName;
		this.type = type;
		this.callType = callType;
		this.oppositeNumType = oppositeNumType;
		this.dur = dur;
		this.beginTime = beginTime;
		this.fee = fee;
		this.description = description;
	}

	/**
	 * 由原始数据进行一下封装，得到drools规则中的“通话时间”
	 * @param packageName	需要匹配的套餐名字
	 * @param cr			原始的一次通话数据
	 */
	public CallingEvent(String packageName, CallingRow cr){
		super();
		this.packageName = packageName;
		type = cr.getType();
		callType = cr.getCallType();
		oppositeNumType = TelRelation.getOppositeNumType(cr.getOppositeNum());
		dur = MyTime.getMin(cr.getDuration());
		beginTime = MyTime.getTimeNum(cr.getTime());
		fee = 0;
		description = cr.toString();
	}

	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getOppositeNumType() {
		return oppositeNumType;
	}

	public void setOppositeNumType(String oppositeNumType) {
		this.oppositeNumType = oppositeNumType;
	}

	public int getDur() {
		return dur;
	}

	public void setDur(int dur) {
		this.dur = dur;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

