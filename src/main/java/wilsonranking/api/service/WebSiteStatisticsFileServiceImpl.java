package wilsonranking.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by chunwyc on 2/3/2018.
 */
@Service
public class WebSiteStatisticsFileServiceImpl implements FileService {
    private static Logger logger = LoggerFactory.getLogger(WebSiteStatisticsFileServiceImpl.class.getName());

//    @Autowired @Qualifier("websiteStatisticsFileWatcher")
//    FileSystemWatcher websiteStatisticsFileWatcher;
//
//    @Autowired
//    WebVisitCountService service;
//
//    // setup Spring Bean of website statistics file watcher
//    @Value("#{website.statistics.file.watch.polling_interval ?: 30}")
//    long filePollingIntervalInSeconds;
//    @Value("#{website.statistics.file.watch.quiet_period ?: 30}")
//    long fileChangeQuietPeriodInSeconds;
//    @Value("#{website.statistics.file.watch.source_folder ?: classpath:statistics_data}")
//    String fileSourceFolderPath;
//    File getWebsiteStatisticsFileFolder() {
//        File f = null;
//        try{ f = new File(new URI(fileSourceFolderPath)); }
//        catch(URISyntaxException ue) {
//            logger.warn("Fail to parse website statistics file source folder - " + fileSourceFolderPath, ue);
//            f = new File(this.getClass().getResource("statistics_data").getFile());
//        }
//        logger.info("watching website statistics file at path - " + f.getAbsolutePath());
//        return f;
//    }
//    FileSystemWatcher fileWatcher = new FileSystemWatcher(true, filePollingIntervalInSeconds*1000, fileChangeQuietPeriodInSeconds*1000);
//
//    @PostConstruct
//    public void init() {
//        fileWatcher.addSourceFolder(getWebsiteStatisticsFileFolder());
//        fileWatcher.addListener((Set<ChangedFiles> changeSet) -> {
//            changeSet.forEach(fs -> {
//                fs.forEach(f -> {
//                    service.addStaticticsData(f.getFile());
//                });
//            });
//        });
//    }

    @Override
    public void startFileSystemWatcher() {
//        websiteStatisticsFileWatcher.start();
    }

    @Override
    public void stopFileSystemWatcher() {
//        websiteStatisticsFileWatcher.stop();
    }

    @Override
    public void submitFile(File file) {
    }
}
