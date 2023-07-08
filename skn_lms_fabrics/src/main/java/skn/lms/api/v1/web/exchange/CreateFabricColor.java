package skn.lms.api.v1.web.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import skn.lms.datastore.FabricColorStatus;

public record CreateFabricColor(
    @JsonProperty("code") @NotBlank String fabricColorCode,
    @JsonProperty("description") String description,
    @JsonProperty("status") @NotNull FabricColorStatus status) {}
