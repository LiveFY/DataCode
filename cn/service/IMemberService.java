package cn.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.vo.Member;
/**
 * 定义业务层的标准，每一个业务层接口对象都拥有各自独立的事务与数据库连接
 */
public interface IMemberService {
	/**
	 * 实现用户数据的增加，该操作要执行如下处理：
	 * 1、检查传入的年龄数据与性别数据是否合法；
	 * 2、调用IMemberDAO.findById()方法查询用户名是否存在；
	 * 3、调用IMemberDAO.doCreate()方法进行数据保存。
	 * @param vo 要增加的用户数据
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception 增加失败抛出的异常
	 */
	public boolean add(Member vo) throws Exception ;
	/**
	 * 实现用户数据的修改操作，该操作主要执行IMemberDAO.doEdit()方法，该操作为根据主键进行全部数据修改
	 * @param vo 要修改的新数据，一定要包含有已有数据的主键信息
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception 修改失败抛出的异常
	 */
	public boolean edit(Member vo) throws Exception ;
	/**
	 * 实现数据的删除操作，将根据主键进行删除删除处理，该操作需要进行如下处理：
	 * 1、检测传入的主键集合是否为空以及是否有内容，如果没有内容直接返回false
	 * 2、调用IMemberDAO.doRemove()方法进行删除操作
	 * @param ids 要删除的数据主键集合
	 * @return 删除成功返回true，否则返回false
	 * @throws Exception 执行异常
	 */
	public boolean delete(Set<String> ids) throws Exception ;
	/**
	 * 根据主键进行用户信息的查询，调用IMemberDAO.findById()
	 * @param id 要查询的主键内容
	 * @return 以VO的形式返回，如果数据不存在则返回null
	 * @throws Exception 执行异常
	 */
	public Member get(String id) throws Exception ;
	/**
	 * 查询全部数据，调用IMemberDAO.findAll()方法
	 * @return 全部数据的集合，没有数据集合为空集合（size() == 0）
	 * @throws Exception 执行异常
	 */
	public List<Member> list() throws Exception ;
	/**
	 * 数据分页显示列表，要执行如下的操作：
	 * 1、调用IMemberDAO.findSplit()方法获取全部的数据信息；
	 * 2、调用IMemberDAO.getAllCount()方法查询所有的数据量
	 * @param currentPage 当前页
	 * @param lineSize 每页显示数据行
	 * @return 返回数据包含有两类信息：
	 * 1、key = allMembers、value = 全部数据的集合，没有数据集合为空集合（size() == 0）；
	 * 2、key = memberCount、value = 数据的统计结果
	 * @throws Exception 执行异常
	 */
	public Map<String,Object> list(long currentPage,int lineSize) throws Exception ;
	/**
	 * 进行数据的模糊查询，要执行如下操作：
	 * 1、调用IMemberDAO.findSplit()进行指定列的模糊查询；
	 * 2、调用IMemberDAO.getSplitCount()进行统计
	 * @param currentPage 当前页
	 * @param lineSize 每页显示行
	 * @param column 模糊查询列
	 * @param keyWord 关键字
	 * @return 返回数据包含有两类信息：
	 * 1、key = allMembers、value = 全部数据的集合，没有数据集合为空集合（size() == 0）；
	 * 2、key = memberCount、value = 数据的统计结果
	 * @throws Exception 执行异常
	 */
	public Map<String, Object> list(long currentPage, int lineSize, String column, String keyWord) throws Exception;
}
