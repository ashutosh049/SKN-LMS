package skn.lms.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum SortByOrder {
  @JsonProperty("ASC")
  ASC("ASC"),
  @JsonProperty("DESC")
  DESC("DESC");

  private String getParameterName;

  SortByOrder(String getParameterName) {
    this.getParameterName = getParameterName;
  }
}