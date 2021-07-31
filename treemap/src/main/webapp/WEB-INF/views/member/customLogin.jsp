<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="../../../../resources/css/customLogin.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div class="customLogin">
		<div class="customLogin-header"></div>
		<div class="customLogin-body">
			<h1 style="font-family:Open Sans; color:rgb(75,75,75)">Sign In</h1>
			<h2>
				<c:out value="${msg}" />
				${msg}
			</h2>
			<h2>
				<c:out value="${logout}" />
			</h2>
			<h3>
				<c:out value="${loginFailMsg}" />
			</h3>

			<form method="post" action="/login" id="loginForm">
				
					<div class="loginBoxWrapper">
						<div class="loginBox">
							<label class="loginLabels">Email</label> <input
								class="loginInputs" type="email" name="userEmail" id="userEmail"
								placeholder="이메일을 입력해주세요." autofocus>
						</div>
						<div class="loginBox">
							<label class="loginLabels">Password</label> <input
								class="loginInputs" type="password" name="userPW" id="userPW"
								placeholder="비밀번호를 입력해주세요.">
							<div>
								<input type="checkbox" name="remember-me">Remember Me
							</div>
							<a href="/member/findPw">비밀번호 찾기</a>
							<a href="/member/findEmail">아이디 찾기</a>
						</div>
						<div class="loginBox">
							<button class="loginsubmit">확 인</button>
						</div>
					</div>
			
				<input type="hidden" name="${_csrf.parameterName }"
					value="${_csrf.token }" />
			</form>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
	let userEmail = document.getElementById('userEmail');
	let userPW = document.getElementById('userPW');
	
		$(".loginsubmit").on("click", function(e) {
			e.preventDefault();
			if(checkForm()) $("#loginForm").submit();
		});
		
		function checkForm() {
			var regexEmail = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$/;
	 		if (userEmail.value == ''|| !(regexEmail.test(userEmail.value))) {
				alert("이메일을 입력해주세요.");
				userEmail.focus();
				return false;
			}
			if (userPW.value == '') {
				alert("비밀번호를 입력해주세요.");
				userPW.focus();
				return false;
			}
			else return true;
		} 
	</script>
</body>
</html>