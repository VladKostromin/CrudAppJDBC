package com.crudapp.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class Label {
    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.NONE)
    private Long postId;
    private String name;
}
