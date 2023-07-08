package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum MaterialStatus {
	@JsonProperty("AVAILABLE")
	AVAILABLE,

	@JsonProperty("UN-AVAILABLE")
	UN_AVAILABLE;
}
