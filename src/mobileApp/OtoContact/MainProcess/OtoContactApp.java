

package mobileApp.OtoContact.MainProcess;

import java.util.ArrayList;
import java.util.List;

import mobileApp.OtoContact.ContactSearch.ContactSearcher;
import mobileApp.OtoContact.Database.DataHelper;
import mobileApp.OtoContact.Database.DataLoader;
import mobileApp.OtoContact.MainProcess.R;
import mobileApp.OtoContact.MessagePassing.SMSGenerator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

/**..
 * This class is the class for main UI. This UI gets the contact name and search it and show the result.
 * Using this UI sending SMS is also done.
 * 
 * @author Dileepa Rajaguru
 *
 */
public class OtoContactApp extends Activity {
    /** Called when the activity is first created. */
	
	Button btnSendSMS;             //Declaration of  variables
	EditText txtPhoneNo;
	EditText txtMessage;

	Button btnSearchContact;
	EditText txtContactName;
	EditText txtContactNumber;

	Button btnSettings;
	Button btnExit;
	
	String numOfSharer;
	Cursor cur;
	
	DataLoader dataLd;
	static DataHelper dh;
	
	
	
	/**..
	 * This method load the layout and inistialize the variables.
	 * Button click listeners are used for 'Search','Send' and 'Exit' buttons. 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);                      //Set the activity content from a layout resource
        
        ContentResolver cr = getContentResolver();
		
		cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,null);
		this.dh=new DataHelper(this);
		
		btnSendSMS = (Button) findViewById(R.id.btnSendSMS);      //link the variables with the layout
		txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
		txtMessage = (EditText) findViewById(R.id.txtMessage);
		
		btnSearchContact = (Button) findViewById(R.id.btnSearchContactNum);
		
		btnSettings=(Button)findViewById(R.id.btnSettings);
		btnExit=(Button)findViewById(R.id.btnExit);
		
		txtContactName = (EditText) findViewById(R.id.txtContactName);
		txtContactNumber = (EditText) findViewById(R.id.txtContactNum);
		
		
		
	
		btnSendSMS.setOnClickListener(new View.OnClickListener() {               //Listener for the Send Button to send SMS
			public void onClick(View v) {
				String phoneNo = txtPhoneNo.getText().toString();
				String message = txtMessage.getText().toString();
				if (phoneNo.length() > 0 && message.length() > 0)                  //Validate the inputs
					invokeSendSMS(phoneNo,message);
				else
					Toast.makeText(getBaseContext(),"Please enter both phone number and message.",Toast.LENGTH_SHORT).show();
				
				
			}
		});
		
		
		btnSearchContact.setOnClickListener(new View.OnClickListener() {           //Listener for the Search button
			public void onClick(View v) {
				invokeSearcher();			
			}
		});
		
		
		btnExit.setOnClickListener(new View.OnClickListener() {           //Listener for the Exit button
			public void onClick(View v) {
				finish();		
			}
		});
		
		
		
		
		btnSettings.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent myIntent = new Intent(view.getContext(), OtoContactSettings.class);
				startActivityForResult(myIntent, 100);

			}
    	});
		
		
    }
    
        /**..
         * This method is used to get the intent send by the SmsReceiver
         * It pass the Contact Name and the contact number which received from  the second party
         */
    	@Override
    	protected void onNewIntent(Intent intent) {
    		Log.d("YourActivity", "onNewIntent is called!");

    		String name = intent.getStringExtra("name");
    		String number=intent.getStringExtra("num");
    		if("Not Found".equals(number)){
    			txtContactName.setText(name);
    			txtContactNumber.setText("");
    			Toast.makeText(getBaseContext(),"Number Not Found",Toast.LENGTH_SHORT).show();
    		}
    		
    		else{
    			txtContactName.setText(name);
        		txtContactNumber.setText(number);
    		}
    		
    		super.onNewIntent(intent);
    	} 
    
    /**..
     * Method to call the ContactSearcher and pass the contact name
     */
    private void invokeSearcher()                                
    {
    	String searchingContactName = txtContactName.getText().toString();
    	ContactSearcher cs=new ContactSearcher();
    	
    	
    	if(searchingContactName.length()>0)
    	{
    		String searchResult=cs.searchContact(searchingContactName,this);               //Pass the Contact Number and the activity
    		validateContact(searchResult);
    		dataLd=DataLoader.getInstance();	    		
    	}
		
    	else
    	{
    		Toast.makeText(getBaseContext(),"Please enter the Contact Name.",Toast.LENGTH_SHORT).show();
    	}
		
    }
    
    /**..
     * Method to call to send the SMS
     * @param phoneNum
     * @param message
     */
    private void invokeSendSMS(String phoneNum,String message)   
    {
    	SMSGenerator genSMS=new SMSGenerator();
    	genSMS.sendSMS(phoneNum, message, this);                    //pass the phone number and the message
    	
    	
    }
    
    
    /**..
     * Check the availability of the searched contact
     * @param searchResult
     */
    private void validateContact(String searchResult)             
    {
    	String contactNum="";
    	List<String> listOfSharer=new ArrayList<String>();
    	
    	listOfSharer=dh.getData();
    	numOfSharer=listOfSharer.get(0);
    	
    	
    	if(searchResult.equals(""))
    	{
    		String contactName = txtContactName.getText().toString();
    		SMSGenerator to2dParty=new SMSGenerator();
    	    to2dParty.sendSMS(numOfSharer, "aOtoCont "+contactName, this);                //generate the SMS if the contact number is not available
    	}
    	else
    	{
    		if(searchResult.charAt(0)=='#')
    		{
    			contactNum=searchResult.substring(1, searchResult.length());
    			txtContactNumber.setText(contactNum);  
    		}
    		               
    		
    		else
    		{
    			contactNum=searchResult;
    			txtContactNumber.setText(contactNum);
    		}
    	}
    }
    
}