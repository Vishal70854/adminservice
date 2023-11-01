package dev.vishal.adminservice.controllers;

import dev.vishal.adminservice.dtos.*;
import dev.vishal.adminservice.models.Complaint;
import dev.vishal.adminservice.services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indicates spring that this special class will contain all api endpoints
@RequestMapping("/auth")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService){

        this.adminService = adminService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserRequestDto userRequestDto){
        return adminService.signup(userRequestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        return adminService.login(loginRequestDto);
    }
    // CRUD operations for complaint
    @PostMapping("/raiseComplaint")
    public String registerComplaint(@RequestBody RegisterComplaintDto request){
        return adminService.registerComplaint(request);
    }

    @GetMapping("/getAllComplaintDetails/{email}")
    public List<GetComplaintDetailsDto> getAllComplaintDetails(@PathVariable("email") String email){
        return adminService.getAllComplaintDetails(email);
    }

    @PostMapping("/updateComplaint")
    public Complaint updateComplaint(@RequestBody UpdateComplaintDto complaintDetailsDto){
        return adminService.updateComplaint(complaintDetailsDto);
    }

    @GetMapping("deleteComplaint/{id}")
    public String deleteComplaint(@PathVariable("id") Long id){
        return adminService.deleteComplaint(id);
    }

    @GetMapping("/retrieveComplaint/{status}")
    public List<Complaint> retrieveComplaints(@PathVariable("status") String status){
        return adminService.retrieveAllComplaints(status);
    }

}
