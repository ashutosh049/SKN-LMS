package skn.lms.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import skn.lms.datastore.Material;

import java.util.Optional;

@Repository
public interface MaterialRepository
    extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {

  Optional<Material> findMaterialByMaterialCodeEquals(String materialCode);

  boolean existsByMaterialCodeEquals(String materialCode);
}
