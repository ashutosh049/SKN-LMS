package skn.lms.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import skn.lms.datastore.FabricMaterial;

import java.util.Optional;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record FabricMaterialDto(
    Long fabricMaterialId, float percentage, FabricDto fabric, MaterialDto material) {

  public Optional<FabricMaterial> toEntity(Optional<FabricMaterialDto> fabricMaterialDtoOp) {
    if (fabricMaterialDtoOp.isPresent()) {
      FabricMaterialDto fabricMaterialDto = fabricMaterialDtoOp.get();

      FabricMaterial fabricMaterial =
          FabricMaterial.builder()
              .fabricMaterialId(fabricMaterialDto.fabricMaterialId())
              .percentage(fabricMaterialDto.percentage())
              // .fabric(FabricDto.toEntity())
              .material(MaterialDto.toEntity(Optional.of(fabricMaterialDto.material())).get())
              .build();

      return Optional.of(fabricMaterial);
    }

    return Optional.empty();
  }

  public FabricMaterialDto setFabric(FabricDto fabricDto) {
    return new FabricMaterialDto(
        this.fabricMaterialId(), this.percentage(), fabricDto, this.material());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    FabricMaterialDto that = (FabricMaterialDto) o;

    return new EqualsBuilder()
        .append(percentage, that.percentage)
        .append(fabricMaterialId, that.fabricMaterialId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(fabricMaterialId).append(percentage).toHashCode();
  }
}
