package skn.lms.infra.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import skn.lms.api.v1.web.exchange.CreateFabric;
import skn.lms.api.v1.web.exchange.FabMaterial;
import skn.lms.datastore.Fabric;
import skn.lms.datastore.FabricStatus;
import skn.lms.datastore.repository.FabricColorRepository;
import skn.lms.datastore.repository.FabricRepository;
import skn.lms.datastore.repository.MaterialRepository;
import skn.lms.infra.dto.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;
import static skn.lms.datastore.predicates.FabricPredicateUtil.getFabricBySpecification;
import static skn.lms.infra.dto.FabricColorDto.toDto;
import static skn.lms.infra.dto.MaterialDto.toDto;
import static skn.lms.infra.exception.ExceptionUtil.buildAndThrow;

@RequiredArgsConstructor
@Slf4j
@Service
public class FabricService {

  private final FabricColorRepository fabricColorRepository;
  private final FabricRepository fabricRepository;
  private final MaterialRepository materialRepository;

  public static final PageRequest toPageable(final int pageNumber, final int perPage, Sort sort) {

    int pageNumber_ = pageNumber;
    int perPage_ = perPage;

    if (pageNumber_ < 0) {
      pageNumber_ = 0;
    }

    if (perPage_ < 1) {
      perPage_ = 15;
    } else if (perPage_ > 50) {
      perPage_ = 50;
    }
    return PageRequest.of(pageNumber_, perPage_, sort);
  }

  /** 1. Get color */
  public void createNewFabric(CreateFabric createFabric) {

    Optional<FabricColorDto> fabricColorDtoOp =
        toDto(
            fabricColorRepository.findFabricColorByFabricColorCodeEquals(
                createFabric.colorCode().trim()));

    if (fabricColorDtoOp.isEmpty()) {
      buildAndThrow(HttpStatus.BAD_REQUEST, "Invalid color code.");
    }

    // Get all Materials from repo
    List<FabMaterial> fabMaterials = createFabric.fabMaterials();

    if (isEmpty(fabMaterials)) {
      buildAndThrow(HttpStatus.BAD_REQUEST, "Materials can not be empty.");
    }

    final Set<FabricMaterialDto> fabricMaterialDtos = new HashSet<>();

    FabricDto fabricDto =
        new FabricDto(
            createFabric.fabricCode(),
            createFabric.description(),
            createFabric.price(),
            createFabric.status(),
            fabricColorDtoOp.get(),
            fabricMaterialDtos);

    fabMaterials.forEach(
        fabMaterial_ -> {
          Optional<MaterialDto> materialDtoOp =
              toDto(
                  materialRepository.findMaterialByMaterialCodeEquals(fabMaterial_.materialCode()));

          if (materialDtoOp.isEmpty()) {
            buildAndThrow(HttpStatus.BAD_REQUEST, "Materials can not be empty.");
          }

          FabricMaterialDto fabricMaterialDto =
              new FabricMaterialDto(null, fabMaterial_.percent(), null, materialDtoOp.get());

          fabricMaterialDto = fabricMaterialDto.setFabric(fabricDto);

          fabricMaterialDtos.add(fabricMaterialDto);
        });

    fabricRepository.save(FabricDto.toEntity(Optional.of(fabricDto)).get());
  }

  public Optional<FabricDto> getFabricByCode(String code) {
    if (code == null || code.length() == 0) {
      buildAndThrow(HttpStatus.BAD_REQUEST, "Fabric code can not be empty.");
    }

    Optional<FabricDto> fabricDtoOp;

    if ((fabricDtoOp = FabricDto.toDto(fabricRepository.findFabricByFabricCodeEquals(code)))
        .isEmpty()) {
      buildAndThrow(HttpStatus.NOT_FOUND, "Fabric does not exist.");
    }

    return fabricDtoOp;
  }

  public Optional<FabricDto> getFabricById(Long id) {
    if (id == null) {
      buildAndThrow(HttpStatus.BAD_REQUEST, "Fabric id can not be empty.");
    }

    Optional<FabricDto> fabricDtoOp;

    if ((fabricDtoOp = FabricDto.toDto(fabricRepository.findById(id))).isEmpty()) {
      buildAndThrow(HttpStatus.NOT_FOUND, "Fabric does not exist.");
    }

    return fabricDtoOp;
  }

  public PagedDto findAllBySpecification(
      String fabricCode,
      String colorCode,
      String materialCode,
      Set<FabricStatus> fabricStatus,
      FabricSortByParameter sortBy,
      int pageNumber,
      int perPage,
      SortByOrder sortByOrder) {

    FabricSortByParameter sortByParam = FabricSortByParameter.ID;
    Sort.Direction direction = Sort.Direction.ASC;

    if (sortBy != null) {
      sortByParam = sortBy;
    }

    if (sortByOrder != null) {
      direction = Sort.Direction.valueOf(sortByOrder.name());
    }

    final PageRequest pageRequest =
        toPageable(pageNumber, perPage, Sort.by(direction, sortByParam.getGetParameterName()));

    Specification<Fabric> specification =
        getFabricBySpecification(fabricCode, colorCode, materialCode, fabricStatus);

    final Page<Fabric> page = fabricRepository.findAll(specification, pageRequest);

    return PagedDto.builder()
        .withData(page.getContent().stream().map(entity_ -> FabricDto.toDto(Optional.of(entity_))))
        .withPaginator(
            CustomPaginator.builder()
                .withPageNo(page.getNumber())
                .withItemPerPages(page.getNumberOfElements())
                .withTotalItems(page.getTotalElements())
                .withHasNext(page.hasNext())
                .withHasPrevious(page.hasPrevious())
                .withIsFirst(page.isFirst())
                .withIsLast(page.isLast())
                .build())
        .build();
  }
}
