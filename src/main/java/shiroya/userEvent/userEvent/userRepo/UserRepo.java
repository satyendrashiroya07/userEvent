package shiroya.userEvent.userEvent.userRepo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiroya.userEvent.userEvent.userEntity.UserEntity;

import java.util.List;


@Repository
public interface UserRepo<Optinal> extends JpaRepository<UserEntity, Long> {


    List<UserEntity> findByUserNameContainingIgnoreCase(String userName);

    UserEntity getByUserId(String userId);
}
