package hr.estudent.webshop.estudentwebshop.repository;

import hr.estudent.webshop.estudentwebshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
