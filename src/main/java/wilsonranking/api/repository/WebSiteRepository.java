package wilsonranking.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wilsonranking.model.Report;
import wilsonranking.model.WebSite;

import java.sql.Date;
import java.util.List;

/**
 * Created by chunwyc on 28/2/2018.
 */
public interface WebSiteRepository extends JpaRepository<WebSite, Long> {
    @Query(value = "SELECT w.HOST, SUM(w.VISIT_COUNT) AS totalVisitCount FROM WEB_SITE w"
            + " LEFT JOIN EXCLUSION ex on (ex.ENABLE IS TRUE and ex.HOST = w.HOST"
            + " and (ex.EXCLUDED_SINCE IS NULL OR w.DATE >= ex.EXCLUDED_SINCE)"
                + " and (ex.EXCLUDED_TILL IS NULL OR w.Date <= ex.EXCLUDED_TILL))"
            + " WHERE ex.ID IS NULL and w.DATE >= ?1 and w.DATE <= ?2"
            + " GROUP BY w.HOST ORDER BY SUM(w.VISIT_COUNT) DESC, w.HOST LIMIT ?3", nativeQuery = true)
    List<Report> findTopWebSites(Date from, Date to, int topCount);

    WebSite findByHostAndDate(String host, Date date);
}
