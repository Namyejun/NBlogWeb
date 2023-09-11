package com.ssamz.jblog.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssamz.jblog.domain.Post;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.dto.PostDTO;
import com.ssamz.jblog.dto.ResponseDTO;
import com.ssamz.jblog.security.UserDetailsImpl;
import com.ssamz.jblog.service.PostService;


@Controller
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/post/insertPost")
	public String insertPost() {
		return "post/insertPost";
	}
	
	@PostMapping("/post")
	public @ResponseBody ResponseDTO<?> insertPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl principal) {
		Post post = modelMapper.map(postDTO, Post.class);
		
		post.setUser(principal.getUser());
		post.setCnt(0);
		
		postService.insertPost(post);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "새로운 포스트를 등록했습니다.");
	}
	
	@GetMapping("/post/{id}")
	public String getPost(@PathVariable int id, Model model) {
		model.addAttribute("post", postService.getPost(id));
		return "post/getPost";
	}
	
	@GetMapping("/post/updatePost/{id}")
	public String updatePost(@PathVariable int id, Model model) {
		model.addAttribute("post", postService.getPost(id));
		return "post/updatePost";
	}
	
	@PutMapping("/post")
	public @ResponseBody ResponseDTO<?> updatePost(@RequestBody Post post) {
		postService.updatePost(post);
		return new ResponseDTO<>(HttpStatus.OK.value(), post.getId() + "번 포스트를 수정했습니다.");
	}
	
	@DeleteMapping("post/{id}")
	public @ResponseBody ResponseDTO<?> deletePost(@PathVariable int id) {
		postService.deletePost(id);
		return new ResponseDTO<>(HttpStatus.OK.value(), id + "번 포스트를 삭제했습니다.");
	}
	
	
	@GetMapping({"", "/"})
	public String getPostList(Model model, @PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable) {
		model.addAttribute("postList", postService.getPostList(pageable));
		// postService.getPostList(pageable).getContent(); 을 넣으면 jsp 수정 안해도 될듯
		return "index";
	}
}
