<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
<h1>비밀번호 찾기</h1>
<p>
<label>이름</label>
<input type="text" id="findName" placeholder="이름을 입력하세요">s
</p>
<p>
<label>이메일</label>
<input type="text" id="findEmail" placeholder="이메일을 입력하세요">
</p>
<button type="button" id="findBtn" onclick="findPw()" >찾기</button>
</body>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	let userName = document.querySelector("#findName");
	let userEmail = document.querySelector("#findEmail");
	function findPw(){
		$.ajax({
			url:'/member/findPw',
			type:'POST',
			data:{
				'userName':userName.value,
				'userEmail':userEmail.value	
			},
			success : function(result) {
				alert(result);
			}
		})
	}
</script>
</html>