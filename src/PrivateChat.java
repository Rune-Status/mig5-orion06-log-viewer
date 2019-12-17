import com.rs2.model.Position;


public class PrivateChat {

	Player from;
	Player to;
	Long time;
	String message;
	Position fromPosition;
	Position toPosition;
	
	PrivateChat(Player from, int x1, int y1, int z1, Player to, int x2, int y2, int z2, Long time, String message){
		this.from = from;
		this.to = to;
		this.time = time;
		this.message = message;
		this.fromPosition = new Position(x1, y1, z1);
		this.toPosition = new Position(x2, y2, z2);
	}

	public Player getFrom() {
		return from;
	}

	public void setFrom(Player from) {
		this.from = from;
	}

	public Player getTo() {
		return to;
	}

	public void setTo(Player to) {
		this.to = to;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
