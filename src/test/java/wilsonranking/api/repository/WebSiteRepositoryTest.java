package wilsonranking.api.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wilsonranking.model.Exclusion;
import wilsonranking.model.Report;
import wilsonranking.model.WebSite;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by chunwyc on 1/3/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class WebSiteRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WebSiteRepository repository;

    @Autowired
    private ExclusionRepository exRepository;

    List<WebSite> webSites = null;
    List<Exclusion> exclusions = null;

    @Before
    public void setUp() throws Exception {
        // prepare testing data - WebSite
        webSites = new ArrayList(8);
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(1L), "host1", 1L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(1L), "host2", 2L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(2L), "host3", 3L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(2L), "host4", 4L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(3L), "host1", 5L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(3L), "host2", 6L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(4L), "host3", 7L));
        webSites.add(prepareWebSiteData(LocalDate.now().plusDays(4L), "host4", 8L));

        for(WebSite w : webSites) {
            entityManager.persist(w);
        }

        // prepare testing data - Exclusion
        exclusions = new ArrayList<>(2);
        exclusions.add(prepareExclusionData("host4", LocalDate.now().plusDays(1L), LocalDate.now().plusDays(3L), true));
        exclusions.add(prepareExclusionData("host3", LocalDate.now().plusDays(1L), LocalDate.now().plusDays(4L), false));

        for(Exclusion ex : exclusions) {
            entityManager.persist(ex);
        }
    }

    WebSite prepareWebSiteData(LocalDate date,String host, Long count) {
        WebSite ws = new WebSite();
        ws.setDate(date);
        ws.setHost(host);
        ws.setVisitCount(count);
        return ws;
    }

    Exclusion prepareExclusionData(String host, LocalDate from, LocalDate to, Boolean enable) {
        Exclusion ex = new Exclusion();
        ex.setHost(host);
        ex.setExcludedSince(from);
        ex.setExcludedTill(to);
        ex.setEnable(enable);
        return ex;
    }

    @Test
    public void testFindTopWebSites() throws Exception {
        List<Report> result = repository.findTopWebSites(Date.valueOf(LocalDate.now().minusDays(1L)), Date.valueOf(LocalDate.now().plusDays(4L)), 3);

        Assert.assertNotNull(result);
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getHost(), is("host3"));
        assertThat(result.get(0).getTotalVisitCount().longValue(), is(10L));
        assertThat(result.get(1).getHost(), is("host2"));
        assertThat(result.get(1).getTotalVisitCount().longValue(), is(8L));
        assertThat(result.get(2).getHost(), is("host4"));
        assertThat(result.get(2).getTotalVisitCount().longValue(), is(8L));
    }
}
