package com.crudapp.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Label {
    private Long id;
    private String name;
}
