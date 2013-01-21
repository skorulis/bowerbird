package bowerbird.server;

public class APIResult {

	public Object data;
	public String result;
	
	public APIResult(Object data) {
		this.data = data;
		this.result = "SUCCESS";
	}
	
}
