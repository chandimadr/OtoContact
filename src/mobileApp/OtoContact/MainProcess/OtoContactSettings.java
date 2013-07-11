package mobileApp.OtoContact.MainProcess;

import java.util.ArrayList;
import java.util.List;





import mobileApp.OtoContact.ContactSearch.ContactSearcher;
import mobileApp.OtoContact.Database.DataHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**..
 * This class is used to change the settings of the application. Contact sharing person can be changed using this UI.
 * @author Dileepa Rajaguru
 *
 */
public class OtoContactSettings extends Activity{

	Button btnExit;
	Button btnSave;
	EditText txtNameSharer;
	static DataHelper dh;
	TextView txtSharer;
   
	
	
	/**..
	 *  This method load the layout and inistialize the variables.
	 *  Listner are used for 'Save' and 'Exit' buttons.
	 */
	public void onCreate(Bundle savedInstanceState){
		
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
	        
	        btnExit=(Button)findViewById(R.id.btnExit);
	        btnSave=(Button)findViewById(R.id.btnSave);
	        txtNameSharer=(EditText)findViewById(R.id.txtNameSharer);
            txtSharer=(TextView)findViewById(R.id.txtCurrentSharer);
	        
	        this.dh=new DataHelper(this);
	        List<String> sharerList=new ArrayList<String>();
	        sharerList=dh.getContactData();
	        String currentSharer=sharerList.get(0);
	        txtSharer.setText("Current Sharer : "+currentSharer);
	        
	        
	        btnExit.setOnClickListener(new View.OnClickListener() {               //Click listener for the Exit button
	            public void onClick(View view) {
	            	
	                finish();
	            }

	        });
	        
	        
	        btnSave.setOnClickListener(new View.OnClickListener() {                //Click listener for the Save button
	            public void onClick(View view) {
	            	
	            	String nameOfSharer=txtNameSharer.getText().toString();
	            	
	            	invokeSearching(nameOfSharer);
	            	
	            }

	        });

	}
	
	/**..
	 * This method is used to see that whether the contact name entered as the sharer exists or not.
	 * If the contact name exits is written to the database.
	 * @param name
	 */
	private void invokeSearching(String name)
	{
		ContactSearcher searcher=new ContactSearcher();
		String numberOfSharer=searcher.searchContact(name, this);           //Search the contact
		
		
		
		if(numberOfSharer.equals(""))
		{
			Toast.makeText(getBaseContext(),"Not Available",Toast.LENGTH_SHORT).show();
		}
		
		else
		{
			
			if(numberOfSharer.charAt(0)=='#')
			{
				numberOfSharer=numberOfSharer.substring(1,numberOfSharer.length());
			}
			
			
			List<String> contactList=new ArrayList<String>();
			contactList=dh.getContactData();
			
			
			if(contactList.size()!=0)
			{
				dh.delete(contactList.get(0));
				dh.addContact(name,numberOfSharer);
			}
			
			else
			{
				dh.addContact(name,numberOfSharer);                        //add entry to the database
			}
			
			finish();
			
		}
		
	}
	
	
	
	
	
	
	
}
