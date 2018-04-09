package cn.util.dao.abs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.dbc.DatabaseConnection;

public abstract class AbstractDAO {
	protected Connection conn ; // 数据库连接对象
	protected PreparedStatement pstmt ; // 数据库的操作对象
	/**
	 * 实例化数据层对象，但是需要传入连接对象 
	 * @param conn 数据库连接对象
	 */
	public AbstractDAO() { // 抽象类帮助开发者创建数据库连接
		this.conn = DatabaseConnection.getConnection() ; // 获取的是当前线程的连接对象
	}
	/**
	 * 实现数据的统计查询
	 * @param tableName 要查询的数据表
	 * @return 数据表中的数据记录行数
	 * @throws SQLException SQL异常
	 */
	protected Long handleGetAllCount(String tableName) throws SQLException {
		String sql = "SELECT COUNT(*) FROM " + tableName ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getLong(1) ;
		} 
		return 0L ;
	}
}
