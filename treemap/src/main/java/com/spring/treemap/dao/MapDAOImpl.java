package com.spring.treemap.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.treemap.domain.AddressVO;

@Repository
public class MapDAOImpl implements MapDAO {

	@Autowired
	private SqlSession sql;

	// mybatis에게 namespace가 여기인것을 알려줌
	private static String namespace = "com.spring.treemap";

	
	@Override
	public List<AddressVO> getMapBoardList(int userNo) {
		return sql.selectList(namespace+".getMapBoardList",userNo);
	}


	@Override
	public void insertCategory(AddressVO vo) {
		sql.insert(namespace+".insertCategory",vo);
	}


	@Override
	public void insertAddress(AddressVO vo) {
		sql.insert(namespace+".insertAddress",vo);
	}
}
