package dev.vishal.adminservice.repositories;

import dev.vishal.adminservice.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByName(String name); // find all the complaint  with the name




}
