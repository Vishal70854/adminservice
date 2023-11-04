package dev.vishal.adminservice.repositories;

import dev.vishal.adminservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Repository  // tells spring to create a repository for contacting with db
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.id, count(c.assignedTo) FROM User u innerjoin Complaint c group by c.assignedTo "
            +"order by c.assignedTo limit 1",
            nativeQuery = true)
    Optional<User> findByRolesToAssign();
}