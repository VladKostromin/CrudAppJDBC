package com.crudapp.model;

import com.crudapp.enums.PostStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Data
public class Post {
    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.NONE)
    private Long writerId;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private PostStatus status;
    List<Label> labels;

}
