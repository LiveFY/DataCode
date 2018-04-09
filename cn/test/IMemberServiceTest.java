package cn.test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cn.service.IMemberService;
import cn.util.factory.Factory;
import cn.vo.Member;
import junit.framework.TestCase;

class IMemberServiceTest {
	private static final String MEMBER_KEY = "member.service" ;
	private Random random = new Random() ;
	@Test
	void testAdd() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Member mem = new Member() ;
		mem.setMid("Hello - " + random.nextInt(Integer.MAX_VALUE));
		mem.setName("小同学");
		mem.setAge(89);
		mem.setBirthday(new Date());
		mem.setNote("简短的描述");
		mem.setSex("女");
		mem.setSalary(1.1);
		boolean flag = memberService.add(mem) ;
		TestCase.assertTrue(flag);
	}

	@Test
	void testEdit() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Member mem = new Member() ;
		mem.setMid("Hello");
		mem.setName("同学");
		mem.setAge(19);
		mem.setBirthday(new Date());
		mem.setNote("添加描述");
		mem.setSex("女");
		mem.setSalary(1.1);
		boolean flag = memberService.edit(mem) ;
		TestCase.assertTrue(flag);
	}

	@Test
	void testDelete() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Set<String> ids = new HashSet<String>() ;
		ids.add("Hello") ;
		boolean flag = memberService.delete(ids) ;
		TestCase.assertTrue(flag);
	}

	@Test
	void testGet() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Member mem = memberService.get("Hello") ;
		System.out.println(mem.getName() + "、" + mem.getNote());
		TestCase.assertNotNull(mem);
	}

	@Test
	void testList() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		List<Member> all = memberService.list();
		System.out.println(all);
		TestCase.assertTrue(all.size() > 0);
	}

	@Test
	void testListLongInt() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Map<String,Object> map = memberService.list(2, 3) ;
		System.out.println(map);
	}

	@Test
	void testListLongIntStringString() throws Exception {
		IMemberService memberService = Factory.getServiceInstance(MEMBER_KEY,IMemberService.class) ;
		Map<String, Object> map = memberService.list(1, 3, "mid", "534787109");
		System.out.println(map); 
	}

}
