package com.crudapp.services;

import com.crudapp.enums.PostStatus;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.PostRepository;
import com.crudapp.repository.jdbc.JdbcPostRepositoryImpl;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService() {
        this.postRepository = new JdbcPostRepositoryImpl();
    }



    public Post getPostByID(Long id) throws NotFoundException, StatusDeletedException  {
        Post post = postRepository.findById(id);
        if(post == null) throw new NotFoundException("Post not exist");
        if(post.getStatus().equals(PostStatus.DELETED)) throw new StatusDeletedException("Post is deleted");
        return post;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) throws NotFoundException, StatusDeletedException {
        Post postToUpdate = postRepository.update(post);
        if(postToUpdate == null) throw new NotFoundException("Post not exist");
        if(post.getStatus().equals(PostStatus.DELETED)) throw new StatusDeletedException("Post is deleted");
        return postRepository.update(postToUpdate);
    }

    public void deletePostByID(Long id) {
       postRepository.deleteById(id);
    }
    public List<Post> getAllActiveAndUnderReviewPosts() {
        return postRepository.getAll()
                .stream()
                .filter(post -> (post.getStatus().equals(PostStatus.ACTIVE) || post.getStatus().equals(PostStatus.UNDER_REVIEW)))
                .toList();
    }
    public Writer getWriterByID(Long id) {
        return postRepository.getWriter(id);
    }

    public void addLabelToPost(Long postIdForLabel, Long labelId) {
        postRepository.addLabelToPost(postIdForLabel, labelId);
    }
}
