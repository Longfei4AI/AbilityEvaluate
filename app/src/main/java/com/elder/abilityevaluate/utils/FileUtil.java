package com.elder.abilityevaluate.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xcj 文件操作类
 */
public class FileUtil {

	private String sdPath;

	public FileUtil() {
		// 获得SD卡路径
		sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExists(String filePath) {
		if (filePath == null || "".equals(filePath))
			return false;
		File file = new File(filePath);
		if (file.isDirectory())
			return false;
		return file.exists();
	}

	/**
	 * 创建文件路径
	 * 
	 * @param dirPath
	 * @return
	 */
	public File creatDir(String dirPath) {
		// if path is null,return
		if (null == dirPath)
			return null;
		File dir = new File(sdPath + dirPath);
		// if path exists,return
		if (dir.exists())
			return dir;
		System.err.println(dir.isDirectory());
		dir.mkdirs();
		return dir;
	}

	/**
	 * 根据文件路径和文件名创建文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public File creatFile(String filePath, String fileName) {
		// if the file path or file name is null,return null;
		if (filePath == null || fileName == null)
			return null;
		File file = new File(sdPath + filePath + fileName);
		// if File is already existed,return;
		if (file.exists())
			return file;
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.err.println("创建文件失败！");
		}
		return file;
	}

	/**
	 * 写入文件
	 * @param path
	 * @return
	 */
	public boolean write2File(String path, InputStream is) {

		// 路径或者数据为空时，返回
		if (path == null || is == null) {
			return false;
		}

		File file = new File(sdPath + path);
		FileOutputStream fos = null;
		if (file.isDirectory()) {
			return false;
		}
		try {
			fos = new FileOutputStream(file);
			byte[] bytes = new byte[4 * 1024];
			while (is.read(bytes) != -1) {
				fos.write(bytes);
			}

			fos.flush();
		} catch (FileNotFoundException e) {
			System.err.println("指定路径不存在！");
		} catch (IOException e) {
			System.err.println("文件写入异常！");
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					System.err.println("文件关闭异常！");
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

}
