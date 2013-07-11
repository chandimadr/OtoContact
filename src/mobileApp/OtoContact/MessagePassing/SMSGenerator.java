
package mobileApp.OtoContact.MessagePassing;

import mobileApp.OtoContact.MainProcess.OtoContactApp;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;



/**..
 * This class is for generating SMS
 * @author Dileepa Rajaguru
 *
 */

public class SMSGenerator {

	
	/**..
	 * This method is used to send the SMS to any number
	 * @param phoneNumber
	 * @param message
	 * @param activity
	 */
	public void sendSMS(String phoneNumber, String message,Activity activity)         //Pass the phone Number and message body
    {
        PendingIntent pi = PendingIntent.getActivity(activity, 0,new Intent(activity, OtoContactApp.class), 0);                
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);                     //send the SMS
        
    }
	
	
	/**..
	 * This method is used to send the Contact number searched as reply for the request
	 * @param phoneNumber
	 * @param message
	 * @param activity
	 */
	public void sendReply(String phoneNumber, String message,Activity activity)         //Pass the phone Number and message body
	{
		String msg="";
		PendingIntent pi = PendingIntent.getActivity(activity, 0,new Intent(activity, OtoContactApp.class), 0);                
        SmsManager sms = SmsManager.getDefault();
        msg="rOtoCont "+message;
        sms.sendTextMessage(phoneNumber, null, msg, pi, null);                     //send the SMS
	}
}
