package skn.lms.api.v1.web.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FabMaterial(
    @JsonProperty("material-code") @NotBlank String materialCode,
    @JsonProperty("percent") @NotNull float percent) {}
