package skn.lms.infra.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skn.lms.datastore.FabricColor;
import skn.lms.datastore.repository.FabricColorRepository;
import skn.lms.infra.dto.FabricColorDto;

import java.util.Optional;

import static skn.lms.infra.dto.FabricColorDto.toDto;
import static skn.lms.infra.dto.FabricColorDto.toEntity;
import static skn.lms.infra.exception.ExceptionUtil.buildAndThrow;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricColorService {

  private final FabricColorRepository fabricColorRepository;

  public void createFabricColor(Optional<FabricColorDto> fabricColorDtoOp) {

    if (fabricColorDtoOp.isPresent()) {
      Optional<FabricColor> fabricColorOp;

      if ((fabricColorOp = toEntity(fabricColorDtoOp)).isPresent()) {
        FabricColor fabricColor = fabricColorOp.get();

        if (fabricColorRepository.existsByFabricColorCodeEquals(fabricColor.getFabricColorCode())) {
          buildAndThrow(
              HttpStatus.CONFLICT,
              "Invalid color code. `" + fabricColor.getFabricColorCode() + "` Already exist.");
        }

        fabricColorRepository.save(fabricColor);
        return;
      }
    }

    buildAndThrow(HttpStatus.BAD_REQUEST, "Invalid content.");
  }

  public Optional<FabricColorDto> getFabricColorByColorCode(String colorCode) {

    if (colorCode == null || colorCode.length() == 0) {
      buildAndThrow(HttpStatus.BAD_REQUEST, "Color code can not be empty.");
    }

    Optional<FabricColorDto> fabricColorDtoOp;

    if ((fabricColorDtoOp =
            toDto(fabricColorRepository.findFabricColorByFabricColorCodeEquals(colorCode)))
        .isEmpty()) {
      buildAndThrow(HttpStatus.NOT_FOUND, "Color does not exist.");
    }

    return fabricColorDtoOp;
  }
}
