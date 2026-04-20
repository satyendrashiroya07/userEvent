package shiroya.userEvent.userEvent.userEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OutBoxEventUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private String aggregateId;
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status;
}
