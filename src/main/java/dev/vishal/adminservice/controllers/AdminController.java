package dev.vishal.adminservice.controllers;

import dev.vishal.adminservice.dtos.LoginRequestDto;
import dev.vishal.adminservice.dtos.RegisterComplaintDto;
import dev.vishal.adminservice.dtos.UserRequestDto;
import dev.vishal.adminservice.services.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/raiseComplaint")
    public String registerComplaint(@RequestBody RegisterComplaintDto request){
        return adminService.registerComplaint(request);
    }
}
