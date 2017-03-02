package com.aby.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 从excel读取数据/往excel中写入 excel有表头，表头每列的内容对应实体类的属性
 * 
 * @author nagsh
 * 
 */
public class Excel {
	private HSSFWorkbook workbook = null;

	/**
	 * 判断文件是否存在.
	 * 
	 * @param fileDir
	 *            文件路径
	 * @return
	 */
	public boolean fileExist(String fileDir) {
		boolean flag = false;
		File file = new File(fileDir);
		flag = file.exists();
		return flag;
	}

	/**
	 * 判断文件的sheet是否存在.
	 * 
	 * @param fileDir
	 *            文件路径
	 * @param sheetName
	 *            表格索引名
	 * @return
	 */
	public boolean sheetExist(String fileDir, String sheetName) {
		boolean flag = false;
		File file = new File(fileDir);
		if (file.exists()) { // 文件存在
			// 创建workbook
			try {
				workbook = new HSSFWorkbook(new FileInputStream(file));
				// 添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
				HSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet != null)
					flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else { // 文件不存在
			flag = false;
		}

		return flag;
	}

	/**
	 * 创建新excel.
	 * 
	 * @param fileDir
	 *            excel的路径
	 * @param sheetName
	 *            要创建的表格索引
	 * @param titleRow
	 *            excel的第一行即表格头
	 */
	public void createExcel(String fileDir, String sheetName, String titleRow[]) {
		// 创建workbook
		workbook = new HSSFWorkbook();
		// 添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
		Sheet sheet1 = workbook.createSheet(sheetName);
		// 新建文件
		FileOutputStream out = null;
		try {
			// 添加表头
			Row row = workbook.getSheet(sheetName).createRow(0); // 创建第一行
			for (int i = 0; i < titleRow.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(titleRow[i]);
			}

			out = new FileOutputStream(fileDir);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除文件.
	 * 
	 * @param fileDir
	 *            文件路径
	 */
	public boolean deleteExcel(String fileDir) {
		boolean flag = false;
		File file = new File(fileDir);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				file.delete();
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 往excel中写入(已存在的数据无法写入).
	 * 
	 * @param fileDir
	 *            文件路径
	 * @param sheetName
	 *            表格索引
	 * @param object
	 */
	public void writeToExcel(String fileDir, String sheetName, Object object) {
		// 创建workbook
		File file = new File(fileDir);
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 流
		FileOutputStream out = null;
		HSSFSheet sheet = workbook.getSheet(sheetName);
		// 获取表格的总行数
		int rowCount = sheet.getLastRowNum() + 1; // 需要加一
		// 获取表头的列数
		int columnCount = sheet.getRow(0).getLastCellNum();
		try {
			Row row = sheet.createRow(rowCount); // 最新要添加的一行
			// 通过反射获得object的字段,对应表头插入
			// 获取该对象的class对象
			Class class_ = object.getClass();
			// 获得表头行对象
			HSSFRow titleRow = sheet.getRow(0);
			if (titleRow != null) {
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) { // 遍历表头
					String title = titleRow.getCell(columnIndex).toString().trim().toString().trim();
					String UTitle = Character.toUpperCase(title.charAt(0)) + title.substring(1, title.length()); // 使其首字母大写;
					String methodName = "get" + UTitle;
					Method method = class_.getDeclaredMethod(methodName); // 设置要执行的方法
					String data = method.invoke(object).toString(); // 执行该get方法,即要插入的数据
					Cell cell = row.createCell(columnIndex);
					cell.setCellValue(data);
				}
			}

			out = new FileOutputStream(fileDir);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 
	 * @param fileDir		excle文件的位置
	 * @param sheetName		读取这个excel文件的哪一个sheet
	 * @return
	 */
	public List<List<String>> readFromExcel(String fileDir, String sheetName) {
		// 创建workbook
		File file = new File(fileDir);
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<List<String>> result = new ArrayList<List<String>>();
		// 获取该对象的class对象
		// 获得该类的所有属性

		// 读取excel数据
		// 获得指定的excel表
		HSSFSheet sheet = workbook.getSheet(sheetName);
		// 获取表格的总行数
		int rowCount = sheet.getLastRowNum() + 1; // 需要加一
		
		if (rowCount <= 1) {
			return result;
		}

		HSSFRow row0 = sheet.getRow(0);
		int columnCount = row0.getLastCellNum(); // 获取一个有多少列
		// 逐行读取数据 从1开始 忽略表头
		for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);
			List<String> list = new ArrayList<String>();
			
			// 获得本行中各单元格中的数据
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				String data = row.getCell(columnIndex).toString();
				list.add(data);
			}
			result.add(list);
		}
		return result;
	}
	
	public static void main(String[] args) {
		Excel excelDemo = new Excel();
		List<List<String>> list = excelDemo.readFromExcel("/home/aby/Desktop/voice.xls", "Sheet1");
		System.out.println(list.size());
		for(int i = 0; i < list.size(); i++){
			List<String> tmpList = list.get(i);
			for (String string : tmpList) {
				System.out.print(string + "  ");
			}
			System.out.println();
		}
	}
}
