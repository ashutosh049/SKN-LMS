package skn.lms.api.v1;

// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import skn.lms.api.v1.web.exchange.CreateMaterial;
import skn.lms.infra.dto.MaterialDto;
import skn.lms.infra.service.MaterialService;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/fabric-material")
// @Api
public class MaterialEndpoint {
  private final MaterialService materialService;

  @PostMapping(value = {""})
  //  @ApiOperation(value = "Create new material")
  ResponseEntity<?> createFabricColor(@Valid @RequestBody CreateMaterial createMaterial) {

    materialService.createMaterial(
        Optional.of(
            new MaterialDto(
                null,
                createMaterial.materialCode(),
                createMaterial.description(),
                createMaterial.status())));

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/" + createMaterial.materialCode())
            .build()
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping(value = {"/{materialCode}"})
  //  @ApiOperation(value = "Get material by code")
  ResponseEntity<?> getMaterialByCode(@PathVariable(value = "materialCode") String materialCode) {
    return ResponseEntity.ok(materialService.getMaterialByCode(materialCode));
  }
}
