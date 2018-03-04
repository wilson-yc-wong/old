package wilsonranking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by chunwyc on 28/2/2018.
 */
@Entity
@Table(name = "WEB_SITE", uniqueConstraints={@UniqueConstraint(columnNames={"DATE", "HOST"})})
public class WebSite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Long visitCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date.toLocalDate();
    }

    public void setDate(LocalDate date) {
        this.date = Date.valueOf(date);
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

    @Override
    public String toString() {
        return "WebSite{" +
                "id=" + id +
                ", date=" + date +
                ", host='" + host + '\'' +
                ", visitCount=" + visitCount +
                '}';
    }
}
