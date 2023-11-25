package com.crudapp.controller;

import com.crudapp.enums.PostStatus;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Post;
import com.crudapp.services.PostService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public Post getPost(Long id) throws NotFoundException, StatusDeletedException {
        return postService.getPostByID(id);
    }

    public Post createNewPost(String content, Long writerId) throws NotFoundException {
        Post post = Post.builder()
                .content(content)
                .labels(new ArrayList<>())
                .created(new Timestamp(new Date().getTime()))
                .status(PostStatus.ACTIVE)
                .writer(postService.getWriterByID(writerId))
                .build();
        return postService.createPost(post);
    }
    public Post updatePostContent(String content, Long postIdForUpdate) throws NotFoundException, StatusDeletedException {
        Post postToUpdate = Post.builder()
                .id(postIdForUpdate)
                .content(content)
                .status(PostStatus.UNDER_REVIEW)
                .updated(new Timestamp(new Date().getTime()))
                .build();
        return postService.updatePost(postToUpdate);
    }
    public void deletePost(Long id) {
        postService.deletePostByID(id);
    }

    public List<Post> getAllPosts(){
        return postService.getAllActiveAndUnderReviewPosts();
    }

    public void addLabelToPost(Long postIdForLabel, Long labelId) {
        postService.addLabelToPost(postIdForLabel, labelId);
    }
}
