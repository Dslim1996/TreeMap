package com.spring.treemap.mapper;

import com.spring.treemap.domain.MemberVO;

public interface MemberMapper {

	// 아이디 개수
	public int emailCnt(String userEmail);

	// 회원가입
	public int insertUser(MemberVO vo);

	// 회원가입 인증 추가
	public int insertAuth(MemberVO vo);

	// 로그인
	public MemberVO read(String userEmail);

	// 비밀번호변경
	public void updatePw(MemberVO vo);
	//아이디 찾기
	public String getUserEmail(MemberVO vo);

}
