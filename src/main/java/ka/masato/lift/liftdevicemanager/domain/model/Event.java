package ka.masato.lift.liftdevicemanager.domain.model;


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
    @GeneratedValue
    private Integer eventId;
    private String eventName;
    @ManyToOne
    private Lift lift;

}
