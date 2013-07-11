package mobileApp.OtoContact.Secondparty;

import mobileApp.OtoContact.ContactSearch.ContactSearcher;
import mobileApp.OtoContact.MainProcess.R;
import mobileApp.OtoContact.MainProcess.R.id;
import mobileApp.OtoContact.MainProcess.R.layout;
import mobileApp.OtoContact.MessagePassing.SMSGenerator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**..
 * This method is used to search  the contact number at  the second party
 * It generates the message after the searching.
 * @author Chandima Dileepa
 *
 */
public class SearchingView extends Activity{
	
	EditText contactName;
	Button btnExit;
	
	
	/**..
	 * This method load the layout and inistialize the variables.
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        contactName = (EditText) findViewById(R.id.searchingName);
        btnExit=(Button)findViewById(R.id.btnSearchingExit);
        

        
		Intent it = getIntent();
        String searchingName = it.getExtras().getString("reqname");
        String replyNum=it.getExtras().getString("replyNum");
        contactName.setText(searchingName);
        
        ContactSearcher cs=new ContactSearcher();
        String reqNumber=cs.searchContact(searchingName, this);               //Search the Contact
        if(!(reqNumber.equals("")))
        {
        	if(reqNumber.charAt(0)=='#')
            {
            	reqNumber=reqNumber.substring(1, reqNumber.length());
            }
            else
            {
            	reqNumber="";
            }
        }
        
        
        SMSGenerator toSeacher=new SMSGenerator(); 
        if(reqNumber.equals(""))
        {
        	 toSeacher.sendReply(replyNum, searchingName+"@"+"Not Found", this);         //generate the SMS
        }
        else
        {
        	toSeacher.sendReply(replyNum, searchingName+"@"+reqNumber, this);           //generate the SMS
        }
        
        finish();
        
	}
	
	
}
