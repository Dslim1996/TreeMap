package com.spring.treemap.controller;

import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.treemap.domain.AddressVO;
import com.spring.treemap.domain.CategoryPage;
import com.spring.treemap.domain.CategoryVO;
import com.spring.treemap.domain.MapVO;
import com.spring.treemap.domain.Page;
import com.spring.treemap.service.MapService;

@Controller
@RequestMapping("/treeMap/*")
public class MapContoller {

	@Autowired
	MapService service;

	// 처음 들어올시
	@RequestMapping(value = "/map", method= {RequestMethod.GET, RequestMethod.POST})
	public String openMap(Model model, @RequestParam(value="num", required = false, defaultValue = "1") int num,
			@RequestParam(value = "catNum", required = false, defaultValue = "1") int catNum,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,String userEmail) {
		System.out.println("접근");
		model.addAttribute("userNo",0);
		/*
		int userNo = 1;
		Page page = new Page();
		page.setNum(num);

		page.setCount(service.getAddressCount(keyword));
		page.setSearchType(searchType);
		page.setKeyword(keyword);
		
		CategoryPage categoryPage = new CategoryPage();
		categoryPage.setNum(catNum);
		List<CategoryVO> category = service.getMapBoardCateNameList(userNo, categoryPage.getStartNum(),categoryPage.getEndNum());
	
		
		// 현재 페이지에 해당하는 리스트
		model.addAttribute("mapBoardList",
				service.getMapBoardList(userNo, page.getDisplayPost(), page.getPostNum(), searchType, keyword));
		// 카테고리 이름 표시
		model.addAttribute("categoryName", category);
		// 페이징처리
		model.addAttribute("page", page);
		// 현재 페이지가 몇인지 표시
		model.addAttribute("select", num);
		// 현재키워드가 뭔지 알려줌 페이지 이동할떄 알고있어야함
		model.addAttribute("keyword", keyword);
		// 카테고리 페이지
		model.addAttribute("categoryPage", categoryPage);
		// 카테고리가 몇개인지
		model.addAttribute("categorylength", service.getMapBoardCateNameList(userNo, 0, 0).size());
		*/
		return "treeMap/map";
	}
	@GetMapping("/userMapBoard")
	public String getUserMapBoard(Model model, @RequestParam(value="num", required = false, defaultValue = "1") int num,
			@RequestParam(value = "catNum", required = false, defaultValue = "1") int catNum,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam("userEmail") String userEmail) {
		
		int userNo = service.getUserNo(userEmail);
		System.out.println(userNo);
		Page page = new Page();
		page.setNum(num);

		page.setCount(service.getAddressCount(userNo,keyword));
		
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		CategoryPage categoryPage = new CategoryPage();
		categoryPage.setNum(catNum);
		List<CategoryVO> category = service.getMapBoardCateNameList(userNo, categoryPage.getStartNum(),categoryPage.getEndNum());

		// 현재 페이지에 해당하는 리스트
		model.addAttribute("mapBoardList",service.getMapBoardList(userNo, page.getDisplayPost(), page.getPostNum(), searchType, keyword));
		// 카테고리 이름 표시
		model.addAttribute("categoryName", category);
		// 페이징처리
		model.addAttribute("page", page);
		// 현재 페이지가 몇인지 표시
		model.addAttribute("select", num);
		// 현재키워드가 뭔지 알려줌 페이지 이동할떄 알고있어야함
		model.addAttribute("keyword", keyword);
		// 카테고리 페이지
		model.addAttribute("categoryPage", categoryPage);
		// 카테고리가 몇개인지
		model.addAttribute("categorylength", service.getMapBoardCateNameList(userNo, 0, 0).size());
		
		model.addAttribute("userNo",userNo);

		return "treeMap/map";
	}
	// 이벤트 발생시 다시 받음
	@GetMapping("/reloadBoard")
	public String getMapBoard(Model model, @RequestParam(value="num", required = false, defaultValue = "1") int num,
			@RequestParam(value = "catNum", required = false, defaultValue = "1") int catNum,
			@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam("userNo") int userNo) {

		Page page = new Page();
		page.setNum(num);

		if (searchType.equals("catName")) {
			page.setCount(service.getCategoryCount(userNo,keyword));
		} else {
			page.setCount(service.getAddressCount(userNo,keyword));
		}
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		CategoryPage categoryPage = new CategoryPage();
		categoryPage.setNum(catNum);
		List<CategoryVO> category = service.getMapBoardCateNameList(userNo, categoryPage.getStartNum(),categoryPage.getEndNum());

		// 현재 페이지에 해당하는 리스트
		model.addAttribute("mapBoardList",service.getMapBoardList(userNo, page.getDisplayPost(), page.getPostNum(), searchType, keyword));
		// 카테고리 이름 표시
		model.addAttribute("categoryName", category);
		// 페이징처리
		model.addAttribute("page", page);
		// 현재 페이지가 몇인지 표시
		model.addAttribute("select", num);
		// 현재키워드가 뭔지 알려줌 페이지 이동할떄 알고있어야함
		model.addAttribute("keyword", keyword);
		// 카테고리 페이지
		model.addAttribute("categoryPage", categoryPage);
		// 카테고리가 몇개인지
		model.addAttribute("categorylength", service.getMapBoardCateNameList(userNo, 0, 0).size());

		return "include/mapboard";
	}

	// 즐겨찾기 등록
	@ResponseBody
	@PostMapping("/favorites")
	public void insertMap(AddressVO address, CategoryVO category) {
		// 사용자 입력값 카테고리테이블 인서트
		service.insertCategory(category);
		// 사용자 입력값 주소테이블 인서트
		service.insertAddress(address);
	}

	// 상세보기
	@GetMapping("/mapBoardDetail")
	public String getMapBoardDetail(int adrNo, int catNo, Model model) {
		MapVO mapBoardDetail = service.getMapBoardDetail(adrNo, catNo);
		AddressVO address = mapBoardDetail.getAddress();
		CategoryVO category = mapBoardDetail.getCategory();

		// detail이 true면 include가 바뀜
		address.setDetail(true);
		model.addAttribute("address", address);
		model.addAttribute("category", category);

		return "include/mapboard";
	}

	// 수정
	@ResponseBody
	@PostMapping("/modifyMapBoard")
	public String modifyMapBoard(AddressVO address, CategoryVO category) {
		// 사용자 입력값 카테고리테이블 업데이트
		service.updateCategory(category);
		// 사용자 입력값 주소테이블 업데이트
		service.updateAddress(address);

		return "include/mapboard";
	}

	// 삭제
	@PostMapping("/deleteMapBoard")
	public String deleteMapBoard(int adrNo, int catNo) {
		// 해당 값을 가진 주소 테이블 삭제
		service.deleteAddress(adrNo);
		// 해당 값을 가진 카테고리 테이블 컬럼 삭제
		service.deleteCateGory(catNo);

		return "include/mapboard";
	}

}
