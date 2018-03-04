package wilsonranking.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by chunwyc on 3/3/2018.
 */
public class WebSiteStatisticsData {
    LocalDate date;
    String host;
    Long visitCount;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String text) {
        date = LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }
}
