package wilsonranking.api;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import wilsonranking.WilsonApplication;
import wilsonranking.api.service.WebVisitCountService;
import wilsonranking.model.Report;

import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by chunwyc on 28/2/2018.
 */
@RestController
@RequestMapping("/api")
public class ReportRestController {

    private static Logger logger = LoggerFactory.getLogger(ReportRestController.class.getName());


    @Value("${website.report.max:5}")
    int queryMax;

    @Value("${website.statistics.file.watch.source_folder}")
    String tempFolder;

    @Autowired
    WebVisitCountService webVisitCountService;

    @RequestMapping(value="/admin/init", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> initDatabase() {
        // init DB
        try {
            URI from = WilsonApplication.class.getResource("/statistics_data/db_init.csv").toURI();
            File to = new File(tempFolder + "/db_init.csv");
            logger.info("try to init DB by cloning CSV");
            logger.info("destinating file exist? " + to.exists());
            FileUtils.writeByteArrayToFile(to, IOUtils.toByteArray(from));
            logger.info("destinating file exist after copy? " + to.exists());
        }
        catch(Exception e){ logger.error("fail to init DB by uploading CSV", e); }
        return new ResponseEntity("init db successfully", HttpStatus.OK);
    }

    @RequestMapping(value="/websites/top", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getTopCountHandler(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") final LocalDate from
            , @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") final LocalDate to) {

        if(from==null) {
            throw new IllegalArgumentException("empty query parameter - from");
        }
        if(to==null) {
            throw new IllegalArgumentException("empty query parameter - to");
        }

        List<Report> list = webVisitCountService.getReportOfTopWebSites(from, to, queryMax);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String exceptionHandlerMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        return "invalid request param format";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String exceptionHandlerIllegalArgumentException(final IllegalArgumentException e) {
        return e.getMessage();
    }

}
