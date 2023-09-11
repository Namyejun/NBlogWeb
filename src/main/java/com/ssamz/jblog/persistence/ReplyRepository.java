package com.ssamz.jblog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssamz.jblog.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
