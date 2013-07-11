
package mobileApp.OtoContact.MessagePassing;

import mobileApp.OtoContact.ContactSearch.ContactSearcher;

import mobileApp.OtoContact.Database.DataLoader;
import mobileApp.OtoContact.MainProcess.OtoContactApp;
import mobileApp.OtoContact.Secondparty.SearchingView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**...
 * This class is to receive the SMS and process the message body
 * @author Dileepa Rajaguru
 *
 */
public class SmsReceiver extends BroadcastReceiver
{
	/**..
	 * This method is called when a message is received
	 * After message is received it check the content and read the number of the sender
	 */
    @Override
    public void onReceive(Context context, Intent intent) //Call when a message is received
    {
                                                           
        Bundle bundle = intent.getExtras();        //get the SMS message passed in
        SmsMessage[] msgs = null;
        String msg = ""; 
        String name="";
        String replyNum="";
        char firstChar;
        DataLoader dataLd=new DataLoader();
        
        if (bundle != null)
        {
            
            Object[] pdus = (Object[]) bundle.get("pdus");                //retrieve the SMS message received
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                msg += "SMS from " + msgs[i].getOriginatingAddress();             //read the message 
                replyNum=msgs[i].getOriginatingAddress();
                msg += " :";
                msg += msgs[i].getMessageBody().toString();
                name=msgs[i].getMessageBody().toString();
                msg += "\n";        
            }
            
            if(name.length()>7)
            {
            	String firstSet=name.substring(0, 8);
                
                
                       
                if("aOtoCont".equals(firstSet))                  //Identifier for the request
                {  
                	name=name.substring(9, name.length());
                	Intent i = new Intent(context, SearchingView.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("reqname", name);
                    i.putExtra("replyNum",replyNum );
                    context.startActivity(i);
                }
                
                else if("rOtoCont".equals(firstSet))           //identifier for the reply
                { 
                	name=name.substring(9, name.length());
                	String array[]=name.split("@");

                	Intent intent2open = new Intent(context, OtoContactApp.class);
                	intent2open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                	intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                	String nameId = "name";
                	String numId="num";
                	intent2open.putExtra(nameId, array[0]);
                	intent2open.putExtra(numId, array[1]);
                	context.startActivity(intent2open);
                	
                	
                }
            }
            
            
        }                         
    }
    

}
