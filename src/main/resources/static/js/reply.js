let replyObject = {
	init: function(){
		$("#btn-save-reply").on("click", () => {
			this.insertReply();
		});
	},
	
	insertReply: function() {
		alert("댓글 등록 요청됨");
		
		let id = $("#postId").val();
		
		let reply = {
			content: $("#reply-content").val()
		}
		
		$.ajax({
			type: "POST",
			url: "/reply/" + id,
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8"
		}).done(function(response) {
			let message = response["data"];
			alert(message);
			location = "/post/" + id;
		}).fail(function(error) {
			let message = error["data"]
			alert("에러 발생 : " + message);
		});
	},
	
	deleteReply: function(postId, replyId) {
		alert("댓글 삭제 요청됨");
		
		$.ajax({
			type: "DELETE",
			url: "/reply/" + replyId
		}).done(function(response) {
			let message = response["data"];
			alert(message);
			location = "/post/" + postId
		}).fail(function(error) {
			let message = error["data"]
			alert("에러 발생 : " + message);
		});
	}
}

replyObject.init();