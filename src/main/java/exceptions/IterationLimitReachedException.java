package exceptions;

public class IterationLimitReachedException extends Exception {
	private int iterationLimit;
	private String className;
	private String errorOutput;
	
	public IterationLimitReachedException(int iterationLimit, String className) {
		this.iterationLimit = iterationLimit;
		this.className = className;
		errorOutput = "Iteration count exceeded " + iterationLimit + " times at class " + className + ".";
	}
	
	@Override
	public String toString() {
		return errorOutput;
	}
	
}
