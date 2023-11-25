package com.crudapp.repository;

import com.crudapp.model.Post;
import com.crudapp.model.Writer;

public interface PostRepository extends GenericRepository<Post, Long> {
    Post addLabelToPost(Long postId, Long labelId);
    Writer getWriter(Long id);
}
