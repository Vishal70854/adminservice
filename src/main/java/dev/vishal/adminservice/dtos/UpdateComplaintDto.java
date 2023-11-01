package dev.vishal.adminservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateComplaintDto {
    private Long id;
    private String name;
    private String email;
    private String description;
}