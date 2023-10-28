package dev.vishal.adminservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    private Date createdAt;

    @OneToOne // one to one cardinality
    private Role role;
}
