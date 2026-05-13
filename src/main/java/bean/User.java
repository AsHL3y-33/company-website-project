package bean;

public class User implements java.io.Serializable {
	
	private boolean isAuthenticated;
	
	public boolean getisAuthenticated() {
		return isAuthenticated;
	}
	
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

}
