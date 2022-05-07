package com.example.project.Helper;

public class DateTimeHelper {

    public static String getGeneralDateFormat(int year, int month, int day) {
        String date;

        if(day < 10)
            date = "0" + day + ".";
        else date = day + ".";

        if(month < 10)
            date += "0" + month + ".";
        else date += month + ".";

        return date + year;
    }

    public static String getGeneralTimeFormat(int hours, int minutes) {
        String time;

        if(hours < 10)
            time = "0" + hours + ":";
        else time = hours + ":";

        if(minutes < 10)
            time += "0" + minutes;
        else time += minutes;

        return time;
    }
}
