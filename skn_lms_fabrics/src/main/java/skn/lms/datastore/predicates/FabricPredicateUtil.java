package skn.lms.datastore.predicates;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import skn.lms.datastore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@UtilityClass
public class FabricPredicateUtil {

  public static Specification<Fabric> getFabricBySpecification(
      String fabricCode, String colorCode, String materialCode, Set<FabricStatus> fabricStatus) {

    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (hasText(fabricCode)) {
        predicates.add(criteriaBuilder.equal(root.get(Fabric_.FABRIC_CODE), fabricCode));
      }

      if (hasText(colorCode)) {
        Join<Fabric, FabricColor> colorJoin = root.join(Fabric_.FABRIC_COLOR);

        Predicate colorCodeEquals =
            criteriaBuilder.equal(colorJoin.get(FabricColor_.FABRIC_COLOR_CODE), colorCode);

        predicates.add(colorCodeEquals);
      }

      if (hasText(materialCode)) {
        Join<Fabric, FabricMaterial> fabricMaterialJoin = root.join(Fabric_.FABRIC_MATERIALS);
        Join<FabricMaterial, Material> materialJoin =
            fabricMaterialJoin.join(FabricMaterial_.MATERIAL);

        Predicate materialCodeEquals =
            criteriaBuilder.equal(materialJoin.get(Material_.MATERIAL_CODE), materialCode);

        predicates.add(materialCodeEquals);
      }

      if (!isEmpty(fabricStatus)) {
        Set<String> statusSet =
            fabricStatus.stream().map(FabricStatus::name).collect(Collectors.toSet());

        predicates.add(criteriaBuilder.in(root.get(Fabric_.STATUS)).value(fabricStatus));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
