package com.crudapp.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class Writer {
    @Setter(AccessLevel.NONE)
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
}
