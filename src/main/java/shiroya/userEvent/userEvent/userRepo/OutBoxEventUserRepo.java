package shiroya.userEvent.userEvent.userRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiroya.userEvent.userEvent.userEntity.OutBoxEventUser;

import java.util.List;

@Repository
public interface OutBoxEventUserRepo extends JpaRepository<OutBoxEventUser, Long> {
    List<OutBoxEventUser> findByStatus(String aNew);
}
