package tby.com.beans;

import org.json.JSONObject;

import tby.com.sql.sqlOfGift;

public class shopingGifts {
	private int numGood;
	private long accountGood;
	public int getNumGood() {
		return numGood;
	}
	public void setNumGood(int numGood) {
		this.numGood = numGood;
	}
	public long getAccountGood() {
		return accountGood;
	}
	public void setAccountGood(long accountGood) {
		this.accountGood = accountGood;
	}
	public JSONObject getTheInfo(){
		JSONObject js=new JSONObject();
		String getinfo=sqlOfGift.getGiftInfo(accountGood+"");
		
		return js;
	}
}
