public class UserData
{
	private String ID;
	private String PW;
	
	public String getUserID() {
		return ID;
	}
	public String getUserPW() {
		return PW;
	}
	
	public void setUserID(String s) {
		ID=s;
	}
	public void setUserPW(String s) {
		PW=s;
	}
	
	public UserData(String i, String p) {
		ID = i;
		PW = p;
	}
}
