package skn.lms.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
public class CustomPaginator {
  private int pageNo;
  private int itemPerPages;
  private long totalItems;
  private boolean hasNext;
  private boolean hasPrevious;
  private boolean isFirst;
  private boolean isLast;
}
