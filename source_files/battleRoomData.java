public class battleRoomData {
	String client1;
	String client2;
	Motion client1Motion;
	Motion client2Motion;
	int peekNum;
	boolean isFull;
	
	public battleRoomData() {
		client1 = "NONE";
		client2 = "NONE";
		client1Motion = new Motion(0);
		peekNum = 0;
		isFull = false;
	}
	
	public void insertMotion(String s, Motion m) {
		if(client1 == "NONE") {
			client1 = s;
			client1Motion = m;
		}
		else if(client2 == "NONE") {
			client2 = s;
			client2Motion = m;
			isFull = true;
		}
	}
	
	public int getResult(String s) {
		if(client1.equals(s)) return client1Motion.battleResult(client2Motion);
		else return client2Motion.battleResult(client1Motion);
	}
	
	public int getCombatMotion(String s) {
		if(client1.equals(s)) return client2Motion.getCode();
		else return client1Motion.getCode();
	}
}
