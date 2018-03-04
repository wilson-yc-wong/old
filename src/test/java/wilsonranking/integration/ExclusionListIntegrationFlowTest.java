package wilsonranking.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chunwyc on 4/3/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExclusionListIntegrationFlow.class)
@ContextConfiguration(classes={ContextConfigMock.class, ExclusionListIntegrationFlow.class})
@PropertySource("classpath:app-test.properties")
public class ExclusionListIntegrationFlowTest {

    @Test
    public void testLoadExclusionList() throws Exception {
        Thread.sleep(2000);
    }
}
