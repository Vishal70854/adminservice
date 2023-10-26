package dev.vishal.adminservice.services;

import dev.vishal.adminservice.dtos.LoginRequestDto;
import dev.vishal.adminservice.dtos.RegisterComplaintDto;
import dev.vishal.adminservice.dtos.UserRequestDto;
import dev.vishal.adminservice.models.Complaint;
import dev.vishal.adminservice.models.ComplaintStatus;
import dev.vishal.adminservice.models.Role;
import dev.vishal.adminservice.models.User;
import dev.vishal.adminservice.repositories.ComplaintRepository;
import dev.vishal.adminservice.repositories.RoleRepository;
import dev.vishal.adminservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service // this annotation helps spring to create object of this class at starting of spring boot application.
@Primary    // controller will directly create object of this class and call methods of this class
public class AdminService {
    private UserRepository userRepository;
    private ComplaintRepository complaintRepository;
    private final RoleRepository roleRepository;
    // dependency injection of repository classes

    public AdminService(UserRepository userRepository, ComplaintRepository complaintRepository,
                        RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
        this.roleRepository = roleRepository;
    }

    public String signup(UserRequestDto userRequestDto){
        Optional<User> userOptional = userRepository.findByName(userRequestDto.getName());

        if (!userOptional.isEmpty()){
            return "User Already Exists.";
        }

        User user = new User(); // created new user object
        Role role = new Role(); // created a role object for testing purpose.
        role.setRole(userRequestDto.getRole());
        // add the fields of user in User table
        user.setName(userRequestDto.getName());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(role);
        user.setCreatedAt(new Date());
        user.setEmail(userRequestDto.getEmail());
        userRepository.save(user); // save the user object in db


        return "User created Successfully";
    }

    public String login(LoginRequestDto loginRequestDto){
        Optional<User> userOptional = userRepository.findByName(loginRequestDto.getEmail());

        if (userOptional.isEmpty()){
            return "User Credentials do not match. Please Try Again!";
        }
        User user = userOptional.get(); // get the user object

        return "Logged In Successfully";
    }
    public String registerComplaint(RegisterComplaintDto request) {
        Optional<User> userOptional = userRepository.findByName(request.getName());

        if (userOptional.isEmpty()){
            return "User doesn't exists";
        }

        User user = userOptional.get(); // get the user object

        Complaint complaint = new Complaint(); //  created object of Complaint class
        // add all complaint field in Complaint table
        complaint.setName(request.getName());;
        complaint.setComplaintStatus(ComplaintStatus.NEW);
        complaint.setDescription(request.getDescription());
        complaint.setCreatedBy(user); // set user at createdBy
        complaint.setCreatedAt(new Date());

        complaintRepository.save(complaint); // save the complaint in db

        return "Complaint Registered Successfully";

    }
}
