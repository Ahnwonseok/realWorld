package project7.clonecoding.user;

import org.springframework.data.jpa.repository.JpaRepository;
import project7.clonecoding.user.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
    List<Users> OrderByIdDesc();
    Optional<Users> findByUserName(String userName);

    Users findByEmail(String email);
}
