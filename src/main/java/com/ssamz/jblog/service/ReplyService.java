package com.ssamz.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssamz.jblog.domain.Post;
import com.ssamz.jblog.domain.Reply;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.persistence.PostRepository;
import com.ssamz.jblog.persistence.ReplyRepository;

@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public void insertReply(int postId, Reply reply, User user) {
		Post post = postRepository.findById(postId).get();
		reply.setUser(user);
		reply.setPost(post);
		
		replyRepository.save(reply);
	}
	
	@Transactional
	public void deleteReply(int id) {
		replyRepository.deleteById(id);
	}
}
