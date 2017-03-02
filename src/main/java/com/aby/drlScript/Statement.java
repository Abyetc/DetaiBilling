package com.aby.drlScript;

import java.util.ArrayList;
import java.util.List;
import com.aby.util.MyTime;

public class Statement {
	public static final String LINE_FEED = "\n"; 
	public static final String INTERVAL = "\t";
	
	public static String ruleNameStm(String ruleName){
		return "rule " + "\"" + ruleName + "\"" + LINE_FEED;
	}
	
	public static String salienceStm(int salience){
		String result = INTERVAL + "salience " + salience + LINE_FEED;
		return result;
	}
	
	public static String whenStm(){
		return INTERVAL + "when" + LINE_FEED;
	}
	
	public static String thenStm(){
		return INTERVAL + "then" + LINE_FEED;
	}
	
	public static String endStm(){
		return "end" + LINE_FEED;
	}
	
	public static String freeCallingStm(String ruleName){
		String result = "f : FreeCalling( amount > 0 && description == \"" + ruleName + "\" && freeAmount : amount)";
		return wrapStr(result);
	}
	
	public static String freeCallingEventStm(String packageName, String oppositeNumType, boolean timeLimit){
		
		String result = "e : CallingEvent(dur > 0 && packageName == \"" + packageName + "\" && f.types contains type && f.callType == callType && oppositeNumType == \"" + oppositeNumType + "\" && eventDur : dur";
		if (timeLimit) {
			result += " && f.beginTime <= beginTime && f.endTime > beginTime";
		}
		
		result += ")";
		return wrapStr(result);
	}
	
	public static String wrapStr(String str) {
		return INTERVAL + INTERVAL + str + LINE_FEED;
	}
	
	
	public static String freeStm(boolean timeLimit){
		String result = wrapStr("int min = Math.min(freeAmount, eventDur);");
		if (timeLimit) {
			result += wrapStr("min = Math.min(min, MyTime.countMinutes(f.getEndTime(), e.getBeginTime()));");
		}
		result += wrapStr("f.setAmount(freeAmount - min);");
		result += wrapStr("e.setDur(eventDur - min);");
		result += wrapStr("if(freeAmount == min){");
		result += wrapStr("    f.setRunOutEvent(e.getDescription());");
		result += wrapStr("}");
		result += wrapStr("update (e);");
		result += wrapStr("update (f);");
		
		return result;
	}
	
	
	/**
	 * 免费语音资源匹配规则
	 * @param ruleName		规则名字
	 * @param salience		规则级别，数字越大，级别越大，越先执行
	 * @param oppositeNumType	对方是普通号码或者亲情号码等
	 * @param timeLimit		是否有时段的限制
	 * @return
	 */
	public static String getFreeCallingRule(String packageName, String ruleName, int salience, String oppositeNumType, boolean timeLimit){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ruleNameStm(ruleName));
		stringBuilder.append(salienceStm(salience));
		stringBuilder.append(whenStm());
		stringBuilder.append(freeCallingStm(ruleName));
		stringBuilder.append(freeCallingEventStm(packageName, oppositeNumType, timeLimit));
		stringBuilder.append(thenStm());
		stringBuilder.append(freeStm(timeLimit));
		stringBuilder.append(endStm());
		
		String result = stringBuilder.toString();
		return result;
	}
	
	public static String chargeCalling(String packageName, List<String> types, String oppositeNumType, boolean timeLimit, String callType,int beginTime, int endTime){
		String typesStr = "";
		int len = types.size();
		for(int i = 0; i < len - 1; i++){
			typesStr += "type == \"" + types.get(i) + "\" || " ;
		}
		typesStr += "type == \"" + types.get(len - 1) + "\"";
		if (len > 1) {
			typesStr = "( " + typesStr + ") "; 
		}
	
		String result = "e : CallingEvent(dur > 0 && packageName == \"" + packageName + "\" && " + typesStr + " && oppositeNumType == \"" + oppositeNumType + "\" && callType == \"" + callType + "\" && eventDur : dur";
		if (timeLimit) {
			result += " && beginTime >= " + beginTime + " && beginTime < " + endTime;
		}
		
		result += ")";
		return wrapStr(result);
	}
	
	public static String chargeStm(int unitPrice){
		String result = wrapStr("int fee = " + unitPrice + " * eventDur;");
		result += wrapStr("e.setFee(e.getFee() + fee);");
		result += wrapStr("e.setDur(0);");
		result += wrapStr("update (e);");
		return result;
	}
	
	public static String chargeTimeLimitStm(int unitPrice, int endTime){
		String str = "int max = MyTime.countMinutes(" +  endTime + ", e.getBeginTime());";
		String result = wrapStr(str);
		result += wrapStr("int min = Math.min(max, eventDur);");
		result += wrapStr("int fee = " + unitPrice + " * min;");
		result += wrapStr("e.setFee(e.getFee() + fee);");
		result += wrapStr("e.setDur(eventDur - min);");
		result += wrapStr("int newBeginTime = MyTime.addMins(e.getBeginTime(), min);");
		result += wrapStr("e.setBeginTime(newBeginTime);");
		result += wrapStr("update (e);");
		return result;
	}
	
	/**
	 * 自动生成语音收费资源规则
	 * @param ruleName			规则的描述
	 * @param salience			规则的优先级
	 * @param oppositeNumType	对方号码类型（普通号码等）
	 * @param timeLimit			是否有时间段的限制
	 * @param callType			主叫还是被叫
	 * @param types				这个规则试用于哪一些通话类型，如本地通话、省内通话、国内通话等
	 * @param beginTime			规则适用的起始时间
	 * @param endTime			规则适用的结束时间
	 * @param unitPrice			每分钟收费单价
	 * @return
	 */
	public static String getChargeRule(String packageName, String ruleName, int salience, String oppositeNumType, boolean timeLimit, String callType, List<String> types,int beginTime, int endTime, int unitPrice ){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ruleNameStm(ruleName));
		stringBuilder.append(salienceStm(salience));
		stringBuilder.append(whenStm());
		
		stringBuilder.append(chargeCalling(packageName, types, oppositeNumType, timeLimit, callType, beginTime, endTime));
		stringBuilder.append(thenStm());
		if (timeLimit) {
			stringBuilder.append(chargeTimeLimitStm(unitPrice, endTime));
		}else {
			stringBuilder.append(chargeStm(unitPrice));
		}
		
		stringBuilder.append(endStm());
		
		String result = stringBuilder.toString();
		return result;
	}
	
	
	
	public static void main(String[] args) {
		String str = getFreeCallingRule("lexiang4G_59", "本地通话_免费资源", 2, "普通号码", true);
		System.out.println(str);
		
		String str2 = getFreeCallingRule("lexiang4G_59", "国内通话_免费资源", 1, "普通号码", false);
		System.out.println(str2);
		
		List<String> types = new ArrayList<String>();
		types.add("本地通话");
		String str3 = getChargeRule("lexiang4G_59", "本地通话", 10, "普通号码", false, "主叫", types, MyTime.beginTime, MyTime.endTime, 10);;
		System.out.println(str3);
		
		types.add("国内通话");
		String str4 = getChargeRule("lexiang4G_59", "国内通话", 6, "普通号码", true, "主叫", types, MyTime.beginTime, MyTime.endTime, 10);;
		System.out.println(str4);
	}
	
}
