package cn.util.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.dbc.DatabaseConnection;

public class ServiceProxy implements InvocationHandler {
	private Object target; // 保存的就是真实业务主体（业务实现子类）
	/**
	 * 传递真实的业务对象
	 * @param target 业务子类实例
	 */
	public ServiceProxy(Object target) {
		this.target = target;
	}
	@Override
	public Object invoke(Object proxyObject, Method method, Object[] args) throws Throwable {
		Object backObject = null ; // 方法的返回值
		String methodName = method.getName() ; // 获取方法名称
		System.out.println(methodName);
		try {
			if (this.isTranscation(methodName)) {	// 这些都属于更新的方法
				DatabaseConnection.getConnection().setAutoCommit(false); // 取消自动提交事务
				System.out.println("【" + method + "】开启事务控制...");
			}
			// 进行真实主题业务调用
			backObject = method.invoke(this.target, args) ;// 调用真实业务方法
			if (this.isTranscation(methodName)) {	// 这些都属于更新的方法
				DatabaseConnection.getConnection().commit() ;	// 提交事务
				System.out.println("【" + method + "】事务提交...");
			}
		} catch (Exception e) {
			if (this.isTranscation(methodName)) {	// 这些都属于更新的方法
				DatabaseConnection.getConnection().rollback() ; // 事务回滚
				System.out.println("【" + method + "】事务回滚...");
			}
			throw e ;
		} finally {
			DatabaseConnection.close(); // 负责关闭处理
			System.out.println("【" + method + "】关闭数据库连接...");
		}
		return backObject ; 
	}
	/**
	 * 判断当前的方法名称是否需要事务的支持
	 * @param methodName 方法名称
	 * @return 如果需要事务支持返回true，否则返回false
	 */
	private boolean isTranscation(String methodName) {
		return methodName.startsWith("add") || methodName.startsWith("edit") || methodName.startsWith("delete") ;
	}
	/**
	 * 获取绑定的动态代理对象
	 * @return 代理对象
	 * 参数：真实主题对象的类加载器；真实主体对象的全部接口，代理的实现类对象
	 */
	public Object bind() {
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(),
				this);
	}
}
