package cn.gdqy.aotw.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.service.MessageService;
import cn.gdqy.aotw.utils.ObjectSerializableHelper;

@Service
public class MessageServiceImpl implements MessageService {

	@Override
	public boolean isStorageExist(String storageName) {
		return new File(GlobalConstant.MESSAGE_STORAGE_PATH, storageName).exists();
	}

	@Override
	public List<String> getStorageContent(String storageName) {
		return ObjectSerializableHelper.objectDeserialize(new File(GlobalConstant.MESSAGE_STORAGE_PATH, storageName), String.class);
	}

	@Override
	public void dropStrorage(String storageName) {
		File file = new File(GlobalConstant.MESSAGE_STORAGE_PATH, storageName);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	public void saveContentToStorage(String storageName, String content) {
		ObjectSerializableHelper.objectSerialize(new File(GlobalConstant.MESSAGE_STORAGE_PATH, storageName), content);
	}

}
