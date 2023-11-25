package com.crudapp.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
}
