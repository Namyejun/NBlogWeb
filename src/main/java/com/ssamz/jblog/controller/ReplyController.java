package com.ssamz.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssamz.jblog.domain.Reply;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.dto.ResponseDTO;
import com.ssamz.jblog.security.UserDetailsImpl;
import com.ssamz.jblog.service.ReplyService;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;

	@PostMapping("reply/{postId}")
	public @ResponseBody ResponseDTO<?> insertReply(@PathVariable int postId, @RequestBody Reply reply, @AuthenticationPrincipal UserDetailsImpl principal) {
		replyService.insertReply(postId, reply, principal.getUser());
		return new ResponseDTO<>(HttpStatus.OK.value(), postId + "번 포스트에 댓글이 등록됐습니다.");
	}
	
	@DeleteMapping("/reply/{id}")
	public @ResponseBody ResponseDTO<?> deleteReply(@PathVariable int id) {
		replyService.deleteReply(id);
		return new ResponseDTO<>(HttpStatus.OK.value(), id + "번 댓글이 삭제됐습니다.");
	}
}
