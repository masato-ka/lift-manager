package ka.masato.lift.liftdevicemanager.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String deviceId;
    @NotNull
    @Column(unique = true)
    private String imsi;
    @OneToOne(fetch = FetchType.LAZY,
    cascade =  CascadeType.ALL,
    mappedBy = "lift")
    private StoredItem things;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lift")
    @JsonIgnore
    private List<Event> events;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lift")
    private List<Schedule> schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    private LiftUser user;

}
