package wilsonranking.integration;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wilsonranking.api.repository.WebSiteRepository;

import java.io.File;

/**
 * Created by chunwyc on 3/3/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSiteStatisticsFileIntegrationFlow.class)
@ContextConfiguration(classes={ContextConfigMock.class, WebSiteStatisticsFileIntegrationFlow.class})
@PropertySource("classpath:app-test.properties")
public class WebSiteStatisticsFileIntegrationFlowTest {

    @Autowired
    private WebSiteRepository webSiteRepository;

    @Test
    public void testProceedNewCsvFile() throws Exception{
        // prepare test data
        String rootPath = this.getClass().getResource("/").getFile();
        File template = new File(rootPath + "/statistics_data/test_case_proceedNewCsvFile.txt");
        File testFile = new File(rootPath + "/statistics_data/db_init.csv");
        if(testFile.exists()) { testFile.delete(); }
        FileUtils.copyFile(template, testFile);

        Thread.sleep(3000);
    }
}
