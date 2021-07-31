package com.spring.treemap.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.treemap.domain.MemberVO;
import com.spring.treemap.mapper.MemberMapper;
import com.spring.treemap.security.domain.CustomUser;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {

	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;

	//비밀번호 암호화를 위한 Encoder
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	//이메일 중복체크
	public int checkEmail(String userEmail) {
		int cnt = mapper.emailCnt(userEmail);
		System.out.println(cnt);
		return cnt;
	}

	//회원가입
	public boolean getSignUp(MemberVO member) {
		member.setUserPW(bcryptPasswordEncoder.encode(member.getUserPW()));
		return mapper.insertUser(member) == 1 && mapper.insertAuth(member) == 1;
	}
	
	//아이디 찾기
	public String findEmail(MemberVO member) {
		return mapper.getUserEmail(member);
	}

	@Override
	public void findPw(HttpServletResponse response, MemberVO vo) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		MemberVO ck = mapper.read(vo.getUserEmail());
		PrintWriter out = response.getWriter();
		// 가입된 아이디가 없으면
		if (mapper.emailCnt(vo.getUserEmail()) == 0) {
			out.print("등록되지 않은 아이디입니다.");
			out.close();
		}
		// 가입된 이메일이 아니면
		else if (!vo.getUserEmail().equals(ck.getUserEmail())) {
			out.print("등록되지 않은 이메일입니다.");
			out.close();
		} else {
			// 임시 비밀번호 생성
			StringBuffer pw = new StringBuffer();
			for (int i = 0; i < 12; i++) {
				pw.append((char) ((Math.random() * 26) + 97));
			}
			vo.setUserPW(pw.toString());

			try {
				// 비밀번호 변경 메일 발송
				if (vo.getUserEmail().contains("naver.com")) {
					EmailSender.naverMailSend(vo);
				} else {
					EmailSender.gmailSend(vo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 비밀번호 변경
				vo.setUserPW(bcryptPasswordEncoder.encode(pw));
				mapper.updatePw(vo);
			}
			out.print("이메일로 임시 비밀번호를 발송하였습니다.");
			out.close();
		}

	}

}
