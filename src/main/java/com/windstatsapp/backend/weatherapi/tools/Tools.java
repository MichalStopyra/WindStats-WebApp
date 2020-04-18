package com.windstatsapp.backend.weatherapi.tools;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Tools {

    public static double mph_to_knots(double mph){
        return mph * 0.868976242;
    }

        public static Integer tsToSec8601(String timestamp){
            if(timestamp == null) return null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Date dt = sdf.parse(timestamp);
                long epoch = dt.getTime();
                return (int)(epoch/1000);
            } catch(ParseException e) {
                return null;
            }
        }

        public static int monthStringToInt (String month){
        int input2=0;
        switch(month) {
            case "January":
            case "jan":
                input2 = 1;
                break;

            case "February":
            case "feb":
                input2 = 2;
                break;

            case "March":
            case "mar":
                input2 = 3;
                break;

            case "April":
            case "apr":
                input2 =4;
                break;

            case "May":
                input2 = 5;
                break;

            case "June":
            case "jun":
                input2 = 6;
                break;

            case "July":
            case "jul":
                input2 = 7;
                break;

            case "August":
            case "aug":
                input2 = 8;
                break;

            case "September":
            case "sep":
            case "sept":
                input2 = 9;
                break;

            case "October":
            case "oct":
                input2 = 10;
                break;

            case "November":
            case "nov":
                input2 = 11;
                break;
            case "December":
            case "dec":
                input2 = 12;
                break;
            }
            return input2;
        }

    public static ArrayList<String> DateArr (String month) {
        ArrayList<String> ar = new ArrayList<String>();
        int year=2019;
        int day = 1;
        int monthInt = monthStringToInt(month);//temp
        DateTime DateT = new DateTime(year, monthInt, day, 12, 0);
        Date date;
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy-MM-dd");
        while (DateT.getMonthOfYear() == monthInt) {
            date = DateT.toDate();
            ar.add(ft.format(date));
            DateT = DateT.plusDays(1);
        }
        return ar;
    }

    public static Stream<String> convertListToStream(List<String> list)
    {
        return list.stream();
    }

}
