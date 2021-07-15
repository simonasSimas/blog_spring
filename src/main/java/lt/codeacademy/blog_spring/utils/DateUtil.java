package lt.codeacademy.blog_spring.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateTimeFormatter dateTimeFormatter;

    public String format(LocalDate localDate) {
        return localDate.format(dateTimeFormatter);
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
