package com.project.usermanager.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.hateoas.RepresentationModel;



@EqualsAndHashCode(callSuper = true)
@Data
@Document(indexName = "user")
public class User extends RepresentationModel<User> {

    @Id
    private String id;

    private String name;

    private Long age;
}
