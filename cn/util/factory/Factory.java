package cn.util.factory;

import java.util.ResourceBundle;

import cn.util.service.proxy.ServiceProxy;

/**
 * 实现一个工厂类定义，该工厂类可以返回DAO或业务接口实例 
 */
public class Factory {
	// 定义dao.properties的资源读取类对象
	private static final ResourceBundle DAO_RESOURCE = ResourceBundle.getBundle("cn.resource.dao") ;
	// 定义service.properties的资源读取类对象
	private static final ResourceBundle SERVICE_RESOURCE = ResourceBundle.getBundle("cn.resource.service") ;
	/**
	 * 获取Service接口实例化对象
	 * @param key 要获取资源对象key
	 * @param clazz 对象的接口类型
	 * @return 业务层接口对象，如果不存在返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getServiceInstance(String key, Class<T> clazz) {
		String className = getValue(SERVICE_RESOURCE, key);
		if (className == null || "".equals(className)) {
			return null ;
		} else {
			try {
				return (T) new ServiceProxy(Class.forName(className).getDeclaredConstructor().newInstance()).bind();
			} catch (Exception e) {
				e.printStackTrace();
				return null ;
			} 
		} 
	}
	
	/**
	 * 获取DAO接口实例化对象
	 * @param key 要获取对象的资源文件key
	 * @param clazz 对象的接口类型，用于检测与转型使用
	 * @return 如果配置对象存在则返回数据，如果不存在返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDAOInstance(String key, Class<T> clazz) {
		String className = getValue(DAO_RESOURCE, key);
		if (className == null || "".equals(className)) {
			return null ;
		} else {
			try {
				return (T) Class.forName(className).getDeclaredConstructor().newInstance() ;
			} catch (Exception e) {
				return null ;
			}
		} 
	}
	/**
	 * 实现资源的读取，如果资源为空返回null
	 * @param res 要读取的资源对象
	 * @param key 资源的key
	 * @return 指定key对应的内容，如果没有返回null
	 */
	private static String getValue(ResourceBundle res,String key) {
		try {
			return res.getString(key) ;
		} catch (Exception e) {
			return null ;
		}
	}
}
