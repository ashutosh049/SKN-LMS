package skn.lms.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum FabricSortByParameter {
  @JsonProperty("ID")
  ID("fabricId"),

  @JsonProperty("FABRIC_CODE")
  FABRIC_CODE("fabricCode"),

  @JsonProperty("FABRIC_STATUS")
  FABRIC_STATUS("status"),

  @JsonProperty("FABRIC_PRICE")
  FABRIC_PRICE("price");

  private String getParameterName;

  FabricSortByParameter(String getParameterName) {
    this.getParameterName = getParameterName;
  }
}
