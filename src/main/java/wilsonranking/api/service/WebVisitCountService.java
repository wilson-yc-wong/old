package wilsonranking.api.service;

import wilsonranking.model.Exclusion;
import wilsonranking.model.Report;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by chunwyc on 28/2/2018.
 */
public interface WebVisitCountService {
    List<Report> getReportOfTopWebSites(LocalDate from, LocalDate to, int topCount);
    void setExclusion(List<Exclusion> exclusions);
}
