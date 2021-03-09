package interfaces;
//Holds attributes needed to allow file scanner to load data files.
public interface Loadable {
	public final String FILE_COMMENT = "##";
	public final String LINUX_DATA_PATH = "data/";
	public final String WINDOWS_DATA_PATH = "data\\";
	
	public String GET_DATA_PATH();
}
