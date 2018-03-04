package wilsonranking.integration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import wilsonranking.api.repository.WebSiteRepository;
import wilsonranking.model.WebSite;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by chunwyc on 3/3/2018.
 */
@Configuration
@EnableIntegration
public class WebSiteStatisticsFileIntegrationFlow {
    private static Logger logger = LoggerFactory.getLogger(WebSiteStatisticsFileIntegrationFlow.class.getName());
    private static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String FILE_HANDLER = "website_statstistics_data_handler";

    @Autowired
    WebSiteRepository webSiteRepository;

    @Bean
    public IntegrationFlow inboundFileIntegration(@Value("${website.statistics.file.watch.polling_crontab}") String crontab) {

        return IntegrationFlows.from(fileReadingMessageSource(),
                e -> e.poller(p -> p.cron(crontab)))
            .split(Files.splitter()
                .markers()
                .charset(StandardCharsets.US_ASCII)
                .firstLineAsHeader("fileHeader")
                .applySequence(true))
            .handle(dataHandler())
            .get();
    }

    @Bean
    public FileReadingMessageSource fileReadingMessageSource() {
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.csv"));

        FileReadingMessageSource source = new FileReadingMessageSource();
        File dataDir = getWebsiteStatisticsFileFolder();
        source.setDirectory(dataDir);
        source.setFilter(filters);
        source.setUseWatchService(true);
        source.setWatchEvents(FileReadingMessageSource.WatchEventType.CREATE,
                FileReadingMessageSource.WatchEventType.MODIFY);

        return source;
    }

    @Value("${website.statistics.file.watch.source_folder}")
    String fileSourceFolderPath;
    File getWebsiteStatisticsFileFolder() {
        File f = null;
        if(StringUtils.isNotBlank(fileSourceFolderPath)) {
            try {
                f = new File(new URI(fileSourceFolderPath));
            } catch (Exception e) {
            }
        }
        if(f==null) {
            f = new File(this.getClass().getResource("/").getFile() + "/statistics_data");
        }
        logger.info("watching website statistics file at path - " + f.getAbsolutePath());
        return f;
    }

    @Bean
    public MessageHandler dataHandler() {
        return new AbstractMessageHandler() {
            protected void handleMessageInternal(Message<?> message) throws MessagingException {
                if(message.getPayload() instanceof String && StringUtils.isNotBlank(message.getPayload().toString())) {
                    if(logger.isTraceEnabled()) {
                        logger.trace("website statistics data file body: " + message.getPayload());
                    }
                    String[] data = message.getPayload().toString().split("\\|");
                    WebSite webData = new WebSite();
                    webData.setDate(LocalDate.parse(data[0], dtFormatter));
                    webData.setHost(data[1]);
                    webData.setVisitCount(Long.parseLong(data[2]));

                    // save if not exist
                    WebSite dbData = webSiteRepository.findByHostAndDate(webData.getHost(), Date.valueOf(webData.getDate()));
                    if(dbData==null) {
                        webSiteRepository.save(webData);
                    }
                }
            }
        };
    }
}
