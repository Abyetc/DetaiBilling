package com.aby.process;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.aby.charging.Calling;


/**
 * This is a sample class to launch a rule.
 */
public class Main {
	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-rules");

			for(int i = 0; i < 10; i++){
				Message message = new Message("M1");
				Sec sec = new Sec("S1");
				sec.setId(110);
				Sec sec2 = new Sec("S2");
				sec2.setId(110);

				// 分开两部分，但是之前fire过的对象不会继续
				message.setMessage("Hello World");
				message.setStatus(Message.HELLO);
				FactHandle factHandle = kSession.insert(sec);

				FactHandle factHandle2 = kSession.insert(message);
				kSession.fireAllRules();
				kSession.delete(factHandle);
				kSession.delete(factHandle2);
				System.out.println("第几个： " + i);
			}
			
			
			

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static class Sec {
		private int id;
		private String info;
		
		public Sec(String info){
			this.info = info;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public String getInfo() {
			return info;
		}


		public void setInfo(String info) {
			this.info = info;
		}
		
	}

	public static class Message {

		public static final int HELLO = 0;
		public static final int GOODBYE = 1;

		private String info;
		private String message;

		private int status;

		
		public Message(String info){
			this.info = info;
		}
		
		
		public String getInfo() {
			return info;
		}


		public void setInfo(String info) {
			this.info = info;
		}


		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}

}
