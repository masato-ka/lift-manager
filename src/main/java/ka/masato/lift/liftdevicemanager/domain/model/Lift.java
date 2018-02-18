package ka.masato.lift.liftdevicemanager.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lift {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer liftId;
    @NotNull //Please add annotation for Unique.
    //TODO Do Not Update deviceName;
    private String deviceId;
    private String imsi;
    private String status;
    @OneToOne(fetch = FetchType.LAZY,
    cascade =  CascadeType.ALL,
    mappedBy = "lift")
    private StoredItem things;

    @OneToMany(mappedBy = "lift")
    private List<Event> events;
    @OneToMany(mappedBy = "lift")
    private List<Schedule> schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    private LiftUser user;




}
