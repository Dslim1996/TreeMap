<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="../../../../resources/css/signup.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div class="signup">
		<div class="signup-header"></div>
		<div class="signup-body">
			<h1 style="font-family: Open Sans; color: rgb(75, 75, 75)">Sign
				Up</h1>
			<h3>
				<c:out value="${error}" />
			</h3>
			<form method="post" action="/signUp" name="signupForm"
				id="signupForm">
				<div class="signupBoxWrapper">
					<div class="signupBox">
						<div id="emailSet">
							<label class="signupLabels">Email</label> <input type="button"
								class="chkEmail" onclick="emailChk();" value="이메일 중복 확인">
						</div>
						<input type="email" name="userEmail" id="userEmail" autofocus
							required="required" placeholder="이메일을 입력해주세요."
							class="signupInputs">

					</div>
					<div class="signupBox">
						<label class="signupLabels">Password</label> <input
							type="password" name="userPW" id="userPW" class="signupInputs"
							required="required" placeholder="비밀번호를 입력해주세요.">
					</div>
					<div class="signupBox">
						<label class="signupLabels">Name</label> <input type="text"
							name="userName" id="userName" class="signupInputs"
							required="required" placeholder="이름을 입력해주세요.">
					</div>
					<div class="signupBox">
						<label class="signupLabels">Phone</label> <input type="text"
							name="phoneNum" id="phoneNum" class="signupInputs"
							required="required"
							placeholder="휴대폰 번호를 -없이 입력해주세요 (ex:01012341234)">
					</div>
					<div class="signupBox">
						<label class="signupLabels">Birthday</label> <input type="text"
							name="birthday" id="birthday" class="signupInputs"
							required="required"
							placeholder="생년월일의 연월일을 -없이 입력해주세요 (ex:20010101)">
					</div>
					<div class="signupBox">
						<button class="signupBtn">확 인</button>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName }"
					value="${_csrf.token }" />
			</form>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		/* var csrfHeaderName = "${_csrf.headerName}";
		 var csrfTokenValue = "${_csrf.token}";

		 $(document).ajaxSend(function(e,xhr,option){
		 xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
		 });
		 */
		
		let userEmail = document.getElementById('userEmail');
		let userPW = document.getElementById('userPW');
		let userName = document.getElementById('userName');
		let phoneNum = document.getElementById('phoneNum');
		let birthday = document.getElementById('birthday');
		let userChk = false;

		function emailChk() {

			var regexEmail = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}$/;
			$.ajax({
				type : "POST",
				url : "/chkEmail",
				data : {
					"userEmail" : userEmail.value
				},
				//dataType : "text",
				success : function(res) {
					if (userEmail.value.match(regexEmail) != null) {
						if (res == 0) {
							alert("사용 가능한 아이디입니다.");
							userChk = true;
							console.log("이메일 체크 완료");
						} else {
							alert("다른 이메일를 사용해주세요.");
							userChk = false;
						}
					} else {
						alert("이메일 형식에 맞게 입력해주세요.");
						console.log("이메일 형식 오류");

					}
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}
			});
		}

		$(".signupBtn").on("click",
				function(e) {
					e.preventDefault();
					if(checkForm()){
					if (userChk == false) {
						alert("이메일 중복 확인해주세요.");
						return;
					} else {
						$("#signupForm").submit();
					}
					}else{
						return;
					}
				});

	function checkForm() {
			var regexpBirth = /^[0-9]{8}$/;
			var regexpPhone = /^[0-9]{10,11}$/;

	 		if (userEmail.value == '') {
				alert("이메일을 입력해주세요.");
				return false;
			}
			if (userPW.value == '') {
				alert("비밀번호를 입력해주세요.");
				userPW.focus();
				return false;
			}
			if (userName.value == '' ) {
				alert("이름을 입력해주세요.");
				userName.focus();
				return false;
			}
			if (phoneNum.value == ''|| !(regexpPhone.test(phoneNum.value))) {
				alert("휴대폰 번호를 입력값을 확인 해주세요.");
				
				phoneNum.focus();
				return false;
			}
			if (birthday.value =='' || !(regexpBirth.test(birthday.value))) {
				alert("생년월일의 연월일을 -없이 입력해주세요 (ex:20010101)");
				console.log(regexpBirth.test(birthday.value));
				birthday.focus();
				return false;
			}
			else return true;
		} 
	</script>
</body>
</html>