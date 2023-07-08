package skn.lms.datastore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

  @JsonIgnore
  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private U createdBy;

  @JsonIgnore
  @CreatedDate
  @Column(name = "created_date", updatable = false)
  @Temporal(TIMESTAMP)
  private Date createdDate;

  @JsonIgnore
  @LastModifiedBy
  @Column(name = "last_modified_by")
  private U lastModifiedBy;

  @JsonIgnore
  @LastModifiedDate
  @Column(name = "last_modified_date")
  @Temporal(TIMESTAMP)
  private Date lastModifiedDate;
}
