package dev.vishal.adminservice;

import dev.vishal.adminservice.controllers.AdminController;
import dev.vishal.adminservice.dtos.RegisterComplaintDto;
import dev.vishal.adminservice.dtos.UpdateComplaintDto;
import dev.vishal.adminservice.dtos.UserRequestDto;
import dev.vishal.adminservice.models.Complaint;
import dev.vishal.adminservice.services.AdminService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean // it means we are not going to use real implementation of ProductServiceImpl but we are going to mock it values by hardcoding it in our end for testing purpose.
    private AdminService adminService;

    @Test
    void returnsNullWhenUserDoesNotExist(){
        // mock when user does not exists then return null
        when(
                adminService.signup(any(UserRequestDto.class))
        ).thenReturn(null);
        // as soon as sign up method will be called, null will be returned since we have mocked adminservice
        String userRequestDto = adminController.signup(any(UserRequestDto.class));
        assertNull(userRequestDto);
    }

    @Test
    void returnsNameWhenUserCredentialsAreRight(){
        when(
                adminService.signup(any(UserRequestDto.class))
        ).thenReturn("Vishal");

        String userRequestDto1 = adminController.signup(new UserRequestDto());
        assertEquals("Vishal",userRequestDto1);
    }

    @Test
    void returnsBooleanWhenComplaintIsCorrect(){
        when(
                adminService.registerComplaint(any(RegisterComplaintDto.class))
        ).thenReturn("true");

        String complaintDetails = adminController.registerComplaint(new RegisterComplaintDto());
        assertEquals("true", complaintDetails);
    }

    @Test
    void shouldReturnNameWhenRegisterComplaint(){
        RegisterComplaintDto registerComplaintDto = new RegisterComplaintDto();
        registerComplaintDto.setName("Vishal");

        when(
                adminService.registerComplaint(any(RegisterComplaintDto.class))
        ).thenReturn("Vishal");

        assertEquals(registerComplaintDto.getName(), adminController.registerComplaint(registerComplaintDto));
    }

    @Test
    void getEmptyListWhenNoComplaintsAvailable(){
        when(
                adminService.getAllComplaintDetails(any()) // under any() we can give anything based upon our requirement.
        ).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), adminController.getAllComplaintDetails(any()));
    }

    @Test
    void returnNullWhenNoComplaintExists(){
        when(
                adminService.registerComplaint(any(RegisterComplaintDto.class))
        ).thenReturn(null);

        assertEquals(null, adminController.registerComplaint(any(RegisterComplaintDto.class)));
    }

    @Test
    void returnsTrueWhenComplaintUpdateIsSuccessful(){
        Complaint complaint = new Complaint();
        complaint.setName("Vishal");

        UpdateComplaintDto updateComplaintDto = new UpdateComplaintDto();
        updateComplaintDto.setName("Vishal");

        when(
                adminService.updateComplaint(any(UpdateComplaintDto.class))
        ).thenReturn(complaint);

        assertEquals(complaint.getName(), adminController.updateComplaint(updateComplaintDto).getName());
    }

    @Test
    void returnNullWhenNoComplaintExistsToUpdate(){
        when(
                adminService.updateComplaint(any(UpdateComplaintDto.class))
        ).thenReturn(null);

        assertEquals(null, adminController.updateComplaint(any(UpdateComplaintDto.class)));
    }

    @Test
    void returnsNullWhenComplaintAvailableToDelete(){
        when(
                adminService.deleteComplaint(any())
        ).thenReturn(null);

        String deleteComplaint = adminController.deleteComplaint(any());
        assertNull(deleteComplaint);
    }

    @Test
    void returnsTheComplaintWhichIsDeleted(){
        String deleteComplaint = adminService.deleteComplaint(1L);

        when(
                adminService.deleteComplaint(any())
        ).thenReturn("deleted");

        assertEquals("deleted", adminController.deleteComplaint(any()));
    }

    @Test
    void returnsNullWhenNoComplaintsAvailableToRetrieve(){
        when(
                adminService.retrieveAllComplaints(any())
        ).thenReturn(null);

        assertNull(adminController.retrieveComplaints(any()));
    }

    @Test
    void returnsComplaintWithGivenStatus(){
        List<Complaint> complaintList = new ArrayList<>();
        complaintList.add(new Complaint());
        complaintList.add(new Complaint());
        complaintList.add(new Complaint());

        when(
                adminService.retrieveAllComplaints(any())
        ).thenReturn(complaintList);

        assertEquals(complaintList, adminController.retrieveComplaints(any()));
    }
}
