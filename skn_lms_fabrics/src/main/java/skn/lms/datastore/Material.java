package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    name = "material",
    uniqueConstraints = {
      @UniqueConstraint(name = "material_unique_material_code_idx", columnNames = "material_code")
    })
public class Material extends Auditable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "material_id", nullable = false)
  private Long materialId;

  @Column(name = "material_code", nullable = false, updatable = false)
  private String materialCode;

  private String description;

  @Enumerated(EnumType.STRING)
  private MaterialStatus status;

  @JsonManagedReference
  @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Collection<FabricMaterial> fabricMaterials = new HashSet<>();

  public void setFabricMaterials(Collection<FabricMaterial> fabricMaterials) {
    if (!isEmpty(fabricMaterials)) {

      if (!isEmpty(this.fabricMaterials)) {
        this.fabricMaterials = new HashSet<>();
      }

      fabricMaterials.forEach(
          entry_ -> {
            this.fabricMaterials.add(entry_);
            entry_.setMaterial(this);
          });
    }
  }
}
