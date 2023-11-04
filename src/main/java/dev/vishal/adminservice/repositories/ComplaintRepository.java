package dev.vishal.adminservice.repositories;

import dev.vishal.adminservice.models.Complaint;
import dev.vishal.adminservice.models.ComplaintStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByEmail(String name); // find all the complaint  with the name

    List<Complaint> findAllByEmail(String email);

    @Override
    void deleteById(Long id);

//    @Query("select * from Complaint where complaintStatus = 'new'")
    List<Complaint> findByComplaintStatus(ComplaintStatus status);

}
