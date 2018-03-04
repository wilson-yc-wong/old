package wilsonranking.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import wilsonranking.api.service.WebVisitCountService;
import wilsonranking.model.Report;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by chunwyc on 28/2/2018.
 */
@RestController
@RequestMapping("/api/websites")
public class ReportRestController {

    @Value("${website.report.max:5}")
    int queryMax;

    @Autowired
    WebVisitCountService webVisitCountService;

    @RequestMapping(value="/top", method = RequestMethod.GET)
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
