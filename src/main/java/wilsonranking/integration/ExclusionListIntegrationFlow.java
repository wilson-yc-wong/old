package wilsonranking.integration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import wilsonranking.api.service.WebVisitCountService;
import wilsonranking.model.Exclusion;
import wilsonranking.model.ExclusionJson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunwyc on 4/3/2018.
 */
@Configuration
@EnableIntegration
public class ExclusionListIntegrationFlow {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    WebVisitCountService webVisitCountService;

    @Bean(name="exclusionListIntegrationFlowBean")
    public IntegrationFlow httpInternalServiceFlow(@Value("${website.exclusion.watch.polling_crontab}") String pollingCrontab
        , @Value("${website.exclusion.watch.endpoint}") String restEndpoint) {

        return IntegrationFlows.from(this::httpMessageSource,
            e -> e.poller(p -> p.cron(pollingCrontab)))
            .handle(Http.outboundGateway(restEndpoint)
                .charset("UTF-8")
                .httpMethod(HttpMethod.GET)
                .expectedResponseType(String.class))
            .transform(Transformers.fromJson(ExclusionJson[].class))
            .handle(dataHandler())
            .get();
    }

    @Bean(name="exclustionListMessageSourceBean")
    public MessageSource httpMessageSource() {
        return new MessageSource() {
            public Message<String> receive() {
                DefaultMessageBuilderFactory builder = new DefaultMessageBuilderFactory();
                return builder.withPayload("go").build();
            }
        };
    }

    @Bean(name="exclustionListDataHandlerBean")
    public MessageHandler dataHandler() {
        return new AbstractMessageHandler() {
            protected void handleMessageInternal(Message<?> message) throws MessagingException {

                ExclusionJson[] jsonArray = (ExclusionJson[]) message.getPayload();
                List<Exclusion> exlist = new ArrayList<>(jsonArray.length);
                for(ExclusionJson exj : jsonArray) {
                    logger.info("loaded exclusion: " + exj);
                    Exclusion ex = new Exclusion();
                    ex.setEnable(true);
                    ex.setHost(exj.getHost());
                    if(StringUtils.isNotBlank(exj.getExcludedSince())) {
                        ex.setExcludedSince(LocalDate.parse(exj.getExcludedSince(), dtf));
                    }
                    if(StringUtils.isNotBlank(exj.getExcludedTill())) {
                        ex.setExcludedTill(LocalDate.parse(exj.getExcludedTill(), dtf));
                    }
                    exlist.add(ex);
                }
                webVisitCountService.setExclusion(exlist);
            }
        };
    }
}
