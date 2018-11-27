package cn.gdqy.aotw.service;

import java.util.List;

//离线状态下的消息寄存处理接口
public interface MessageService {
	/**
	 * 查看指定仓库是否存在
	 * @param storageName  	仓库的名称
	 * @return	
	 */
	boolean isStorageExist(String storageName);
	
	/**
	 * 获取指定仓库的所有内容
	 * @param storageName	仓库的名称
	 * @return 	仓库的内容
	 */
	List<String> getStorageContent(String storageName);
	
	/**
	 * 删除指定的仓库
	 * @param storageName 	仓库的名称
	 */
	void dropStrorage(String storageName);
	
	/**
	 * 保存内容到指定仓库中
	 * @param storageName	要保存到的仓库名称（仓库不存在时将自动创建仓库）
	 * @param content		要保存到仓库的内容
	 */
	void saveContentToStorage(String storageName, String content);
}
