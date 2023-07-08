package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum FabricStatus {
	@JsonProperty("AVAILABLE")
	AVAILABLE,

	@JsonProperty("UN_AVAILABLE")
	UN_AVAILABLE;
}
