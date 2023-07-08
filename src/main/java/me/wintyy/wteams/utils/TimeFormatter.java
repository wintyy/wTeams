package me.wintyy.wteams.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    public static String formatTime(long ticks) {
        long milliseconds = TimeUnit.MILLISECONDS.convert(ticks * 50, TimeUnit.MILLISECONDS);
        long seconds = milliseconds / 1000;
        long minutes = (seconds / 60) % 60;
        long hours = seconds / 3600;
        seconds %= 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            long timeleft = milliseconds / 10;
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat nf = new DecimalFormat(timeleft >= 1000 ? "##0.0" : "0.0", symbols);
            String formattedTime = nf.format(timeleft / 100.0);
            return formattedTime;
        }
    }

    public static String formatTimeSec(long sec) {
        long minutes = sec / 60;
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        long remainingSeconds = sec % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, remainingMinutes, remainingSeconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, remainingSeconds);
        } else {
            return remainingSeconds + "s";
        }
    }
}
