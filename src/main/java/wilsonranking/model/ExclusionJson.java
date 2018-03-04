package wilsonranking.model;

/**
 * Created by chunwyc on 4/3/2018.
 */
public class ExclusionJson {
    String host;
    String excludedSince;
    String excludedTill;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getExcludedSince() {
        return excludedSince;
    }

    public void setExcludedSince(String excludedSince) {
        this.excludedSince = excludedSince;
    }

    public String getExcludedTill() {
        return excludedTill;
    }

    public void setExcludedTIll(String excludedTIll) {
        this.excludedTill = excludedTIll;
    }

    @Override
    public String toString() {
        return "ExclusionJson{" +
                "host='" + host + '\'' +
                ", excludedSince='" + excludedSince + '\'' +
                ", excludedTIll='" + excludedTill + '\'' +
                '}';
    }
}
