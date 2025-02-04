<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<link href="../../../../resources/css/mapboardNav.css" rel="stylesheet" type="text/css">
<html>
<meta charset="UTF-8">
<body>
	<div id="boardNav" class="boardNav">
		<div class="category">
		<div onclick="reloadMapList();" style="margin-left:30px; width: 70px;height: 100%;cursor:pointer; display:flex;justify-content: center; align-items: center;">
			<span style="font-size:13px; color:white;">전체보기</span>
		</div>
			<ul>
			
				<c:if test="${categoryPage.startNum>3}">
						<li class="catName" style="color:white;" onclick="reloadMapListKeyword(1,${categoryPage.num-1},'${page.searchType}','${page.keyword}')"><</li>
				</c:if>
 				<c:forEach items="${categoryName}" var="list" varStatus="status">
 				
 						<c:if test="${keyword != list.catName}">
 							<li class="catName" onclick="reloadMapListKeyword(1,${categoryPage.num},'catName','${list.catName}')">${list.catName}</li>
						</c:if>
						<c:if test="${keyword == list.catName}">
							<li class="catName" style="color:white;">${list.catName}</li>
						</c:if>
				</c:forEach>
				
				<c:if test="${categorylength > categoryPage.startNum+2}">
					 <li class="catName" style="color:white;" onclick="reloadMapListKeyword(1,${categoryPage.num+1},'${page.searchType}','${page.keyword}')">></li>
				</c:if>
			</ul>
		</div>
		<div id="mapboardListBox" class="mapboardListBox">
			<div class="mapboardListSearch">
				<input class="boardSearch" placeholder="내 지도 검색" value="${keyword}"/>
				<button class="SearchBtn" onclick="reloadMapListKeyword(1,1,'adrName','')"><img class="searchImg" src="../../../../resources/imgs/searchgray.png"></button>
			</div>
			<c:choose>
				<c:when test="${address.detail}">
					<div class="boardDetailwapper">
						<div class="boardDetail">
							<div class="detailTitle">
								<h3 style="margin-left:10px;">${address.adrName}</h3>
							</div>
							<div class="detailaddress">
								<div class="detailLabel">주소</div>
									<div style="font-size: 12px; margin-top:10px;">
										<p style="margin-bottom:5px;">지번 : ${address.address}</p>
										<c:if test="${address.rowaddress!=null }">
											<p>도로명 : ${address.rowaddress}</p>
										</c:if>
										
								</div>
							</div>

							<div class="date">
								<div class="detailLabel">작성일</div>
								<div style="font-size: 12px; margin-top:10px;">${address.createdAt}</div>
							</div>
							<div class="memoBox">
								<div class="detailLabel">메모일</div>
								<textarea class="memo" disabled>${address.memo} </textarea>
							</div>
							<div class="MapChange">
								<button type="button" class="modifyBtn" onclick="modifyModel()">수정</button>
								<button type="button" class="modifyBtn" onclick="deleteMapboard(${address.adrNo},${category.catNo})">삭제</button>
							</div>
						</div>
						<div style="margin-top:20px;">
							<button type="button" class="modifyBtn" onclick="reloadMapCategoryList()">돌아가기</button>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items="${mapBoardList}" var="list" varStatus="status">
						<div class="boardList">
							<button class="addrName" onclick="addrmarker(${list.address.lat},${list.address.lng},'${list.address.rowaddress}','${list.address.address}','${list.address.adrName}',${list.address.adrNo},${list.address.catNo},'${list.category.iconUrl}');">
								<span>${list.address.adrName}</span>
							</button>
							<p class="address">${list.address.address}</p>
						</div> 
					</c:forEach>
					<div class="pageBox">
					 <c:if test="${page.prev}">
						 <button class ="pageNumBtn" onclick="reloadMapListKeyword(${page.startPageNum - 1},${categoryPage.num},'${page.searchType}','${page.keyword}')"><</button>
					</c:if>
						
					<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
						 <span>
						  
						  <c:if test="${select != num}">
						   <button class="pageNumBtn" onclick="reloadMapListKeyword(${num},${categoryPage.num},'${page.searchType}','${page.keyword}')">${num}</button>
						  </c:if>    
						  
						  <c:if test="${select == num}">
						    <button class="pageNumBtn" style="background-color:rgb(0,50,150); color:white;">${num}</button>
						  	<c:set var="number" value='${num}'></c:set>
						  </c:if>
						    
						 </span>
						</c:forEach>
						
						<c:if test="${page.next}">
						 <button class ="pageNumBtn" onclick="reloadMapListKeyword(${page.endPageNum + 1},${categoryPage.num},'${page.searchType}','${page.keyword}')">></button> 
						</c:if>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		
	</div>
		<div id="modifyModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content">
			<div class="modalTitle">
				<h3 style="color: darkslategray">즐겨찾기 등록</h3>
			</div>
			<div class="modalBox">
				<label class="modalLabel">카테고리</label>
				<input class="modalInput" id="Mcategory" type="text" value="${category.catName}" />
			</div>
			<div class="modalBox">
				<label class="modalLabel">별칭</label><input class="modalInput" id="Maddressname" type="text" value="${address.adrName}" />
			</div>
			<div class="modalBox">
				<label class="modalLabel">메모</label><textarea class="modalmemo" id="Mmemo">${address.memo}</textarea>
			</div>
			<div class="modalBox">
				<label class="modalLabel">마커 선택</label> 
				<div class="markerBtnDiv">
					<div class="markerBtnDiv2">
						<button class="markerBtn" id="Mdefault" onclick="markerSelect('Mdefault','../../../../resources/imgs/default.png')"> <img style="width: 50px; "src="../../../../resources/imgs/default.png"> </button>
						<button class="markerBtn" id="Mfood" onclick="markerSelect('Mfood','../../../../resources/imgs/food.png')"> <img style="width: 50px;" src="../../../../resources/imgs/food.png"> </button>
						<button class="markerBtn" id="Mbank" onclick="markerSelect('Mbank','../../../../resources/imgs/bank.png')"> <img style="width: 50px;" src="../../../../resources/imgs/bank.png"> </button>
					</div>
					<div class="markerBtnDiv2">
						<button class="markerBtn" id="Mmart" onclick="markerSelect('Mmart','../../../../resources/imgs/mart.png')"> <img style="width: 50px;" src="../../../../resources/imgs/mart.png"> </button>
						<button class="markerBtn" id="Mhome"onclick="markerSelect('Mhome','../../../../resources/imgs/home.png')"> <img style="width: 50px;"src="../../../../resources/imgs/home.png"> </button>
						<button class="markerBtn" id="Mhospital" onclick="markerSelect('Mhospital','../../../../resources/imgs/hospital.png')"> <img style="width: 50px;" src="../../../../resources/imgs/hospital.png"> </button>
					</div>
				</div>
			</div>
			<p>
				<br />
			</p>
			<div
				style="width: 100%; display: flex; align-items: center; justify-content: center;">
				<div style="width: 40%">
					<button class="fBtn" onClick="modifyFavorites(${address.adrNo},${category.catNo});">
						<span class="pop_bt" style="font-size: 10pt;"> 등록 </span>
					</button>
				</div>
				<div>
					<button class="fBtn" onClick="closeModal();">
						<span class="pop_bt" style="font-size: 10pt;"> 취소 </span>
					</button>
				</div>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">



</script>
</html>