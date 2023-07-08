package skn.lms.api.v1;

// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import skn.lms.api.v1.web.exchange.CreateFabric;
import skn.lms.datastore.FabricStatus;
import skn.lms.infra.dto.FabricSortByParameter;
import skn.lms.infra.dto.SortByOrder;
import skn.lms.infra.service.FabricColorService;
import skn.lms.infra.service.FabricService;
import skn.lms.infra.service.MaterialService;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/fabric")
// @Api
public class FabricEndpoint {
  private final MaterialService materialService;
  private final FabricService fabricService;
  private final FabricColorService fabricColorService;

  @PostMapping(value = {""})
  //  @ApiOperation(value = "Create new material")
  ResponseEntity<?> createFabric(@Valid @RequestBody CreateFabric createFabric) {

    fabricService.createNewFabric(createFabric);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/" + createFabric.fabricCode())
            .build()
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping(value = {"/code/{fabricCode}"})
  //  @ApiOperation(value = "Get Fabric by code")
  ResponseEntity<?> getFabricByCode(@PathVariable(value = "fabricCode") String fabricCode) {
    return ResponseEntity.ok(fabricService.getFabricByCode(fabricCode));
  }

  @GetMapping(value = {"/{fabricId}"})
  //  @ApiOperation(value = "Get Fabric by Id")
  ResponseEntity<?> getFabricById(@PathVariable(value = "fabricId") Long fabricId) {
    return ResponseEntity.ok(fabricService.getFabricById(fabricId));
  }

  @GetMapping("")
  ResponseEntity<?> searchFabric(
      @RequestParam(value = "fabric-code", required = false) String fabricCode,
      @RequestParam(value = "color-code", required = false) String colorCode,
      @RequestParam(value = "material-code", required = false) String materialCode,
      @RequestParam(value = "fabric-status[]", required = false, defaultValue = "")
          FabricStatus[] fabricStatus,
      @RequestParam(value = "sort-by", required = false, defaultValue = "ID")
          FabricSortByParameter sortBy,
      @Valid
          @Min(value = 0, message = "must be greater than or equal to 0")
          @RequestParam(value = "page-number", required = false, defaultValue = "0")
          Integer pageNumber,
      @Valid
          @Min(value = 1, message = "{pagination.itemsPerPage.min}")
          @RequestParam(value = "per-page", required = false, defaultValue = "10")
          Integer perPage,
      @RequestParam(value = "sort-order", required = false, defaultValue = "ASC")
          SortByOrder sortByOrder) {
    Set<FabricStatus> statuses = Arrays.stream(fabricStatus).collect(Collectors.toSet());

    return ResponseEntity.ok(
        fabricService.findAllBySpecification(
            fabricCode,
            colorCode,
            materialCode,
            statuses,
            sortBy,
            pageNumber,
            perPage,
            sortByOrder));
  }
}
