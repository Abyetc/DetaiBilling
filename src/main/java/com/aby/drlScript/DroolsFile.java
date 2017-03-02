package com.aby.drlScript;

import java.util.ArrayList;
import java.util.List;

import com.aby.charging.FreeCalling;
import com.aby.data.CallingEvent;
import com.aby.util.Excel;
import com.aby.util.MyFile;
import com.aby.util.MyNumber;
import com.aby.util.MyTime;

public class DroolsFile {
	private String packageName; // 套餐名字
	private List<FreeCalling> freeCallings; // 免费通话资源
	private List<String> callingEventStr; // 语音收费资源的drools语句
	private List<String> freeCallingStr; // 免费通话资源的drools语句
	private String packageStr; // 这个规则文件的归属包
	private List<String> importStrs; // 需要import的资源

	/**
	 * 通过给定的excel套餐描述文件，构造出DroolsFile
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param fileName
	 *            文件的名字
	 */
	public DroolsFile(String filePath, String fileName) {
		this.packageName = fileName.split("\\.")[0];
		importStrs = new ArrayList<String>();
		setDefaultPackageAndImport();
		freeCallings = new ArrayList<FreeCalling>();
		callingEventStr = new ArrayList<String>();
		freeCallingStr = new ArrayList<String>();

		Excel excel = new Excel();
		List<List<String>> list = excel.readFromExcel(filePath + fileName, "call");
		for (List<String> list2 : list) {
			if (!list2.get(7).equals("")) { // 有免费资源
				FreeCalling freeCalling = getFreeCalling(list2);
				freeCallings.add(freeCalling);
				freeCallingStr.add(getFreeCallingStr(freeCalling));
			} else { // 收费资源
				String str = getCallingEventStr(list2);
				callingEventStr.add(str);
			}
		}
	}

	private void setDefaultPackageAndImport() {
		packageStr = "package com.aby.charging;";
		importStrs.add("import com.aby.charging.*;");
		importStrs.add("import com.aby.data.*;");
		importStrs.add("import com.aby.util.*;");
	}

	public String getPackageStr() {
		return packageStr;
	}

	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}

	public List<String> getImportStrs() {
		return importStrs;
	}

	public void setImportStrs(List<String> importStrs) {
		this.importStrs = importStrs;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<FreeCalling> getFreeCallings() {
		return freeCallings;
	}

	public void setFreeCallings(List<FreeCalling> freeCallings) {
		this.freeCallings = freeCallings;
	}

	public List<String> getCallingEventStr() {
		return callingEventStr;
	}

	public void setCallingEventStr(List<String> callingEventStr) {
		this.callingEventStr = callingEventStr;
	}

	public List<String> getFreeCallingStr() {
		return freeCallingStr;
	}

	public void setFreeCallingStr(List<String> freeCallingStr) {
		this.freeCallingStr = freeCallingStr;
	}

	/**
	 * 从免费资源的描述中生成这个免费资源类
	 * 
	 * @param list
	 * @return
	 */
	public FreeCalling getFreeCalling(List<String> list) {
		int salience = MyNumber.str2int(list.get(0));
		List<String> types = new ArrayList<String>();
		String[] typeStr = list.get(1).split("/");
		for (int i = 0; i < typeStr.length; i++) {
			types.add(typeStr[i]);
		}
		String callType = list.get(2);
		String oppositeNumType = list.get(3);
		String beginStr = list.get(4);
		String endStr = list.get(5);
		int beginTime = MyTime.beginTime;
		int endTime = MyTime.endTime;
		if (!beginStr.equals("") && !endStr.equals("")) {
			beginTime = MyNumber.str2int(beginStr);
			endTime = MyNumber.str2int(endStr);
		}

		int amount = MyNumber.str2int(list.get(7));
		String description = list.get(8);

		FreeCalling freeCalling = new FreeCalling(description, types, callType, amount, beginTime, endTime, salience,
				oppositeNumType);
		return freeCalling;

	}

	/**
	 * 通过免费资源类生成关于这个免费资源的drools语句
	 * 
	 * @param f
	 * @return
	 */
	public String getFreeCallingStr(FreeCalling f) {
		String ruleName = f.getDescription();
		int salience = f.getSalience();
		String oppositeNumType = f.getOppositeNumType();
		boolean timeLimit = false;
		if (f.getBeginTime() == MyTime.beginTime && f.getEndTime() == MyTime.endTime) {
			timeLimit = true;
		}
		return Statement.getFreeCallingRule(packageName, ruleName, salience, oppositeNumType, timeLimit);
	}

	/**
	 * 利用从excel读出的语音收费部分，自动生成drools语句
	 * 
	 * @param list
	 * @return
	 */
	public String getCallingEventStr(List<String> list) {
		int salience = MyNumber.str2int(list.get(0));

		List<String> types = new ArrayList<String>();
		String[] typeStr = list.get(1).split("/"); // java正则表达式一个"\"要用四个来表示
		for (int i = 0; i < typeStr.length; i++) {
			types.add(typeStr[i]);
		}
		String callType = list.get(2);
		String oppositeNumType = list.get(3);
		String beginStr = list.get(4);
		String endStr = list.get(5);
		int beginTime = MyTime.beginTime;
		int endTime = MyTime.endTime;
		boolean timeLimit = false;
		if (!beginStr.equals("") && !endStr.equals("")) {
			beginTime = MyNumber.str2int(beginStr);
			endTime = MyNumber.str2int(endStr);
			timeLimit = true;
		}

		int unitPrice = MyNumber.str2int(list.get(6));
		String ruleName = list.get(8);

		return Statement.getChargeRule(ruleName, ruleName, salience, oppositeNumType, timeLimit, callType, types,
				beginTime, endTime, unitPrice);
	}

	/**
	 * 生成drl文件的package和import部分
	 * @return
	 */
	public String getPackageAndImport() {
		String result = packageStr + "\n";
		for (String string : importStrs) {
			result += string + "\n";
		}
		return result;
	}
	
	/**
	 * 返回这个规则文件的全部内容（字符串形式）
	 * @return
	 */
	public String getFullContent(){
		String result = getPackageAndImport();
		result += "\n";
		for (String string : freeCallingStr) {
			result += string;
			result += "\n";
		}
		for (String string : callingEventStr) {
			result += string;
			result += "\n";
		}
		return result;
	}

	public static void main(String[] args) {
		DroolsFile droolsFile = new DroolsFile("/home/aby/Desktop/", "lexiang4G_59.xls");
		List<String> free = droolsFile.getFreeCallingStr();
		List<String> charge = droolsFile.getCallingEventStr();

		for (String string : free) {
			System.out.println(string);
		}

		for (String string : charge) {
			System.out.println(string);
		}
	}
}
