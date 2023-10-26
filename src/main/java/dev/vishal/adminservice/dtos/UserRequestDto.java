package dev.vishal.adminservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String name; // name of user
    private String email;
    private String password;
    private String role; // role of user
}
