package cn.gdqy.aotw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象序列化帮助类
 * @author zrz
 */
public class ObjectSerializableHelper {

	/**
	 * 将对象序列化输出或追加到到文件中，解决追加对象覆盖问题
	 * <p>
	 * 原理：Java默认的对象序列化是每次写入对象都会写入一点头aced
	 * 0005（占4个字节），然后每次读取都读完头然后在读内容。解决方法就是先判断文件是否存在。如果不存在，就先创建文件。然后写了第一个对象，
	 * 也写入了头aced 0005。追加的情况就是当判断文件存在时，把那个4个字节的头aced
	 * 0005截取掉，然后在把对象写入到文件。这样就实现了对象序列化的追加
	 * </p>
	 * @param file 序列化化对象后要保存到的文件
	 * @param obj  要序列化的对象
	 */
	public static void objectSerialize(File file, Serializable obj) {
		FileOutputStream fo = null;
		ObjectOutputStream oos = null;
		try {
			if (file.exists()) {
				fo = new FileOutputStream(file, true);
				oos = new ObjectOutputStream(fo);
				long pos = 0;
				pos = fo.getChannel().position() - 4; // 追加的时候去掉头部aced 0005
				fo.getChannel().truncate(pos);
				oos.writeObject(obj);
			} else {
				file.createNewFile();
				fo = new FileOutputStream(file);
				oos = new ObjectOutputStream(fo);
				oos.writeObject(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fo != null)
					fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将指定文件中的所有对象进行反序列化
	 * @param file 待反序列化对象所在的文件
	 * @param clazz 待反序列对象的类型
	 * @return 反序列化后得到的集合
	 */
	public static <T> List<T> objectDeserialize(File file, Class<T> clazz) {
		if (!file.exists()) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		T obj = null;

		ObjectInputStream ois = null;
		FileInputStream fn = null;
		try {
			fn = new FileInputStream(file);
			ois = new ObjectInputStream(fn);
			while (fn.available() > 0) {// 代表文件还有内容
				obj = (T) ois.readObject();// 从流中读取对象
				list.add(obj);
			}
			
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (ois != null) ois.close();
				if (fn != null) fn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
