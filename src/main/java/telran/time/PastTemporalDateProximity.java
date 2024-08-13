package telran.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class PastTemporalDateProximity implements TemporalAdjuster {
    Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.temporals = java.util.Arrays.copyOf(temporals, temporals.length);
        java.util.Arrays.sort(this.temporals, this::copareTemporal);
    }    

    private int copareTemporal(Temporal t1, Temporal t2) {
        return Long.compare(betweenDays(t1, t2), 0);
    }

    private long betweenDays(Temporal t1, Temporal t2) {
        return ChronoUnit.DAYS.between(LocalDate.from(t2), LocalDate.from(t1));
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal resultTemporal = null;
        int index = getIndexOfPastTemporalDateProximity(temporal);
        if ( index >= 0 && index < temporals.length) {
            resultTemporal = temporals[index];
            long daysBetweenTemporals = betweenDays(resultTemporal, temporal);
            resultTemporal = temporal.plus(daysBetweenTemporals, ChronoUnit.DAYS);
        }
        return resultTemporal;
    }

    private int getIndexOfPastTemporalDateProximity(Temporal temporal) {
        int start = 0;
        int finish = temporals.length - 1;
        int middle = start + (finish - start) / 2;
        while (start <= finish) {
            if (copareTemporal(temporals[middle], temporal) >= 0) {
                finish = middle - 1;
            } else {
                start = middle + 1;
            }
            middle = start + (finish - start) / 2;
        }
        return finish;
    }
}
