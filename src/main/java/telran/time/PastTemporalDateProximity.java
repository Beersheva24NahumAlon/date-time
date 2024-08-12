package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class PastTemporalDateProximity implements TemporalAdjuster {
    Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        Temporal[] resultTemporals = java.util.Arrays.copyOf(temporals, temporals.length);
        java.util.Arrays.sort(resultTemporals);
        this.temporals = resultTemporals;
    }    

    @Override
    public Temporal adjustInto(Temporal temporal) {
        Temporal resultTemporal = null;
        int index = getIndexOfPastTemporalDateProximity(temporal);
        if ( index >= 0 && index < temporals.length) {
            resultTemporal = temporals[index];
            long daysBetweenTemporals = ChronoUnit.DAYS.between(temporal, resultTemporal);
            resultTemporal = temporal.plus(daysBetweenTemporals, ChronoUnit.DAYS);
        }
        return resultTemporal;
    }

    private int getIndexOfPastTemporalDateProximity(Temporal temporal) {
        int start = 0;
        int finish = temporals.length - 1;
        int middle = start + (finish - start) / 2;
        while (start <= finish) {
            if (temporal.until(temporals[middle], ChronoUnit.DAYS) >= 0) {
                finish = middle - 1;
            } else {
                start = middle + 1;
            }
            middle = start + (finish - start) / 2;
        }
        return start - 1;
    }

    private boolean isSuppurtedFiedlsDMY(Temporal date) {
        return date.isSupported(ChronoField.MONTH_OF_YEAR) &&
                date.isSupported(ChronoField.DAY_OF_MONTH) &&
                date.isSupported(ChronoField.YEAR);
    }
}
