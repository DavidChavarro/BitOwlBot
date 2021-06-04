package interfaces;

import exceptions.NullEnvVarException;

//Holds attributes needed to allow file scanner to load data files.
//NOTE TO DEVELOPER: implement the GET_ENV_PATH() method to the classes implementing
//this interface.
public interface Loadable {
	public final String FILE_COMMENT = "##";
	public final String DATA_ENV_VARIABLE = "BITOWL_DATA";
	public final String HOME_ENV_VARIABLE = "BITOWL_HOME";
	public final String RESOURCES_ENV_VAR = "BITOWL_RESOURCES";
	
	//public String GET_ENV_PATH() throws NullEnvVarException;
}
