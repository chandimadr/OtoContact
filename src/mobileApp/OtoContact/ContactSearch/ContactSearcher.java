

package mobileApp.OtoContact.ContactSearch;


import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
/**..
 * This class is used to search the contact list and get the required contact number.
 * @author Dileepa Rajaguru
 *
 */
public class ContactSearcher{

	/**..
	 * This method is used to search the contact name in the contact list. If the contact is not available it returns "".
	 * If the contact is public it returns "#phonNum".
	 * in this method accessing note is also done.
	 * @param contactName
	 * @param activity
	 * @return phoneNumber
	 */
	public String searchContact(String contactName,Activity activity)   
	{
		
		String name=""; 
		String phoneNumber="";
		String note="";
		Cursor curs = activity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
		if (curs.getCount() > 0)
		{
			while(curs.moveToNext())
			{
				name = curs.getString(curs.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = curs.getString(curs.getColumnIndex( ContactsContract.Contacts._ID));
				if(contactName.toLowerCase().equals(name.toLowerCase()))                                                                   //Compare the contact name with the available contacts
				{
					if (Integer.parseInt(curs.getString(curs.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)    //Check the contact number is available
					{
						//Query the contact number 
						Cursor phon =activity.getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null);
						while (phon.moveToNext()) 
						{ 
					         phoneNumber = phon.getString(phon.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));                 
					    }
					    phon.close();
					    
					    
					    
					    
					    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
			 	        String[] noteWhereParams = new String[]{contactId, 
			 	 		ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
			 	                Cursor noteCur = activity.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
			 	                if (noteCur.moveToFirst()) { 
			 	                	note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
					    
			 	                } 
					    
					    
					}
					
					
					break;
				}
			}			
		}
		if("Public".toLowerCase().equals(note.toLowerCase()))
		{
			phoneNumber="#"+phoneNumber;
		}
		return phoneNumber;                //return the searched contact number		
	}
}
