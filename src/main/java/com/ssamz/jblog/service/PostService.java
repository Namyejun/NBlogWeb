package com.ssamz.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamz.jblog.domain.Post;
import com.ssamz.jblog.persistence.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public void insertPost(Post post) {
		postRepository.save(post);
	}
	
	@Transactional(readOnly = true)
	public Page<Post> getPostList(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Post getPost(int id) {
		return postRepository.findById(id).get();
	}
	
	@Transactional
	public void updatePost(Post post) {
		Post findPost = postRepository.findById(post.getId()).get();
		findPost.setTitle(post.getTitle());
		findPost.setContent(post.getContent());
	}
	
	@Transactional
	public void deletePost(int id) {
		postRepository.deleteById(id);
	}
}
