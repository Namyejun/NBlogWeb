package com.ssamz.jblog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamz.jblog.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
}
