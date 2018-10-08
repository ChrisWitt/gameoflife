package gameoflife;

import java.util.Random;

public class GameType {

	boolean[] birth = new boolean[9];
	boolean[] keepAlive = new boolean[9];

	public GameType() {
		Random r = new Random(System.currentTimeMillis());
		
		int birthInt = r.nextInt(511);
		int aliveInt = r.nextInt(511);
		
		for(int i=0; i< birth.length;i++) {
			int check = 1 << i;
			
			birth[i] = (check & birthInt) > 0;
			keepAlive[i] = (check & aliveInt) > 0;
		}
		
	}

	public GameType(boolean[] birth, boolean[] keepAlive) {
		this.birth = birth;
		this.keepAlive = keepAlive;
	}

	@Override
	public String toString() {

		StringBuilder sbkeepAlive = new StringBuilder();
		StringBuilder sbbirth = new StringBuilder();

		for (int i = 0; i < birth.length; i++) {
			if (birth[i]) {
				appendNumber(sbbirth, i);
			}
			if (keepAlive[i]) {
				appendNumber(sbkeepAlive, i);
			}
		}

		return "GameType [type=" + sbkeepAlive.toString() + "/" + sbbirth + "]";
	}

	private void appendNumber(StringBuilder sbbirth, int i) {
		sbbirth.append(i);
		if (i != birth.length-1) {
			sbbirth.append(";");
		}
	}
	
	boolean keepAlive(int neighbours) {
		return keepAlive[neighbours];
	}
	
	boolean birth(int neighbours) {
		return birth[neighbours];
	}

}
