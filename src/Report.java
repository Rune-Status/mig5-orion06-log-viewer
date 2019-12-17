import com.rs2.model.Position;


public class Report {

	Player reporter;
	Player reported;
	Long time;
	int rule;
	boolean muted;
	Position reporterPosition;
	Position reportedPosition;
	
	Report(Player reporter, int x1, int y1, int z1, Player reported, int x2, int y2, int z2, Long time, int rule, boolean muted){
		this.reporter = reporter;
		this.reported = reported;
		this.time = time;
		this.rule = rule;
		this.muted = muted;
		this.reporterPosition = new Position(x1, y1, z1);
		this.reportedPosition = new Position(x2, y2, z2);
	}
	
	public Position getReporterPosition() {
		return reporterPosition;
	}

	public void setReporterPosition(Position reporterPosition) {
		this.reporterPosition = reporterPosition;
	}

	public Position getReportedPosition() {
		return reportedPosition;
	}

	public void setReportedPosition(Position reportedPosition) {
		this.reportedPosition = reportedPosition;
	}

	public static final String[] reportNames = { "Offensive language",
		"Item scamming", "Password scamming", "Bug abuse",
		"Staff impersonation", "Account sharing/trading", "Macroing", "Multiple logging in",
		"Encouraging others to break rules", "Misuse of customer support",
		"Advertising / website", "Real world item trading", "Asking for personal details" };

	public String ruleIdToName(int id){
		return reportNames[id];
	}
	
	public Player getReporter() {
		return reporter;
	}

	public void setReporter(Player reporter) {
		this.reporter = reporter;
	}

	public Player getReported() {
		return reported;
	}

	public void setReported(Player reported) {
		this.reported = reported;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
