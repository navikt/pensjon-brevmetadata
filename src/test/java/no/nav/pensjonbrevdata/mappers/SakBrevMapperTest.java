package no.nav.pensjonbrevdata.mappers;

import no.nav.pensjonbrevdata.mappers.brevdata.BrevdataMapper;
import no.nav.pensjonbrevdata.mappers.sakBrev.SakBrevMapper;
import no.nav.pensjonbrevdata.model.Brevdata;
import no.nav.pensjonbrevdata.model.codes.BrevsystemCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class SakBrevMapperTest {
    private SakBrevMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new SakBrevMapper();
    }

    @Test
    public void kunInfoP1ErInkludertAvRedigerbareDoksysbrev() {
        var brevdataMapper = new BrevdataMapper();

        var alleRedigerBareDoksysBrev = brevdataMapper.getAllBrevAsList().stream()
                .filter(brev -> brev.brevsystem == BrevsystemCode.DOKSYS && brev.isRedigerbart())
                .map(Brevdata::getBrevkodeIBrevsystem)
                .toList();

        for (String sakType: mapper.keySet()) {
            var sakBrev = mapper.map(sakType);

            // ingen redigerbare doksysbrev blir returnert for sak, bortsett fra "INFO_P1"
            var redigerbareDoksysbrevForSak = sakBrev.stream()
                    .filter(alleRedigerBareDoksysBrev::contains)
                    .filter(brev -> !brev.equals("INFO_P1"))
                    .collect(toList());

            assertThat(redigerbareDoksysbrevForSak, is(empty()));
        }
    }
}