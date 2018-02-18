package ka.masato.lift.liftdevicemanager.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer eventId;
    private String eventName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liftId")
    @JsonIgnore
    private Lift lift;

}
