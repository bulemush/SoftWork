package Five.src.maines;
import java.time.LocalDate;
public class time {
        /*
         * 用LocalDate获取两个日期之间的天数,可正可负
         */
        public static int betweenDays(LocalDate start, LocalDate end) {

            int sy = start.getYear();
            int ey = end.getYear();
            int days;

            if (sy < ey) {
                days = start.lengthOfYear() - start.getDayOfYear();
                for (int i = sy + 1; i < ey; i++) {
                    days += LocalDate.of(i, 12, 31).lengthOfYear();
                }
                days += end.getDayOfYear();
            } else if (sy == ey) {
                days = end.getDayOfYear() - start.getDayOfYear();
            } else {
                days = end.lengthOfYear() - end.getDayOfYear();
                for (int i = ey + 1; i < sy; i++) {
                    days += LocalDate.of(i, 12, 31).lengthOfYear();
                }
                days += start.getDayOfYear();
                days = -days;
            }
            return days;
        }

        /*
         * 推算多少天以后,可正可负
         */
        public static LocalDate reckonDate(LocalDate date, Integer days) {
            if (days >= 0)
                return date.plusDays(days);
            else
                return date.minusDays(-days);
        }




}
