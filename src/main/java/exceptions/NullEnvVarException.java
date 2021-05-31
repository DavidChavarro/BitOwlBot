package exceptions;

public class NullEnvVarException extends Exception{

	String variableName;
	
	public NullEnvVarException(String variableName) {
		this.variableName = variableName;
	}
	
	public String getVariable() {
		return this.variableName;
	}
	
}
