package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

import static org.springframework.util.CollectionUtils.isEmpty;

// @Builder
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fabric")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicInsert
@DynamicUpdate
public class Fabric extends Auditable<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "fabric_id", nullable = false)
  private Long fabricId;

  @Column(name = "fabric_code", nullable = false, unique = true, updatable = false)
  private String fabricCode;

  private String description;

  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  private FabricStatus status;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fabric_color_id")
  private FabricColor fabricColor;

  @JsonManagedReference
  @OneToMany(mappedBy = "fabric", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Collection<FabricMaterial> fabricMaterials = new HashSet<>();

  public void setFabricMaterials(Collection<FabricMaterial> fabricMaterials) {
    if (!isEmpty(fabricMaterials)) {

      if (!isEmpty(this.fabricMaterials)) {
        this.fabricMaterials = new HashSet<>();
      }

      fabricMaterials.forEach(
          entry_ -> {
            this.fabricMaterials.add(entry_);
            entry_.setFabric(this);
          });
    }
  }
}
