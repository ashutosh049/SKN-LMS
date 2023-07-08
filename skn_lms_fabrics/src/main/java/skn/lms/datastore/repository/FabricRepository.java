package skn.lms.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import skn.lms.datastore.Fabric;
import skn.lms.infra.dto.PagedDto;

import java.util.Optional;

@Repository
public interface FabricRepository
    extends JpaRepository<Fabric, Long>, JpaSpecificationExecutor<Fabric> {

	Optional<Fabric> findFabricByFabricCodeEquals(String fabricCode);

}
