package com.socnet.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socnet.persistence.entities.Comment;
import com.socnet.persistence.entities.Post;
import com.socnet.persistence.entities.User;

public interface CommentsRepository {
	
	Comment findById(Long id);
	Set<Comment> findByUser(User user);
	Set<Comment> findByPost(Post post);
	Comment save(Comment comment);
	
	
}