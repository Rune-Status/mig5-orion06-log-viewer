import com.rs2.model.Position;


public class Trade {

	Player player1;
	Player player2;
	Long time;
	Item[] player1Items;
	Item[] player2Items;
	Position player1Position;
	Position player2Position;
	
	Trade(Player player1, int x1, int y1, int z1, Player player2, int x2, int y2, int z2, Long time){
		this.player1 = player1;
		this.player2 = player2;
		this.time = time;
		this.player1Position = new Position(x1, y1, z1);
		this.player2Position = new Position(x2, y2, z2);
	}
	
	Trade(Player player1, int x1, int y1, int z1, Player player2, int x2, int y2, int z2, Long time, Item[] player1Items, Item[] player2Items){
		this.player1 = player1;
		this.player2 = player2;
		this.time = time;
		this.player1Items = player1Items;
		this.player2Items = player2Items;
		this.player1Position = new Position(x1, y1, z1);
		this.player2Position = new Position(x2, y2, z2);
	}

	public Item[] getPlayer1Items() {
		return player1Items;
	}

	public void setPlayer1Items(Item[] player1Items) {
		this.player1Items = player1Items;
	}

	public Item[] getPlayer2Items() {
		return player2Items;
	}

	public void setPlayer2Items(Item[] player2Items) {
		this.player2Items = player2Items;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
