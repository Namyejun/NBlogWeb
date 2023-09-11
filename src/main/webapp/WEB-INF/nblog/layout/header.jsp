<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!-- 로그인 인증에 성공한 브라우저만 접근할 수 있는 영역 -->
<sec:authorize access="isAuthenticated()">
	<!-- principal은 로그인 성공한 사용자 객체에 접근할 수 있는 변수 -->
	<sec:authentication var="principal" property="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/webjars/bootstrap/5.3.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="/webjars/bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
<script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>

<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- summernote 시작 -->
<link href="/webjars/summernote/0.8.20/dist/summernote.min.css" rel="stylesheet">
<script src="/webjars/summernote/0.8.20/dist/summernote.min.js"></script>
<!-- summernote 종료 -->

</head>
<body>

	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">Main</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#mynavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="mynavbar">
				<c:if test="${principal == null }">
					<ul class="navbar-nav me-auto">
						<li class="nav-item"><a class="nav-link" href="/auth/login">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/insertUser">회원가입</a></li>
					</ul>
				</c:if>
				<c:if test="${principal != null }">
					<ul class="navbar-nav me-auto">
						<li class="nav-item"><a class="nav-link" href="/user/updateUser">회원 상세</a></li>
						<li class="nav-item"><a class="nav-link" href="/post/insertPost">포스트 등록</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/logout">로그아웃</a></li>
					</ul>
				</c:if>
			</div>
		</div>
	</nav>