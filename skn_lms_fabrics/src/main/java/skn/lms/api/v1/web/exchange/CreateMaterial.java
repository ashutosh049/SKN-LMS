package skn.lms.api.v1.web.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import skn.lms.datastore.MaterialStatus;

public record CreateMaterial(
		@JsonProperty("code") @NotBlank String materialCode,
		@JsonProperty("description") String description,
		@JsonProperty("status") @NotNull MaterialStatus status) {}
