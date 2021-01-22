package interfaces;
//Holds attributes needed to allow file scanner to load data files.
public interface Loadable {
	public final String FILE_COMMENT = "##";
	public final String LINUX_DATA_PATH = "/media/the_tech_owl/DATA/Documents/GitHub/BitOwlBot/data/";
	public final String WINDOWS_DATA_PATH = "D:\\Documents\\GitHub\\BitOwlBot\\data\\";
	
	public String GET_DATA_PATH();
}
