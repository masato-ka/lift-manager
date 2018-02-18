package ka.masato.lift.liftdevicemanager.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Data
@ToString(exclude = "lifts")
@NoArgsConstructor
@AllArgsConstructor
public class LiftUser {

    @Id
    private String userId;
    @JsonIgnore
    private String encodedPassword;
    private String userRole;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private List<Lift> lifts;


}