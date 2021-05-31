package interfaces;

import exceptions.NullEnvVarException;

//Holds attributes needed to allow file scanner to load data files.
public interface Loadable {
	public final String FILE_COMMENT = "##";
	public final String DATA_ENV_VARIABLE = "BITOWL_DATA";
	
	public String GET_DATA_PATH() throws NullEnvVarException;
}
