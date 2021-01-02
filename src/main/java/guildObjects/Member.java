package guildObjects;

public class Member {
	
	public long getID (String rawString) {
		System.out.println(rawString);
		int length = rawString.length();
		int startInd = length - 19;
		int endInd = length - 1;
		String tempOutput = rawString.substring(startInd,endInd);
		System.out.println("Temp id string " + tempOutput);
		long id = Long.parseLong(tempOutput);
		return id;
	}
	
}