package hitesh.asimplegame;

import android.app.Activity;

public class Question extends Activity {
	private int ID;
	private String QUESTION;
	private String OPTA;
	private String OPTB;
	private String OPTC;
	private String OPTD;
	private String OPTE;

	private String ANSWER;

	public Question() {
		ID = 0;
		QUESTION = "";
		OPTA = "";
		OPTB = "";
		OPTC = "";
		OPTD = "";
		OPTE = "";

		ANSWER = "";
	}

	public Question(String question, String optA, String optB, String optC, String optD, String optE,
			String answer) {
		QUESTION = question;
		OPTA = optA;
		OPTB = optB;
		OPTC = optC;
		OPTD = optD;
		OPTE = optE;

		ANSWER = answer;
	}

	public int getID() {
		return ID;
	}

	public String getQUESTION() {
		return QUESTION;
	}

	public String getOPTA() {
		return OPTA;
	}

	public String getOPTB() {
		return OPTB;
	}

	public String getOPTC() {
		return OPTC;
	}

	public String getOPTD() {
		return OPTD;
	}

	public String getOPTE(){
		return OPTE;
	}

	public String getANSWER() {
		return ANSWER;
	}

	public void setID(int id) {
		ID = id;
	}

	public void setQUESTION(String qUESTION) {
		QUESTION = qUESTION;
	}

	public void setOPTA(String oPTA) {
		OPTA = oPTA;
	}

	public void setOPTB(String oPTB) {
		OPTB = oPTB;
	}

	public void setOPTC(String oPTC) {
		OPTC = oPTC;
	}

	public void setOPTD(String oPTD){
		OPTD = oPTD;
	}

	public void setOPTE(String oPTE){
		OPTE = oPTE;
	}

	public void setANSWER(String aNSWER) {
		ANSWER = aNSWER;
	}

}

