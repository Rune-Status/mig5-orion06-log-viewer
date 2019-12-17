public class Player {

	String username;
	boolean isShown;
	
	Player(String username){
		this.username = username;
		this.isShown = true;
	}

	public boolean isShown() {
		return isShown;
	}

	public void setShown(boolean isShown) {
		this.isShown = isShown;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
