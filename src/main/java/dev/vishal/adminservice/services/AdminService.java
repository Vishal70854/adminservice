package dev.vishal.adminservice.services;

import dev.vishal.adminservice.dtos.*;
import dev.vishal.adminservice.models.Complaint;
import dev.vishal.adminservice.models.ComplaintStatus;
import dev.vishal.adminservice.models.Role;
import dev.vishal.adminservice.models.User;
import dev.vishal.adminservice.repositories.ComplaintRepository;
import dev.vishal.adminservice.repositories.RoleRepository;
import dev.vishal.adminservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service // this annotation helps spring to create object of this class at starting of spring boot application.
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

    @Transactional
    public String signup(UserRequestDto userRequestDto){
        Optional<User> userOptional = userRepository.findByEmail(userRequestDto.getEmail());

        if (!userOptional.isEmpty()){
            return "User Already Exists.";
        }

        User user = new User(); // created new user object
        // add the fields of user in User table
        user.setName(userRequestDto.getName());
        user.setPassword(userRequestDto.getPassword());
//        user.setRoles(userRequestDto.getRoles());
        user.setCreatedAt(new Date());
        user.setEmail(userRequestDto.getEmail());

        // set the list of roles
        List<Role> roles = new ArrayList<>();
        for (String roleObj : userRequestDto.getRoles()){
            Optional<Role> role = roleRepository.findByRole(roleObj);
            roles.add(role.get());
        }
        user.setRoles(roles);
        userRepository.save(user); // save the user object in db

        return "User created Successfully";
    }

    public String login(LoginRequestDto loginRequestDto){
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDto.getEmail());

        if (userOptional.isEmpty()){
            return "User Credentials do not match. Please Try Again!";
        }
        User user = userOptional.get(); // get the user object

        return "Logged In Successfully";
    }

    //CRUD operations
    // create a complaint
    public String registerComplaint(RegisterComplaintDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()){
            return "User doesn't exists";
        }

        User user = userOptional.get(); // get the user object

        Complaint complaint = new Complaint(); //  created object of Complaint class
        // add all complaint field in Complaint table
        complaint.setName(request.getName());
        complaint.setEmail(user.getEmail());
        complaint.setComplaintStatus(ComplaintStatus.NEW);
        complaint.setDescription(request.getDescription());
        complaint.setCreatedBy(user); // set user at createdBy

        // assign complain to OFFICER role user
        Optional<User> assignedUserOptional = userRepository.findByRolesToAssign();
        if (assignedUserOptional.isEmpty()){
            throw new RuntimeException("User doesn't exists");
        }
        User assignedUser = assignedUserOptional.get();
        System.out.println("assigned user is :" + assignedUser.getId());
        complaint.setAssignedTo(assignedUser);

        complaintRepository.save(complaint); // save the complaint in db

        return "Complaint Registered Successfully";

    }
    // get list of complaints by a particular mail
    public List<GetComplaintDetailsDto> getAllComplaintDetails(String email) {
        List<Complaint> complaintsList = complaintRepository.findAllByEmail(email);

        if (complaintsList.size() == 0) // no complaints available.
            throw new RuntimeException("No complaint exists for the mentioned email");

        List<GetComplaintDetailsDto> complaintDetailsDtos = new ArrayList<>();

        for (Complaint complaint : complaintsList){
            GetComplaintDetailsDto getComplaintDetailsDto = new GetComplaintDetailsDto();
            getComplaintDetailsDto.setName(complaint.getName());
            getComplaintDetailsDto.setEmail(complaint.getEmail());
            getComplaintDetailsDto.setDescription(complaint.getDescription());

            complaintDetailsDtos.add(getComplaintDetailsDto);
        }
        return complaintDetailsDtos;    // list of complaints
    }
    // update a complaint
    public Complaint updateComplaint(UpdateComplaintDto getComplaintDetailsDto){

        Optional<Complaint> complaintOptional = complaintRepository.findById(getComplaintDetailsDto.getId());

        if (complaintOptional.isEmpty())
            throw new RuntimeException("Complaint does not exists");

        Complaint complaint = complaintOptional.get();
        complaint.setName(getComplaintDetailsDto.getName());
        complaint.setDescription(getComplaintDetailsDto.getDescription());

//        // assign complain to OFFICER role user
//        User assignedUser = userRepository.findByRolesToAssign();
//        System.out.println("assigned user is :" +assignedUser.getId());
//        complaint.setAssignedTo(assignedUser);

        complaintRepository.save(complaint); // update and save complaint obj in db

        return complaint;
    }

    // delete a complaint with a particular id
    public String deleteComplaint(Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);

        if (complaintOptional.isEmpty()){
            throw new RuntimeException("Complaint with the id doesn't exists");
        }

        Complaint complaint = complaintOptional.get();
        complaintRepository.deleteById(complaint.getId());

        return "Complaint deleted Successfully";
    }

    public List<Complaint> retrieveAllComplaints(String status){
        List<Complaint> complaintOptional = complaintRepository.findByComplaintStatus(ComplaintStatus.valueOf(status));

        if (complaintOptional.size() == 0){
            throw new RuntimeException("No Complaints found with the given status");
        }
//        for (Complaint complaint : complaintOptional){
//            System.out.println(complaint);
//        }
        return complaintOptional;
//        System.out.println(complaintOptional);
//        return "Retrieve Complaints";
    }

}
