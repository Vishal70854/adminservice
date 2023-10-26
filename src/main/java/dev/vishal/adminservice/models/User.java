package dev.vishal.adminservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = {jakarta.persistence.CascadeType.PERSIST}) // one to one cardinality
    private Role role;
}
