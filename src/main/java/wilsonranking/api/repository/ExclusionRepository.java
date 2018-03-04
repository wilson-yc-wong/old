package wilsonranking.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import wilsonranking.model.Exclusion;

import java.util.List;

/**
 * Created by chunwyc on 28/2/2018.
 */
public interface ExclusionRepository extends JpaRepository<Exclusion, Long> {
    List<Exclusion> findByEnableTrue();
    @Modifying
    @Query("UPDATE Exclusion SET enable = false WHERE enable = true")
    void disableAll();
}
