package com.aby.process;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.aby.charging.FreeCalling;
import com.aby.data.CallingEvent;
import com.aby.drlScript.DroolsFile;
import com.aby.row.CallingRow;
import com.aby.util.MyFile;

/**
 * This is a sample class to launch a rule.
 */
public class Test {
	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			DroolsFile droolsFile = new DroolsFile("/home/aby/Desktop/", "lexiang4G_59.xls");
			List<FreeCalling> freeCallings = droolsFile.getFreeCallings();		// 获取免费通话资源类
			String droolsContent = droolsFile.getFullContent();					// 获取自动生成的drools文件的内容
			MyFile.writeFile(MyFile.DEFAULT_PATH, "lexiang4G_59.drl", droolsContent);		// 这个没必要每次都执行，执行一次就可以了
			
			for (FreeCalling freeCalling : freeCallings) {
				kSession.insert(freeCalling);
			}
			
			// 构造一个原始数据
			LocalDate date = LocalDate.of(2017, 03, 01);
			LocalTime time = LocalTime.of(10, 30, 00);
			LocalTime duration = LocalTime.of(00, 02, 30);
			CallingRow callingRow = new CallingRow(date, time, "本地通话", duration, "主叫", "13450121345", 0);
			
			CallingEvent callingEvent = new CallingEvent("lexiang4G_59", callingRow);
			kSession.insert(callingEvent);
			callingRow.setFee(callingEvent.getFee());
			System.out.println(callingRow.toString());		// 记录下来
			
			kSession.fireAllRules();
			
			// 好像还漏了总体的计数。。。。。。。就是资源用了多少了之类的，资源统计类带进去CallingEvent里面
			// 最后再检查一下哪些免费资源被用完了
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
