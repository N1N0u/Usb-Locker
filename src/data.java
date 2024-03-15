
public class data {
	private String pwd,date,keyString;
	public data() {}
	public data(String p,String d,String k)
	{
		this.pwd=p;
		this.keyString=k;
		this.date=d;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

}
