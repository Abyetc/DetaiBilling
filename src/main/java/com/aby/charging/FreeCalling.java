package com.aby.charging;

import java.util.List;

import com.aby.data.CallingEvent;
import com.thoughtworks.xstream.mapper.Mapper.Null;

public class FreeCalling {
	
	private String description;		// 关于这个免费资源的描述,也是这个免费资源的规则名，rule name
	private List<String> types;		// 可用的通话类型，比如本地通话或者国内通话等
	private String callType;		// 呼叫类型，主叫或者被叫		
	private int amount;				// 免费资源的总量 
	private int beginTime;			// 免费资源可用的开始时间,用一个六位整数表示，前面两位是小时，中间两位表示分，后面两位表示秒
	private int endTime;			// 免费资源可用的结束时间
	private String runOutEvent;		// 关于耗尽这个免费资源的一个描述信息
	private int salience;			// 优先级（越大越高）
	private String oppositeNumType;		// 对方号码类型：普通号码，亲情号码
	
	public boolean isFullTimeAvailable(){
		if (beginTime == 0 && endTime == 235959) {
			return true;
		}else {
			return false;
		}
	}

	public FreeCalling(String description, List<String> types, String callType, int amount, int beginTime,
			int endTime, int salience, String oppositeNumType) {
		super();
		this.salience = salience;
		this.description = description;
		this.types = types;
		this.callType = callType;
		this.amount = amount;
		this.beginTime = beginTime;
		this.endTime = endTime;
		runOutEvent = "";
		this.oppositeNumType = oppositeNumType;
	}

	
	public String getOppositeNumType() {
		return oppositeNumType;
	}

	public void setOppositeNumType(String oppositeNumType) {
		this.oppositeNumType = oppositeNumType;
	}

	public int getSalience() {
		return salience;
	}

	public void setSalience(int salience) {
		this.salience = salience;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public String getRunOutEvent() {
		return runOutEvent;
	}

	public void setRunOutEvent(String runOutEvent) {
		this.runOutEvent = runOutEvent;
	}
	
	
}
