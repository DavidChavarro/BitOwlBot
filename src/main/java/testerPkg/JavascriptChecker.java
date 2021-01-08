package testerPkg;
import javax.script.*;
//import org.graalvm.polygot.*;

public class JavascriptChecker {
//Checks if javascript engine is installed.
	public static void main(String[] args) {
		System.out.println("Printing all installed engines...");
		final ScriptEngineManager manager = new ScriptEngineManager();
		for (final ScriptEngineFactory scriptEngine : manager.getEngineFactories())
		{
		   System.out.println(
		         scriptEngine.getEngineName() + " ("
		       + scriptEngine.getEngineVersion() + ")" );
		   System.out.println(
		         "\tLanguage: " + scriptEngine.getLanguageName() + "("
		       + scriptEngine.getLanguageVersion() + ")" );
		   System.out.println("\tCommon Names/Aliases: ");
		   for (final String engineAlias : scriptEngine.getNames())
		   {
		      System.out.println(engineAlias + " ");
		   }
		}
		System.out.println("Done.");
	}

}
