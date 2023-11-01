package dev.vishal.adminservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    private Date createdAt;

    @ManyToMany // many to many cardinality
    private List<Role> roles = new ArrayList<>();
}
