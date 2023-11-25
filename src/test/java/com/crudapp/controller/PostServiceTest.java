package com.crudapp.controller;

import com.crudapp.enums.PostStatus;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.PostRepository;
import com.crudapp.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService postService;

    private Post post;
    private Long postId = 1L;


    @BeforeEach
    public void setUpPost(){
        post = Post.builder()
                .id(postId)
                .status(PostStatus.ACTIVE)
                .content("")
                .created(new Timestamp(new Date().getTime()))
                .updated(new Timestamp(new Date().getTime()))
                .labels(new ArrayList<>())
                .writer(new Writer())
                .build();
    }

    @Test
    public void testGetPostByID_Found() throws StatusDeletedException, NotFoundException {
        Mockito.when(postRepository.findById(postId)).thenReturn(post);
        Post result = postService.getPostByID(postId);
        Assertions.assertEquals(post, result);
        Mockito.verify(postRepository).findById(postId);

    }
    @Test
    public void testGetPostByID_NotFound() {
        Mockito.when(postRepository.findById(postId)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> postService.getPostByID(postId));
    }

    @Test
    public void testGetPostById_Deleted() {
        post.setStatus(PostStatus.DELETED);
        Mockito.when(postRepository.findById(postId)).thenReturn(post);
        Assertions.assertThrows(StatusDeletedException.class, () -> postService.getPostByID(postId));
    }

    @Test
    public void testCreatePost() {
        Post savedPost = Post.builder()
                .id(postId)
                .writer(new Writer())
                .content("")
                .created(post.getCreated())
                .updated(post.getUpdated())
                .status(PostStatus.ACTIVE)
                .labels(new ArrayList<>())
                .build();

        Mockito.when(postRepository.save(post)).thenReturn(savedPost);

        Post resultPost = postService.createPost(post);
        Assertions.assertEquals(savedPost, resultPost);
        Mockito.verify(postRepository).save(post);
    }
    @Test
    public void testUpdatePost_Success() throws StatusDeletedException, NotFoundException {
        Mockito.when(postRepository.update(post)).thenReturn(post);

        Post resultPost = postService.updatePost(post);

        Assertions.assertEquals(post, resultPost);
        Mockito.verify(postRepository).update(post);
    }
    @Test
    public void testUpdatePost_NotFound() {
        Mockito.when(postRepository.update(post)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> postService.updatePost(post));
    }
    @Test
    public void testUpdatePost_Deleted() {
        post.setStatus(PostStatus.DELETED);
        Mockito.when(postRepository.update(post)).thenReturn(post);
        Assertions.assertThrows(StatusDeletedException.class, () -> postService.updatePost(post));
    }

    @Test
    public void testGetAll_ActiveAndUnderReviewPosts() {
        List<Post> allPosts = List.of(post, post, post, post, post, post);
        allPosts.get(0).setStatus(PostStatus.DELETED);
        allPosts.get(1).setStatus(PostStatus.UNDER_REVIEW);
        allPosts.get(2).setStatus(PostStatus.DELETED);
        allPosts.get(4).setStatus(PostStatus.UNDER_REVIEW);
        List<Post> filteredList = allPosts.stream()
                .filter(post -> post.getStatus().equals(PostStatus.ACTIVE) || post.getStatus().equals(PostStatus.UNDER_REVIEW))
                .collect(Collectors.toList());
        Mockito.when(postRepository.getAll()).thenReturn(allPosts);
        List<Post> resultList = postService.getAllActiveAndUnderReviewPosts();

        Assertions.assertEquals(filteredList, resultList);
        Mockito.verify(postRepository).getAll();
    }

}
