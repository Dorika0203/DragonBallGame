public class Motion {
	private int cost;
	private char motionType;
	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public char getMotionType() {
		return motionType;
	}
	public void setMotionType(char motionType) {
		this.motionType = motionType;
	}

	public Motion(int codeNum) {
		this.setCode(codeNum);
		switch (codeNum) {
		//no motion
		case 0:
			this.setCost(0);
			this.setMotionType('N');
			break;
		//collect energy
		case 1:
			this.setCost(0);
			this.setMotionType('E');
			break;
		//punch
		case 2:
			this.setCost(1);
			this.setMotionType('A');
			break;
		//sun punch
		case 3:
			this.setCost(2);
			this.setMotionType('A');
			break;
		//energy wave
		case 4:
			this.setCost(3);
			this.setMotionType('A');
			break;
		//won-ki-ok
		case 5:
			this.setCost(5);
			this.setMotionType('A');
			break;
		//cross arms
		case 6:
			this.setCost(0);
		//close eyes
		case 7:
			this.setCost(0);
		//teleport
		case 8:
			this.setCost(1);
			this.setMotionType('D');
			break;
		//????? error
		default:
			this.setCost(-1);
			this.setMotionType('N');
		}
	}
	
	//input combat motion, get result
	//this is game Logic
	public int battleResult(Motion x) {
		//when I am Attack.
		if(this.getMotionType() == 'A') {
			switch (x.getMotionType()) {
			case 'A':
				if(this.getCost() == x.getCost()) return 0;
				else if(this.getCost() > x.getCost()) return 1;
				else return 2;
			case 'D':
				if(this.getCode() == 5) return 1;
				else if(this.getCode() == 4) {
					if(x.getCode() == 8) return 0;
					else return 1;
				}
				else if(this.getCode() == 3) {
					if(x.getCode() == 7) return 0;
					else return 1;
				}
				else {
					if(x.getCode() == 7) return 1;
					else return 0;
				}
			case 'E':
				return 1;
			default:
				return 1;
			}
		}
		
		//when I am Collecting Energy
		else if(this.getMotionType() == 'E') {
			if(x.getMotionType() == 'A') return 2;
			else if(x.getMotionType() == 'N') return 1;
			else return 0;
		}
		
		//when I am Defense
		else if(this.getMotionType() == 'D') {
			switch (x.getMotionType()) {
			case 'A':
				if(x.getCode() == 5) return 2;
				else if(x.getCode() == 4) {
					if(this.getCode() == 8) return 0;
					else return 2;
				}
				else if(x.getCode() == 3) {
					if(this.getCode() == 7) return 0;
					else return 2;
				}
				else {
					if(this.getCode() == 7) return 2;
					else return 0;
				}
			case 'D':
			case 'E':
				return 0;
			default:
				return 1;
			}
		}
		
		//If I am no Motion
		else if(this.getMotionType() == 'N') {
			if(x.getMotionType() == 'N') return 0;
			else return 2;
		}
		
		else return 2;
	}
}
