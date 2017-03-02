package com.aby.charging;

public class Calling {
	
	private int VOICE; // 总计费时长(分钟)
	private int VOICE_CALLING; // 主叫总计费时长(分钟)
	private int VOICE_CALLED; // 被叫总计费时长(分钟)

	private int LOC_CALLING; // 本地主叫计费时长(分钟)
	private int DDD_CALLING; // 国内主叫计费时长(分钟)
	private int IDD_CALLING; // 国际主叫计费时长(分钟)
	private int GAT_CALLING; // 港澳台主叫计费时长(分钟)
	private int INNER_MY_CALLING; // 国内漫游主叫计费时长(分钟)
	private int INTER_MY_CALLING; // 国际漫游主叫计费时长(分钟)
	private int GAT_MY_CALLING; // 港澳台漫游主叫计费时长(分钟)

	private int LOC_CALLED; // 本地被叫计费时长(分钟)
	private int INNER_MY_CALLED; // 国内漫游被叫计费时长(分钟)
	private int INTER_MY_CALLED; // 国际漫游被叫计费时长(分钟)
	private int GAT_MY_CALLED; // 港澳台漫游被叫计费时长(分钟)
	
	public Calling(int lOC_CALLING, int dDD_CALLING, int iDD_CALLING, int gAT_CALLING, int iNNER_MY_CALLING,
			int iNTER_MY_CALLING, int gAT_MY_CALLING, int lOC_CALLED, int iNNER_MY_CALLED, int iNTER_MY_CALLED,
			int gAT_MY_CALLED) {
		super();
		LOC_CALLING = lOC_CALLING;
		DDD_CALLING = dDD_CALLING;
		IDD_CALLING = iDD_CALLING;
		GAT_CALLING = gAT_CALLING;
		INNER_MY_CALLING = iNNER_MY_CALLING;
		INTER_MY_CALLING = iNTER_MY_CALLING;
		GAT_MY_CALLING = gAT_MY_CALLING;
		LOC_CALLED = lOC_CALLED;
		INNER_MY_CALLED = iNNER_MY_CALLED;
		INTER_MY_CALLED = iNTER_MY_CALLED;
		GAT_MY_CALLED = gAT_MY_CALLED;
	}

	public int getLOC_CALLING() {
		return LOC_CALLING;
	}

	public void setLOC_CALLING(int lOC_CALLING) {
		LOC_CALLING = lOC_CALLING;
	}

	public int getDDD_CALLING() {
		return DDD_CALLING;
	}

	public void setDDD_CALLING(int dDD_CALLING) {
		DDD_CALLING = dDD_CALLING;
	}

	public int getIDD_CALLING() {
		return IDD_CALLING;
	}

	public void setIDD_CALLING(int iDD_CALLING) {
		IDD_CALLING = iDD_CALLING;
	}

	public int getGAT_CALLING() {
		return GAT_CALLING;
	}

	public void setGAT_CALLING(int gAT_CALLING) {
		GAT_CALLING = gAT_CALLING;
	}

	public int getINNER_MY_CALLING() {
		return INNER_MY_CALLING;
	}

	public void setINNER_MY_CALLING(int iNNER_MY_CALLING) {
		INNER_MY_CALLING = iNNER_MY_CALLING;
	}

	public int getINTER_MY_CALLING() {
		return INTER_MY_CALLING;
	}

	public void setINTER_MY_CALLING(int iNTER_MY_CALLING) {
		INTER_MY_CALLING = iNTER_MY_CALLING;
	}

	public int getGAT_MY_CALLING() {
		return GAT_MY_CALLING;
	}

	public void setGAT_MY_CALLING(int gAT_MY_CALLING) {
		GAT_MY_CALLING = gAT_MY_CALLING;
	}

	public int getLOC_CALLED() {
		return LOC_CALLED;
	}

	public void setLOC_CALLED(int lOC_CALLED) {
		LOC_CALLED = lOC_CALLED;
	}

	public int getINNER_MY_CALLED() {
		return INNER_MY_CALLED;
	}

	public void setINNER_MY_CALLED(int iNNER_MY_CALLED) {
		INNER_MY_CALLED = iNNER_MY_CALLED;
	}

	public int getINTER_MY_CALLED() {
		return INTER_MY_CALLED;
	}

	public void setINTER_MY_CALLED(int iNTER_MY_CALLED) {
		INTER_MY_CALLED = iNTER_MY_CALLED;
	}

	public int getGAT_MY_CALLED() {
		return GAT_MY_CALLED;
	}

	public void setGAT_MY_CALLED(int gAT_MY_CALLED) {
		GAT_MY_CALLED = gAT_MY_CALLED;
	}

	
	// 总语音是主叫加被叫的和
	public int getVOICE() {
		return getVOICE_CALLING() + getVOICE_CALLED();
	}

	// 主叫的总和
	public int getVOICE_CALLING() {
		return LOC_CALLING + DDD_CALLING + IDD_CALLING + GAT_CALLING + INNER_MY_CALLING + INTER_MY_CALLING + GAT_MY_CALLING;
	}

	// 被叫的总和
	public int getVOICE_CALLED() {
		return LOC_CALLED + INNER_MY_CALLED + INTER_MY_CALLED + GAT_MY_CALLED;
	}

}
