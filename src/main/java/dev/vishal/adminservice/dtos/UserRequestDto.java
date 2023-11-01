package dev.vishal.adminservice.dtos;

import dev.vishal.adminservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRequestDto {
    private String name; // name of user
    private String email;
    private String password;

    private List<String> roles = new ArrayList<>();
}
