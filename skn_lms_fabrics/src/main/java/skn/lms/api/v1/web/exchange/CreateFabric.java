package skn.lms.api.v1.web.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import skn.lms.datastore.FabricStatus;

import java.math.BigDecimal;
import java.util.List;

public record CreateFabric(
    @JsonProperty("code") @NotBlank String fabricCode,
    @JsonProperty("description") String description,
    @JsonProperty("price") @NotNull BigDecimal price,
    @JsonProperty("color-code") @NotBlank String colorCode,
    @JsonProperty("materials") @NotNull List<FabMaterial> fabMaterials,
    @JsonProperty("status") @NotNull FabricStatus status) {}
