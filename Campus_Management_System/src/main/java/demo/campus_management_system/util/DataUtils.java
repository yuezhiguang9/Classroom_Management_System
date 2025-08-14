package demo.campus_management_system.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DataUtils {
    public static void main(String[] args) {
        System.out.println(DataUtils.getStartOfCurrentWeek() + "\n");
        System.out.println(DataUtils.getEndOfToday());

    }


    //获取本周一的00:00:00
    public static LocalDateTime getStartOfCurrentWeek() {

        return LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay(); // 注意：atStartOfDay() 是 LocalDate 的方法

    }

    //获取当前日期结束时间（23:59:59:999）
    public static LocalDateTime getEndOfToday() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }


}
