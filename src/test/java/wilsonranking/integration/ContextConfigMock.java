package wilsonranking.integration;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import wilsonranking.api.repository.WebSiteRepository;
import wilsonranking.api.service.WebVisitCountService;

/**
 * Created by chunwyc on 4/3/2018.
 */
@Configuration
@PropertySource("classpath:app-test.properties")
public class ContextConfigMock {
    @MockBean
    WebSiteRepository repo;
    @Bean
    public WebSiteRepository webSiteRepository(){ return repo; }

    @MockBean
    WebVisitCountService website;
    @Bean
    public WebVisitCountService webVisitCountService(){ return website; }
}
