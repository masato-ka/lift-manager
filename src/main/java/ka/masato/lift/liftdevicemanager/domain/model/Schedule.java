package ka.masato.lift.liftdevicemanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue
    private Integer scheduleId;
    private String Name;
    private String description;
    @ManyToOne
    private Status desireStatus;
    private LocalDateTime date;
    @ManyToOne
    private Lift lift;
}
