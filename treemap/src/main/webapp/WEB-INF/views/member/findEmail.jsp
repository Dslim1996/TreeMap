<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
	<h1>아이디 찾기</h1>
	<p>
		<label>Name</label> <input type="text" id="findName"
			placeholder="이름을 입력하세요" onkeyup="enterkey();">
	</p>
	<p>
		<label>Birthday</label> <input type="text" id="findBirthday"
			placeholder="생년월일을 입력하세요" onkeyup="enterkey();">
	</p>
	<p>
		<label>Phone</label> <input type="text" id="findPhoneNum"
			placeholder="휴대폰 번호를 입력하세요" onkeyup="enterkey();">
	</p>
	<button type="button" id="findBtn" onclick="findEmail()">찾기</button>
	<button type="button" id="goLogin" onclick="reloadLogin()">로그인하러 가기</button>
</body>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	let userName = document.querySelector("#findName");
	let birthday = document.querySelector("#findBirthday");
	let phoneNum = document.querySelector("#findPhoneNum");
  
	function findEmail() {
		if (checkForm()) {
			$.ajax({
				url : '/member/findEmail',
				type : 'POST',
				data : {
					'userName' : userName.value,
					'birthday' : birthday.value,
					'phoneNum' : phoneNum.value
				},
				success : function(result) {
					console.log(result);
					if (result !== '') {
						alert(result);
					} else{
						alert("가입 정보가 없습니다.");
					}
				}
			});
		}
	}

	function checkForm() {
		var regexpBirth = /^[0-9]{8}$/;
		var regexpPhone = /^[0-9]{10,11}$/;

		if (userName.value == '') {
			alert("이름을 입력해주세요.");
			userName.focus();
			return false;
		}

		if (birthday.value == '' || !(regexpBirth.test(birthday.value))) {
			alert("생년월일의 연월일을 -없이 입력해주세요 (ex:20010101)");
			console.log(regexpBirth.test(birthday.value));
			birthday.focus();
			return false;
		}
		if (phoneNum.value == '' || !(regexpPhone.test(phoneNum.value))) {
			alert("휴대폰 번호를 입력값을 확인 해주세요.");

			phoneNum.focus();
			return false;
		} else
			return true;
	}
	
	function enterkey() {
        if (window.event.keyCode == 13) {
             // 엔터키가 눌렸을 때 실행할 내용
             findEmail();
        }
   }
	
	function reloadLogin(){
	      console.log("login 페이지로 이동");
	      $.ajax({
	         url:'/member/customLogin',
	         type:'GET',
	         success : function(result) {
	            $('#include').html(result);
	         }
	      });
	   }
</script>
</html>