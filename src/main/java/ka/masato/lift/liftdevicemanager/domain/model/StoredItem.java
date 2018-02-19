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
public class StoredItem {

    @Id
    @GeneratedValue
    private Integer itemId;
    private String itemName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liftId", nullable = false)
    @JsonIgnore
    private Lift lift;
}
