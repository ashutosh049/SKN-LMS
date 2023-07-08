package skn.lms.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import skn.lms.api.v1.web.exchange.CreateFabricColor;
import skn.lms.infra.dto.FabricColorDto;
import skn.lms.infra.service.FabricColorService;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/fabric-color")
public class FabricColorEndpoint {

  private final FabricColorService fabricColorService;

  /*public FabricColorEndpoint(FabricColorService fabricColorService) {
    this.fabricColorService = fabricColorService;
  }*/

  @PostMapping(value = {""})
  // @ApiOperation(value = "Create new fabric color")
  ResponseEntity<?> createFabricColor(@Valid @RequestBody CreateFabricColor fabricColor) {

    fabricColorService.createFabricColor(
        Optional.of(
            new FabricColorDto(
                null,
                fabricColor.fabricColorCode(),
                fabricColor.description(),
                fabricColor.status())));

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/" + fabricColor.fabricColorCode())
            .build()
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping(value = {"/{fabricColorCode}"})
  // @ApiOperation(value = "Get fabric color by code")
  ResponseEntity<?> getColorByCode(
      @PathVariable(value = "fabricColorCode") String fabricColorCode) {
    return ResponseEntity.ok(fabricColorService.getFabricColorByColorCode(fabricColorCode));
  }
}
