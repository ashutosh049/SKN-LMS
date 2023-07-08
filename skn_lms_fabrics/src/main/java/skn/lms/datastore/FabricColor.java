package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.HashSet;

import static org.springframework.util.CollectionUtils.isEmpty;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "fabric_color",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "fabric_color_unique_fabric_color_code_idx",
          columnNames = "fabric_color_code")
    })
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicInsert
@DynamicUpdate
public class FabricColor extends Auditable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "fabric_color_id", nullable = false)
  private Long fabricColorId;

  @Column(name = "fabric_color_code", nullable = false, updatable = false)
  private String fabricColorCode;

  private String description;

  @Enumerated(EnumType.STRING)
  private FabricColorStatus status;

  @JsonManagedReference
  @OneToMany(mappedBy = "fabricColor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Collection<Fabric> fabrics = new HashSet<>();

  public void setFabrics(Collection<Fabric> fabrics) {
    if (!isEmpty(fabrics)) {

      if (!isEmpty(this.fabrics)) {
        this.fabrics = new HashSet<>();
      }

      fabrics.forEach(
          entry_ -> {
            this.fabrics.add(entry_);
            entry_.setFabricColor(this);
          });
    }
  }
}
