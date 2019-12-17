import com.rs2.model.Position;


public class PublicChat {

	Player player;
	Long time;
	String message;
	Position position;
	
	PublicChat(Player player, Long time, String message, int x, int y, int z){
		this.player = player;
		this.time = time;
		this.message = message;
		this.position = new Position(x, y, z);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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
