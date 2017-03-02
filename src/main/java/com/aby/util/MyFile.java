package com.aby.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFile {
	public static final String DEFAULT_PATH = getRuleFilePath();

	/**
	 * 如果不存在这个文件，则创建一个
	 * @param filePath	文件的路径，最后必须有一个“/”
	 * @param fileName  文件名
	 * @return
	 */
	public static boolean createFile(String filePath, String fileName) {
		String fullPath = filePath + fileName;
		boolean result = false;
		File file = new File(fullPath);
		if (!file.exists()) {
			try {
				result = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 删除一个文件
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String filePath, String fileName) {
		boolean result = false;
		String fullPath = filePath + fileName;
		File file = new File(fullPath);
		if (file.exists() && file.isFile()) {
			result = file.delete();
		}
		return result;
	}

	/**
	 * * 创建一个文件，并写如内容
	 * @param filePath
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(String filePath, String fileName, String content) {
		createFile(filePath, fileName);
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(filePath + fileName, false);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取规则文件存放的路径
	 * @return
	 */
	public static String getRuleFilePath() {
		String classPath = MyFile.class.getResource("/").getFile();
		String projectPath = classPath + "../../";
		return projectPath + "src/main/resources/rules/";
	}
}
