package cn.gdqy.aotw.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileHelper {
	private final static String SAVE_PATH = "/upload";
	private final static String DEFAULT_FILE_URL = "/upload/default.jpg";

	/**
	 * 保存上传文件
	 * @param file	上传的文件
	 * @return 		访问保存文件的url
	 */
	public static String saveFile(MultipartFile file) {
		if (file == null) {
			return DEFAULT_FILE_URL;
		}
		String realName = file.getOriginalFilename();
		String extName = realName.substring(realName.lastIndexOf("."));
		String path = WebHelper.getServletContext().getRealPath(SAVE_PATH);
		String filename = genFileName() + extName;
		File saveFile = new File(path, filename);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return SAVE_PATH + "/" + filename;
	}
	
	public static String genFileName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		return str;
	}
}
