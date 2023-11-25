package com.crudapp.model;

import com.crudapp.enums.PostStatus;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@ToString(exclude = {"writer"})
@EqualsAndHashCode
public class Post {
    private Long id;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private PostStatus status;
    private Writer writer;
    List<Label> labels;

}
