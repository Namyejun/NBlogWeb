// userObject 객체 생성
let userObject = {
	// init() 함수 선언
	init: function() {
		let _this = this;
		
		// #btn-save 버튼에 클릭 이벤트가 발생하면 insertUser() 함수 호출
		$("#btn-save").on("click", () => {
			_this.insertUser();
		});
		// #btn-update 버튼에 클릭 이벤트가 발생하면 insertUser() 함수 호출
		$("#btn-update").on("click", () => {
			_this.updateUser();
		});
	},
	
	insertUser: function() {
		alert("회원가입 요청됨");
		// 사용자가 입력한 값 추출
		let user = {
			username : $("#username").val(),
			password : $("#password").val(),
			email : $("#email").val()
		}
		
		// Ajax를 이용한 비동기 호출
		// done() 함수 : 요청 처리에 성공했을 때 실행될 코드
		// fail() 함수 : 요청 처리에 실패했을 때 실행될 코드
		$.ajax({
			type: "POST", // 요청 방식
			url: "/auth/insertUser", // 요청 경로
			data: JSON.stringify(user), // user 객체를 JSON 형식으로 변환
			// HTTP의 Body에 설정되는 데이터 마임 타입
			contentType: "application/json; charset=utf-8"
			// 응답으로 들어온 JSON 데이터를 response로 받는다.
		}).done(function(response) {
			let status = response["status"];
			if(status == 200) {
				let message = response["data"];
				alert(message);
				location="/"
			} else {
				let warn = "";
				let errors = response["data"];
				if (errors.username != null) warn = warn + errors.username + "\n";
				if (errors.password != null) warn = warn + errors.password + "\n";
				if (errors.email != null) warn = warn + errors.email;
				alert(warn);
			}
		}).fail(function(error) {
			alert("에러 발생 : " + error);
		})
	},
	
	updateUser: function() {
		alert("회원 수정 요청됨");
		// 사용자가 입력한 값 추출
		let user = {
			id: $("#id").val(),
			username : $("#username").val(),
			password : $("#password").val(),
			email : $("#email").val()
		}
		
		console.log(user.id);
		console.log(user.username);
		console.log(user.password);
		console.log(user.email);
		
		
		// Ajax를 이용한 비동기 호출
		// done() 함수 : 요청 처리에 성공했을 때 실행될 코드
		// fail() 함수 : 요청 처리에 실패했을 때 실행될 코드
		$.ajax({
			type: "PUT", // 요청 방식
			url: "/user", // 요청 경로
			data: JSON.stringify(user), // user 객체를 JSON 형식으로 변환
			// HTTP의 Body에 설정되는 데이터 마임 타입
			contentType: "application/json; charset=utf-8"
			// 응답으로 들어온 JSON 데이터를 response로 받는다.
		}).done(function(response) {
			let status = response["status"];
			if(status == 200) {
				let message = response["data"];
				alert(message);
				location="/"
			} else {
				let warn = "";
				let errors = response["data"];
				if (errors.username != null) warn = warn + errors.username + "\n";
				if (errors.password != null) warn = warn + errors.password + "\n";
				if (errors.email != null) warn = warn + errors.email;
				alert(warn);
			}
		}).fail(function(error) {
			alert("에러 발생 : " + error);
		})
	}
}

// userObject 객체의 init() 함수 호출
userObject.init();