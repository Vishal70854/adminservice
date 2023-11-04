package dev.vishal.adminservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Complaint extends BaseModel{
    private String name;
    private String email;
    private String description;

    @ManyToOne  // many to one cardinality between user and complaint
    private User createdBy;
    @ManyToOne
    private User assignedTo;

    @Enumerated(EnumType.ORDINAL) // enum reference
    private ComplaintStatus complaintStatus;


}
