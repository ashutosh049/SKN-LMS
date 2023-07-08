package skn.lms.infra.dto;

import skn.lms.datastore.Material;
import skn.lms.datastore.MaterialStatus;

import java.util.Optional;

import static java.util.Optional.empty;

// @SuperBuilder
public record MaterialDto(
    Long materialId, String materialCode, String description, MaterialStatus status) {

  public MaterialDto(String materialCode, String description, MaterialStatus status) {
    this(null, materialCode, description, status);
  }

  public MaterialDto {
    if (materialCode.describeConstable().isEmpty()) {
      throw new IllegalArgumentException("Material code cannot be empty.");
    }

    if (status.describeConstable().isEmpty()) {
      throw new IllegalArgumentException("Material Status cannot be empty.");
    }
  }

  public static Optional<MaterialDto> toDto(Optional<Material> entityOp) {
    if (entityOp.isPresent()) {
      Material material = entityOp.get();
      MaterialDto dto =
          new MaterialDto(
              material.getMaterialId(),
              material.getMaterialCode(),
              material.getDescription(),
              material.getStatus());

      return Optional.of(dto);
    }

    return empty();
  }

  public static Optional<Material> toEntity(Optional<MaterialDto> dtoOp) {
    if (dtoOp.isPresent()) {
      MaterialDto dto = dtoOp.get();

      return Optional.of(
          Material.builder()
              .materialId(dto.materialId())
              .materialCode(dto.materialCode())
              .description(dto.description())
              .status(dto.status())
              .build());
    }
    return empty();
  }
}
