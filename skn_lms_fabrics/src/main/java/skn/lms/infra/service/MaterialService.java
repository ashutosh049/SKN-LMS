package skn.lms.infra.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skn.lms.datastore.Material;
import skn.lms.datastore.repository.MaterialRepository;
import skn.lms.infra.dto.MaterialDto;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static skn.lms.infra.exception.ExceptionUtil.buildAndThrow;

@RequiredArgsConstructor
@Slf4j
@Service
public class MaterialService {

  private final MaterialRepository materialRepository;

  public void createMaterial(Optional<MaterialDto> materialDtoOp) {
    Optional<Material> materialOp = empty();

    if (materialDtoOp.isEmpty()) {
      buildAndThrow(BAD_REQUEST, "Invalid content.");
    }

    if ((materialOp = MaterialDto.toEntity(materialDtoOp)).isPresent()) {
      Material material = materialOp.get();

      if (materialRepository.existsByMaterialCodeEquals(material.getMaterialCode())) {
        buildAndThrow(
            CONFLICT, "Invalid material code. `" + material.getMaterialCode() + "` Already exist.");
      }

      materialRepository.save(material);
      return;
    }
  }

  public Optional<MaterialDto> getMaterialByCode(String materialCode) {

    if (materialCode == null || materialCode.length() == 0) {
      buildAndThrow(BAD_REQUEST, "Material code can not be empty.");
    }

    Optional<Material> materialOp = empty();

    if ((materialOp = materialRepository.findMaterialByMaterialCodeEquals(materialCode))
        .isPresent()) {
      return MaterialDto.toDto(materialOp);
    }

    buildAndThrow(HttpStatus.NOT_FOUND, "Material does not exist.");
    return empty();
  }
}
