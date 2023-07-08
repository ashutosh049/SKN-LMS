package skn.lms.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import skn.lms.datastore.FabricColor;

import java.util.Optional;

@Repository
public interface FabricColorRepository
    extends JpaRepository<FabricColor, Long>, JpaSpecificationExecutor<FabricColor> {

	Optional<FabricColor> findFabricColorByFabricColorCodeEquals(String colorCode);

	boolean existsByFabricColorCodeEquals(String fabricColorCode);

}
