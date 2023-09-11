<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

	<br><br>
	<div class="container border">
		<br>
		<div class="mb-3 mt-3">
			<h3>${post.title }</h3>
		</div>
		<br>
		<div class="mb-3 mt-3">
			<div>${post.content }</div>
		</div>
		
		<br>
		<div class="mb-3 mt-3">
			포스트 번호: <span id="id"><i>${post.id }</i></span><br>
			작성자: <span><i>${post.user.username }</i></span><br>
		</div>
		
		<hr>
		<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
		<c:if test="${principal.username == post.user.username }">
			<a href="/post/updatePost/${post.id }" class="btn btn-warning">수정하기</a>
			<button id="btn-delete" class="btn btn-danger">삭제하기</button>
		</c:if>
		<br><br>
	
		<c:if test="${!empty post.replyList }">
			<div class="container mt-3">
				<table class="table">
					<thead>
						<tr>
							<th width="80%">내용</th>
							<th width="10%">작성자</th>
							<th width="10%">삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="reply" items="${post.replyList }">
							<tr>
								<td>${reply.content }</td>
								<td>${reply.user.username }</td>
								<c:if test="${reply.user.id != null && reply.user.id == principal.user.id }">
									<td><button class="btn btn-secondary btn-sm" onclick="replyObject.deleteReply(${post.id}, ${reply.id })">삭제</button></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	
		<div class="container mt-3">
			<input type="hidden" id="postId" value="${post.id }">
			<table class="table">
				<thead>
					<tr>
						<th><h4>댓글 등록</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr align="right">
						<td>
							<textarea id="reply-content" rows="1" class="form-control"></textarea>
							<button id="btn-save-reply" class="btn btn-secondary">댓글 등록</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<script src="/js/post.js"></script>
	<script src="/js/reply.js"></script>
	
<%@ include file="../layout/footer.jsp"%>