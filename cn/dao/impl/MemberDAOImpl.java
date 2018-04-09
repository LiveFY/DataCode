package cn.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.dao.IMemberDAO;
import cn.util.dao.abs.AbstractDAO;
import cn.vo.Member;
/**
 * 实现具体的数据层的开发操作，每一个数据层的对象都表示一个具体的SQL操作
 */
public class MemberDAOImpl extends AbstractDAO implements IMemberDAO {
	@Override
	public boolean doCreate(Member vo) throws SQLException {
		String sql = "INSERT INTO member(mid,name,age,salary,sex,note,birthday) VALUES (?,?,?,?,?,?,?)" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, vo.getMid());
		super.pstmt.setString(2, vo.getName());
		super.pstmt.setInt(3, vo.getAge());
		super.pstmt.setDouble(4, vo.getSalary());
		super.pstmt.setString(5, vo.getSex());
		super.pstmt.setString(6, vo.getNote());
		super.pstmt.setDate(7, new java.sql.Date(vo.getBirthday().getTime()));
		return super.pstmt.executeUpdate() > 0 ;
	} 

	@Override
	public boolean doEdit(Member vo) throws SQLException {
		String sql = "UPDATE member SET name=?,age=?,salary=?,sex=?,note=?,birthday=? WHERE mid=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, vo.getName());
		super.pstmt.setInt(2, vo.getAge());
		super.pstmt.setDouble(3, vo.getSalary());
		super.pstmt.setString(4, vo.getSex());
		super.pstmt.setString(5, vo.getNote());
		super.pstmt.setDate(6, new java.sql.Date(vo.getBirthday().getTime()));
		super.pstmt.setString(7, vo.getMid());
		return super.pstmt.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemove(Set<String> ids) throws SQLException {
		StringBuffer sql = new StringBuffer() ; // 需要进行SQL的拼凑处理
		sql.append("DELETE FROM member WHERE mid IN (") ;
		for (String id : ids) {
			sql.append("'").append(id).append("',") ;
		}
		sql.delete(sql.length() - 1, sql.length()) ; // 删除最后多余的“,”
		sql.append(")") ; // 完成SQL
		super.pstmt = super.conn.prepareStatement(sql.toString()) ;
		return super.pstmt.executeUpdate() > 0; 
	}

	@Override
	public Member findById(String id) throws SQLException {
		Member vo = null ;
		String sql = "SELECT mid,name,age,salary,sex,note,birthday FROM member WHERE mid=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, id);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {	// 有数据发现
			vo = new Member() ; // 实例化VO类对象，将所有的列数据保存到VO类之中
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2)); 
			vo.setAge(rs.getInt(3));
			vo.setSalary(rs.getDouble(4));
			vo.setSex(rs.getString(5));
			vo.setNote(rs.getString(6));
			vo.setBirthday(rs.getDate(7));
		}
		return vo;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		List<Member> all = new ArrayList<Member>() ;
		String sql = "SELECT mid,name,age,salary,sex,note,birthday FROM member" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {	// 有数据发现
			Member vo = new Member() ; // 实例化VO类对象，将所有的列数据保存到VO类之中
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2)); 
			vo.setAge(rs.getInt(3));
			vo.setSalary(rs.getDouble(4));
			vo.setSex(rs.getString(5));
			vo.setNote(rs.getString(6));
			vo.setBirthday(rs.getDate(7));
			all.add(vo) ;
		}
		return all;
	}

	@Override
	public List<Member> findSplit(long currentPage, int lineSize) throws SQLException {
		List<Member> all = new ArrayList<Member>() ;
		String sql = "SELECT * FROM ( "
				+ " SELECT mid,name,age,salary,sex,note,birthday,ROWNUM rn FROM member "
				+ " WHERE ROWNUM<=?) temp WHERE temp.rn>? " ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setLong(1, currentPage * lineSize);
		super.pstmt.setLong(2, (currentPage - 1) * lineSize);
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {	// 有数据发现
			Member vo = new Member() ; // 实例化VO类对象，将所有的列数据保存到VO类之中
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2)); 
			vo.setAge(rs.getInt(3));
			vo.setSalary(rs.getDouble(4));
			vo.setSex(rs.getString(5));
			vo.setNote(rs.getString(6));
			vo.setBirthday(rs.getDate(7));
			all.add(vo) ;
		}
		return all;
	}

	@Override
	public List<Member> findSplit(long currentPage, int lineSize, String column, String keyWord) throws SQLException {
		List<Member> all = new ArrayList<Member>() ;
		String sql = "SELECT * FROM ( " + " SELECT mid,name,age,salary,sex,note,birthday,ROWNUM rn FROM member "
				+ " WHERE " + column + " LIKE ? AND ROWNUM<=?) temp WHERE temp.rn>? ";
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, "%" + keyWord + "%");
		super.pstmt.setLong(2, currentPage * lineSize);
		super.pstmt.setLong(3, (currentPage - 1) * lineSize);
		ResultSet rs = super.pstmt.executeQuery() ;
		while (rs.next()) {	// 有数据发现
			Member vo = new Member() ; // 实例化VO类对象，将所有的列数据保存到VO类之中
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2)); 
			vo.setAge(rs.getInt(3));
			vo.setSalary(rs.getDouble(4));
			vo.setSex(rs.getString(5));
			vo.setNote(rs.getString(6));
			vo.setBirthday(rs.getDate(7));
			all.add(vo) ;
		}
		return all;
	}

	@Override
	public Long getAllCount() throws SQLException {
		return super.handleGetAllCount("member") ; 
	}

	@Override
	public Long getAllCount(String column, String keyWord) throws SQLException {
		String sql = "SELECT COUNT(*) FROM member WHERE " + column + " LIKE ?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, "%" + keyWord + "%");
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			return rs.getLong(1) ;
		}
		return 0L ;
	}

}
