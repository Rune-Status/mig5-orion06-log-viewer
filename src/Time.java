import org.joda.time.DateTime;

public class Time {
	
	public static String getDate(long time) {
		DateTime past=new DateTime(time);
		String s = past.getDayOfMonth()+"/"+past.getMonthOfYear()+"/"+past.getYear();
	    return s;
	}
	
	public static String getTimeOfDay(long time) {
		DateTime past=new DateTime(time);
		int h = past.getHourOfDay();
		String h_s = (h < 10 ? "0"+h : ""+h);
		int m = past.getMinuteOfHour();
		String m_s = (m < 10 ? "0"+m : ""+m);
		int s = past.getSecondOfMinute();
		String s_s = (s < 10 ? "0"+s : ""+s);
		String s1 = h_s+":"+m_s+":"+s_s;
	    return s1;
	}
	
	public static long addMinutes(long time1, int minutes) {
		DateTime today=new DateTime(time1);
		today = today.plusMinutes(minutes);
		return today.getMillis();
	}
	
	public static long addHours(long time1, int hours) {
		DateTime today=new DateTime(time1);
		today = today.plusHours(hours);
		return today.getMillis();
	}
	
	public static long addDays(long time1, int days) {
		DateTime today=new DateTime(time1);
		today = today.plusDays(days);
		return today.getMillis();
	}
	
	public static long addWeeks(long time1, int weeks) {
		DateTime today=new DateTime(time1);
		today = today.plusWeeks(weeks);
		return today.getMillis();
	}
	
	public static long addMonths(long time1, int months) {
		DateTime today=new DateTime(time1);
		today = today.plusMonths(months);
		return today.getMillis();
	}
	
	public static boolean isBefore(long time1, long time2){
		DateTime time=new DateTime(time1);
		if(time.isBefore(time2))
			return true;
		return false;
	}
	
	public static boolean isAfter(long time1, long time2){
		DateTime time=new DateTime(time1);
		if(time.isAfter(time2))
			return true;
		return false;
	}
	
	public static boolean isBetween(long time_, long past, long future){
		if(past == 0 || future == 0)
			return true;
		DateTime time=new DateTime(time_);
		if((time.isAfter(past) && time.isBefore(future)) || time.isEqual(past) || time.isEqual(future))
			return true;
		return false;
	}

}
