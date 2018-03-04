package wilsonranking.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by chunwyc on 28/2/2018.
 */
@Entity
@Table(name = "EXCLUSION")
public class Exclusion implements Comparable<Exclusion>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    String host;
    @Column(nullable = false)
    Date excludedSince;
    @Column
    Date excludedTill;
    @Column
    Boolean enable;
    @UpdateTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date lastUpdateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public LocalDate getExcludedSince() {
        return excludedSince.toLocalDate();
    }

    public void setExcludedSince(LocalDate excludedSince) {
        if(excludedSince!=null) {
            this.excludedSince = Date.valueOf(excludedSince);
        }
    }

    public LocalDate getExcludedTill() {
        return excludedTill.toLocalDate();
    }

    public void setExcludedTill(LocalDate excludedTill) {
        if(excludedTill!=null) {
            this.excludedTill = Date.valueOf(excludedTill);
        }
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public LocalDate getLastUpdateDate() { return lastUpdateDate.toLocalDate(); }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = Date.valueOf(lastUpdateDate);
    }

    @Override
    public int compareTo(Exclusion other) {
        int cmp = this.getHost().compareTo(other.getHost());
        if(cmp==0) {
            if (this.getExcludedSince() == null) {
                return 1;
            } else if (other.getExcludedSince() == null) {
                return -1;
            } else {
                cmp = (this.getExcludedSince().isAfter(other.getExcludedSince())?1:-1);
            }
        }
        if(cmp==0) {
            if (this.getExcludedTill() == null) {
                return 1;
            } else if (other.getExcludedTill() == null) {
                return -1;
            } else {
                cmp = (this.getExcludedTill().isAfter(other.getExcludedTill())?1:-1);
            }
        }
        return cmp;
    }
}
