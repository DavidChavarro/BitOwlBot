package config.token;
import javax.script.*;
//Contains the methods that runs javascript to get bot token.
public class TokenAccessor {
	private static String token;
	//Accesses Discord bot token from .env file
	
	
			
	public static String getToken() {
		//IMPLEMENT SCRIPT THAT GETS TOKEN FROM .ENV FILE
		try {
			//Create engine manager
			final ScriptEngineManager manager = new ScriptEngineManager();
			//INSTALL JAVASCRIPT FOR GRAALVM, WHICH RECIEVES BOT TOKEN
			final ScriptEngine jsEng = manager.getEngineByName("js");
			jsEng.eval("require('dotnet').config();");
			token = (String) jsEng.get("token");
			System.out.println("/nDiscord bot has retrieved token: " + token);
			System.out.println("WARNING: Delete output from TokenAccessor.java before uploading to GitHub./n");
			return "test";
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ScriptException";
		}
	}
	
}
