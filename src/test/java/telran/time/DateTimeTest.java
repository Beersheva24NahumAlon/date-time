package telran.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
    @Test
    void LocalDateTest() {
        LocalDate currentLocalDate = LocalDate.now();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now();
        Instant currentInstant = Instant.now();
        LocalTime currentLocalTime = LocalTime.now();
        System.out.printf("Current local date is %s in ISO format\n", currentLocalDate);
        System.out.printf("Current local time is %s in ISO format\n", currentLocalTime);
        System.out.printf("Current local date and time is %s in ISO format\n", currentLocalDateTime);
        System.out.printf("Current zoned date and time is %s in ISO format\n", currentZonedDateTime);
        System.out.printf("Current instant is %s in ISO format\n", currentInstant);
        System.out.printf("Current local date is %s in dd/month/yyyy\n",
                currentLocalDate.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy", Locale.forLanguageTag("en"))));
    }

    @Test
    void nextFriday13Test() {
        LocalDate current = LocalDate.of(2024, 8, 11);
        LocalDate expected = LocalDate.of(2024, 9, 13);
        TemporalAdjuster adjuster = new NextFriday13();
        assertEquals(expected, current.with(new NextFriday13()));
        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

    @Test
    void pastTemporalDateProximityTest() {
        Temporal[] dates = {
                LocalDate.of(2024, 8, 11),
                LocalDate.of(2024, 8, 16),
                JapaneseDate.of(2024, 8, 28),
                LocalDate.of(2024, 8, 4),
                MinguoDate.of(2024, 8, 16)
        };
        assertEquals(LocalDate.of(2024, 8, 4),
                new PastTemporalDateProximity(dates).adjustInto(LocalDate.of(2024, 8, 5)));
        assertEquals(JapaneseDate.of(2024, 8, 11),
                new PastTemporalDateProximity(dates).adjustInto(JapaneseDate.of(2024, 8, 16)));   }

    @Test
    void test() {
        assertTrue(MinguoDate.now().isSupported(ChronoField.MONTH_OF_YEAR));
        assertTrue(MinguoDate.now().isSupported(ChronoField.DAY_OF_MONTH));
        assertTrue(MinguoDate.now().isSupported(ChronoField.YEAR));
        System.err.println(MinguoDate.of(2024, 8, 16));
    }
}