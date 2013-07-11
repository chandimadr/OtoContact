package mobileApp.OtoContact.Database;

/**..
 * This class is used to get the number of massage and the get the number of the send of each  message.
 * @author Dileepa Rajaguru
 *
 */
public class DataLoader {
	public static boolean msgCounter=false;
	public static String number="";
	
	
	public static DataLoader dataLd;
	
	
	/**..
	 * This method is used to return the instance of the DataLoader
	 * Singleton design pattern is used
	 * @return
	 */
	public static DataLoader getInstance(){
		if(dataLd!=null)
		{
			return dataLd;
		}
		
		else
		{
			dataLd=new DataLoader();
			return dataLd;
		}
	}

}
