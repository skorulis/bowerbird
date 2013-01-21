package bowerbird.server;

public class ServerError {

	public String message;
	
	public static ServerError missingParam(String param) {
		ServerError error = new ServerError();
		error.addMissingParamMessage(param);
		return error;
	}
	
	public ServerError() { }
	
	public void addMissingParamMessage(String param) {
		message = "Missing parameter '" + param + "'";
	}
	
}
