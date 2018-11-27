package cn.gdqy.aotw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.utils.UploadFileHelper;

@Controller
@RequestMapping("/upload/")
public class UploadController {
	
	@ResponseBody
	@RequestMapping("upload")
	public ResultView upload(MultipartFile file) {
		String path = UploadFileHelper.saveFile(file);
		ResultView result = new ResultView();
		result.putData("path", path);
		return result;
	}
}
