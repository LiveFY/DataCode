package cn.dbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 该类的主要功能是进行数据库连接的获取以及连接的关闭控制，实例化本类对象目的就是为了获取Connection连接对象
 */
public class DatabaseConnection {
	private static final String DATABASE_DRIVER = "oracle.jdbc.driver.OracleDriver" ;
	private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:test" ;
	private static final String DATABASE_USER = "scott" ;
	private static final String DATABASE_PASSWORD = "tiger" ;
	private static final ThreadLocal<Connection> THREADLOCAL = new ThreadLocal<Connection>() ;
	public static void close() throws Exception {
		Connection conn = THREADLOCAL.get() ; // 获取已经存在的连接
		if (conn != null) {
			conn.close(); 
			THREADLOCAL.remove();  // 清除ThreadLocal里面所保存的当前线程的对象 
		}
	}
	/**
	 * 返回一个Connection接口对象，一定是一个实例化对象 
	 * @return 连接对象，如果没有连接返回null
	 */
	public static Connection getConnection() {
		Connection conn = THREADLOCAL.get() ; // 获取连接对象
		if (conn == null) {	// 还没有进行连接
			conn = rebuildConnection() ; // 重新连接
			THREADLOCAL.set(conn); // 保存连接，这样下次进行重复方法调用的时候就不再需要进行连接了
		}
		return conn ; 
	}
	/**
	 * 实现一个建立数据库连接对象的方法
	 * @return 数据库连接实例
	 */
	private static Connection rebuildConnection() {
		try {
			Class.forName(DATABASE_DRIVER) ;
			Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD) ;
			return conn ;
		} catch (Exception e) {
			return null ; 
		}
	}

}
