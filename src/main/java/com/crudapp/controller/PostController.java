package com.crudapp.controller;

import com.crudapp.enums.PostStatus;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.repository.PostRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class PostController {
    PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPost(Long id) throws NotFoundException, StatusDeletedException {
        Post post = postRepository.findById(id);
        if(post == null) throw new NotFoundException("Post not exist");
        if(post.getStatus() == PostStatus.DELETED) throw new StatusDeletedException("Post is deleted");
        return post;
    }

    public Post createPost(String content, Long writerId) {
        Post post = Post.builder()
                .content(content)
                .writerId(writerId)
                .status(PostStatus.ACTIVE)
                .created(new Timestamp(new java.util.Date().getTime()))
                .build();
        return postRepository.save(post);
    }
    public Post updatePost(String content, Long postId) throws NotFoundException, StatusDeletedException {
        Post postToUpdate = getPost(postId);
        postToUpdate.setContent(content);
        postToUpdate = postRepository.update(postToUpdate);
        return postToUpdate;
    }
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    public List<Post> getAllPosts(){
        return postRepository.getAll();
    }
}
