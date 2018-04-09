package cn.util.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set; 

/**
 * 数据层操作的标准方法定义：增加、修改、删除、查询全部、根据ID查询、分页查询、模糊查询、数据统计
 * @param <K> 主键的类型
 * @param <V> VO的类型
 */
public interface IDAO<K, V> {
	/**
	 * 实现数据的增加操作
	 * @param vo 保存要增加数据的VO类对象，该对象不可能为null
	 * @return 增加成功返回true，否则返回false
	 * @throws SQLException SQL执行错误
	 */
	public boolean doCreate(V vo) throws SQLException ;
	/**
	 * 实现数据的修改（编辑）操作，该操作主要实现的是根据ID进行全部数据的修改
	 * @param vo  要修改的新数据，一定要包含有主键
	 * @return 修改成功返回true，否则返回false
	 * @throws SQLException SQL执行错误
	 */
	public boolean doEdit(V vo) throws SQLException ;
	/**
	 * 执行数据的删除操作
	 * @param ids 要删除的数据编号
	 * @return 删除成功返回true，否则返回false
	 * @throws SQLException SQL执行错误
	 */
	public boolean doRemove(Set<K> ids) throws SQLException ;
	/**
	 * 根据主键实现指定数据的查询，查询的数据以VO对象的形式保存
	 * @param id 要查询的主键数据
	 * @return 如果该数据存在则将数据转为VO对象存储，如果不存在返回null
	 * @throws SQLException SQL执行错误
	 */
	public V findById(K id) throws SQLException ;
	/**
	 * 查询全部的数据
	 * @return 返回数据的集合，如果没有任何数据存在则返回空集合（不是null，而是size()==0）
	 * @throws SQLException SQL执行错误
	 */
	public List<V> findAll() throws SQLException ;
	/**
	 * 实现数据的分页显示
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示的数据行数
	 * @return 返回数据的集合，如果没有任何数据存在则返回空集合（不是null，而是size()==0）
	 * @throws SQLException SQL执行错误
	 */
	public List<V> findSplit(long currentPage,int lineSize) throws SQLException ;
	/**
	 * 实现数据的模糊查询操作，数据将以分页的形式返回
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示数据行
	 * @param column 模糊查询列
	 * @param keyWord 模糊查询关键字
	 * @return 返回数据的集合，如果没有任何数据存在则返回空集合（不是null，而是size()==0）
	 * @throws SQLException SQL执行错误
	 */
	public List<V> findSplit(long currentPage, int lineSize, String column, String keyWord) throws SQLException;
	/**
	 * 实现数据表的数据行统计
	 * @return COUNT()统计函数执行结果
	 * @throws SQLException SQL执行错误
	 */
	public Long getAllCount() throws SQLException ;
	/**
	 * 实现模糊查询的数据量统计
	 * @param column 查询字段
	 * @param keyWord 查询关键字
	 * @return COUNT()函数统计结果
	 * @throws SQLException SQL执行错误
	 */
	public Long getAllCount(String column,String keyWord) throws SQLException ;
}
