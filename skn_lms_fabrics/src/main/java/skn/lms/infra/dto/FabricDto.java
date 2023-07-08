package skn.lms.infra.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import skn.lms.datastore.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;
import static org.springframework.util.CollectionUtils.isEmpty;

public record FabricDto(
    Long fabricId,
    String fabricCode,
    String description,
    BigDecimal price,
    FabricStatus fabricStatus,
    FabricColorDto fabricColorDto,
    Collection<FabricMaterialDto> fabricMaterialDtos) {

  public FabricDto(
      String fabricCode,
      String description,
      BigDecimal price,
      FabricStatus fabricStatus,
      FabricColorDto fabricColorDto,
      Collection<FabricMaterialDto> fabricMaterialDtos) {
    this(null, fabricCode, description, price, fabricStatus, fabricColorDto, fabricMaterialDtos);
  }

  public static Optional<FabricDto> toDto(Optional<Fabric> entityOp) {
    if (entityOp.isPresent()) {
      Fabric fabric = entityOp.get();

      FabricColorDto fabricColorDto =
          FabricColorDto.toDto(Optional.of(fabric.getFabricColor())).orElse(null);

      Collection<FabricMaterialDto> fabricMaterialDtos = new HashSet<>();

      if (!isEmpty(fabric.getFabricMaterials())) {
        fabric.getFabricMaterials().stream()
            .map(
                fabricMaterial_ -> {
                  MaterialDto materialDto =
                      MaterialDto.toDto(Optional.of(fabricMaterial_.getMaterial())).orElse(null);

                  return new FabricMaterialDto(
                      fabricMaterial_.getFabricMaterialId(),
                      fabricMaterial_.getPercentage(),
                      null,
                      materialDto);
                })
            .forEach(fabricMaterialDtos::add);
      }

      return Optional.of(
          new FabricDto(
              fabric.getFabricId(),
              fabric.getFabricCode(),
              fabric.getDescription(),
              fabric.getPrice(),
              fabric.getStatus(),
              fabricColorDto,
              fabricMaterialDtos));
    }
    return empty();
  }

  public static Optional<Fabric> toEntity(Optional<FabricDto> fabricDtoOp) {
    if (fabricDtoOp.isPresent()) {

      FabricDto fabricDto = fabricDtoOp.get();
      FabricColor fabricColor =
          FabricColorDto.toEntity(Optional.of(fabricDto.fabricColorDto())).orElse(null);

      Set<FabricMaterial> fabricMaterials = new HashSet<>();

      Fabric fabric =
          Fabric.builder()
              .fabricId(fabricDto.fabricId())
              .fabricCode(fabricDto.fabricCode())
              .description(fabricDto.description())
              .price(fabricDto.price())
              .status(fabricDto.fabricStatus())
              .fabricColor(fabricColor)
              .fabricMaterials(fabricMaterials)
              .build();

      if (!isEmpty(fabricDto.fabricMaterialDtos)) {

        fabricDto.fabricMaterialDtos.stream()
            .map(
                fabricMaterialDto_ -> {
                  Optional<Material> materialOp =
                      MaterialDto.toEntity(Optional.of(fabricMaterialDto_.material()));

                  return FabricMaterial.builder()
                      .fabricMaterialId(fabricMaterialDto_.fabricMaterialId())
                      .percentage(fabricMaterialDto_.percentage())
                      .material(materialOp.orElse(null))
                       .fabric(fabric)
                      .build();
                })
            .forEach(fabricMaterials::add);
      }

      return Optional.of(fabric);
    }

    return empty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    FabricDto fabricDto = (FabricDto) o;

    return new EqualsBuilder().append(fabricId, fabricDto.fabricId).append(fabricCode, fabricDto.fabricCode).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(fabricId).append(fabricCode).toHashCode();
  }
}
