package shiroya.userEvent.userEvent.userRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiroya.userEvent.userEvent.userEntity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
