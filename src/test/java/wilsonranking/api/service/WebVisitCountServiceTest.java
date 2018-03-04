package wilsonranking.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wilsonranking.WilsonApplication;
import wilsonranking.api.repository.ExclusionRepository;
import wilsonranking.model.Exclusion;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by chunwyc on 2/3/2018.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={WilsonApplication.class})
@DataJpaTest
public class WebVisitCountServiceTest {

    @MockBean
    private ExclusionRepository mockExclusionRepo;

    private WebVisitCountService service = new WebVisitCountServiceImpl();

    @Before
    public void setup() throws Exception {
        Field f = service.getClass().getDeclaredField("exRepository");
        f.setAccessible(true);
        f.set(service, mockExclusionRepo);
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
    public void testSetExclusionIgnoreEqualSet() throws Exception {
        // setup mock
        List<Exclusion> mockedEx = new ArrayList<>(2);
        mockedEx.add(prepareExclusionData("host1", null, LocalDate.now(), true));
        mockedEx.add(prepareExclusionData("host2", LocalDate.now(), null, true));
        when(mockExclusionRepo.findByEnableTrue()).thenReturn(mockedEx);

        List<Exclusion> inputExWithReverseOrder = new ArrayList<>(2);
        inputExWithReverseOrder.add(mockedEx.get(1));
        inputExWithReverseOrder.add(mockedEx.get(0));

        service.setExclusion(inputExWithReverseOrder);

        verify(mockExclusionRepo, never()).disableAll();
        verify(mockExclusionRepo, never()).save(any(Exclusion.class));
    }

    @Test
    public void testSetExclusionAcceptDifferentSet() throws Exception {
        // setup mock
        List<Exclusion> mockedEx = new ArrayList<>(2);
        mockedEx.add(prepareExclusionData("host1", null, LocalDate.now(), true));
        mockedEx.add(prepareExclusionData("host2", LocalDate.now(), null, true));
        when(mockExclusionRepo.findByEnableTrue()).thenReturn(mockedEx);
        doNothing().when(mockExclusionRepo).disableAll();
        when(mockExclusionRepo.save(any(Exclusion.class))).thenReturn(null);

        List<Exclusion> differentEx = new ArrayList<>(2);
        differentEx.add(mockedEx.get(0));

        service.setExclusion(differentEx);

        verify(mockExclusionRepo, atLeastOnce()).disableAll();
        verify(mockExclusionRepo, atLeastOnce()).save(any(Exclusion.class));
    }
}
