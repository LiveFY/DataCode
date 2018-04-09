package cn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.dao.IMemberDAO;
import cn.service.IMemberService;
import cn.util.factory.Factory;
import cn.vo.Member;

/**
 * 每一个业务层的子类对象都单独管理一个自己的数据库连接
 * 
 */
public class MemberServiceImpl implements IMemberService {
	private static final String MEMBER_KEY = "member.dao" ;
	@Override
	public boolean add(Member vo) throws Exception {
		boolean flag = false;
		if (vo.getAge() < 0 || vo.getAge() > 300) {
			return false; // 不能够保存
		}
		if (!("男".equals(vo.getSex()) || "女".equals(vo.getSex()))) {
			return false;
		}
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		if (memberDAO.findById(vo.getMid()) == null) { // 根据ID查询，如果返回结果为null
			flag = memberDAO.doCreate(vo); // 直接进行保存
		}
		return flag;
	}

	@Override
	public boolean edit(Member vo) throws Exception {
		boolean flag = false;
		if (vo.getAge() < 0 || vo.getAge() > 300) {
			return false; // 不能够保存
		}
		if (!("男".equals(vo.getSex()) || "女".equals(vo.getSex()))) {
			return false;
		}
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		flag = memberDAO.doEdit(vo);
		return flag;
	}

	@Override
	public boolean delete(Set<String> ids) throws Exception {
		boolean flag = false;
		if (ids == null || ids.size() == 0) { // 没有数据要删除
			return false;
		}
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		flag = memberDAO.doRemove(ids);
		return flag;
	}

	@Override
	public Member get(String id) throws Exception {
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		return memberDAO.findById(id);
	}

	@Override
	public List<Member> list() throws Exception {
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		return memberDAO.findAll();
	}

	@Override
	public Map<String, Object> list(long currentPage, int lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		map.put("allMembers", memberDAO.findSplit(currentPage, lineSize));
		map.put("memberCount", memberDAO.getAllCount());
		return map;
	}

	@Override
	public Map<String, Object> list(long currentPage, int lineSize, String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		IMemberDAO memberDAO = Factory.getDAOInstance(MEMBER_KEY,IMemberDAO.class); // 获取DAO接口对象
		if (column == null || "".equals(column) || keyWord == null || "".equals(keyWord)) {
			map.put("allMembers", memberDAO.findSplit(currentPage, lineSize));
			map.put("memberCount", memberDAO.getAllCount());
		} else {
			map.put("allMembers", memberDAO.findSplit(currentPage, lineSize, column, keyWord));
			map.put("memberCount", memberDAO.getAllCount(column, keyWord));
		}
		return map;
	}

}
