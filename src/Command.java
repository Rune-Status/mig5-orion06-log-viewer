import com.rs2.model.Position;


public class Command {

	Player player;
	Long time;
	String command;
	Position position;
	
	Command(Player player, Long time, String command, int x, int y, int z){
		this.player = player;
		this.time = time;
		this.command = command;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}
