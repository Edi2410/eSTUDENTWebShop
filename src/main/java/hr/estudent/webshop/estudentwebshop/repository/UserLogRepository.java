package hr.estudent.webshop.estudentwebshop.repository;

import hr.estudent.webshop.estudentwebshop.models.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
