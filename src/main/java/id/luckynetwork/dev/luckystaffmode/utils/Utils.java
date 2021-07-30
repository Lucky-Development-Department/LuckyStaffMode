package id.luckynetwork.dev.luckystaffmode.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Utils {

    private final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public String getFormattedDate() {
        return dateFormat.format(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(7L));
    }
}
