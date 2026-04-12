package shiroya.userEvent.userEvent.userRepo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiroya.userEvent.userEvent.userEntity.UserEntity;

@Repository
public interface UserRepo<Optinal> extends JpaRepository<UserEntity, Long> {


    UserEntity findByUserName(String userName);

    UserEntity getByUserId(String userId);
}
