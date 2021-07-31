package com.spring.treemap.controller;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.treemap.domain.MemberVO;
import com.spring.treemap.service.MemberService;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class CommonController {

	@Autowired
	MemberService service;

	// 회원가입 페이지
	@GetMapping("/member/signup")
	public void showSignin(String error, Model model) {
		log.info("회원가입 페이지");

		if (error != null) {
			log.info("error: " + "입력값을 다시 확인해주세요.");
			model.addAttribute("error", error);
		}
	}

	// 회원가입
	@PostMapping("/signUp")
	public String signUp(MemberVO member, Model model) {

		if (service.getSignUp(member)) {
			model.addAttribute("msg", member.getUserName() + "님 로그인 해주세요.");
		} else {
			log.info("회원가입오류");
		}
		return "redirect:/treeMap/map";
	}

	// 이메일 중복 체크
	@ResponseBody
	@PostMapping("/chkEmail")
	public Integer checkEmail(String userEmail) {
		log.info("이메일 중복체크");

		return service.checkEmail(userEmail);
	}

	// 로그인 페이지
	@GetMapping("/member/customLogin")
	public String showLogin(String error, String logout, Model model) {
		log.info("로그인 페이지");
		log.info("error: " + error);
		log.info("logout: " + logout);

		if (error != null) {
			log.info("error: " + "입력값을 다시 확인해주세요.");
		}
		if (logout != null) {
			model.addAttribute("logout", "로그아웃이 완료되었습니다.");
		}
		return "member/customLogin";
	}

	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("access Denied: " + auth);
		model.addAttribute("msg", "Access Denied");
	}

	@RequestMapping(value = "/member/findPw", method = RequestMethod.GET)
	public String findPwGET() throws Exception {
		return "member/findPw";
	}

	@RequestMapping(value = "/member/findPw", method = RequestMethod.POST)
	public void findPwPOST(@ModelAttribute MemberVO member, HttpServletResponse response) throws Exception {
		service.findPw(response, member);
	}
	
	@GetMapping("/member/findEmail")
	public String showFindEmail() {
		return "member/findEmail";
	}
	
	//아이디 찾기
	@ResponseBody
	@PostMapping("/member/findEmail")
	public String doFindEmail(MemberVO member, Model model) {
		log.info("아이디 찾기 시도");
		String userEmail = service.findEmail(member);
		log.info(userEmail);
		model.addAttribute("userEmail",userEmail);
		return userEmail;
	}

}
