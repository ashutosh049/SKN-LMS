package skn.lms.infra.dto;

import skn.lms.datastore.FabricColor;
import skn.lms.datastore.FabricColorStatus;

import java.util.Optional;

import static java.util.Optional.empty;

// @SuperBuilder
public record FabricColorDto(
    Long fabricColorId, String fabricColorCode, String description, FabricColorStatus status) {

  public FabricColorDto(String fabricColorCode, String description, FabricColorStatus status) {
    this(null, fabricColorCode, description, status);
  }

  public FabricColorDto {
    if (fabricColorCode.describeConstable().isEmpty()) {
      throw new IllegalArgumentException("Fabric Color-Code cannot be empty.");
    }

    if (status.describeConstable().isEmpty()) {
      throw new IllegalArgumentException("Fabric Status cannot be empty.");
    }
  }

  public static Optional<FabricColorDto> toDto(Optional<FabricColor> entityOp) {

    if (entityOp.isPresent()) {
      FabricColor entity = entityOp.get();

      FabricColorDto fabricColorDto =
          new FabricColorDto(
              entity.getFabricColorId(),
              entity.getFabricColorCode(),
              entity.getDescription(),
              entity.getStatus());

      return Optional.of(fabricColorDto);
    }

    return empty();
  }

  public static Optional<FabricColor> toEntity(Optional<FabricColorDto> dtoOp) {
    Optional<FabricColor> fabricColorOp = empty();

    if (dtoOp.isPresent()) {

      FabricColorDto fabricColorDto = dtoOp.get();

      FabricColor fabricColor =
          FabricColor.builder()
              .fabricColorId(fabricColorDto.fabricColorId())
              .fabricColorCode(fabricColorDto.fabricColorCode())
              .description(fabricColorDto.description())
              .status(fabricColorDto.status())
              .build();

      fabricColorOp = Optional.of(fabricColor);
    }

    return fabricColorOp;
  }
}
