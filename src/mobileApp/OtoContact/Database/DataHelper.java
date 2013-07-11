package mobileApp.OtoContact.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**..
 * This class is used to create and access the database.
 * @author Dileepa Rajagur
 *
 */
public class DataHelper extends SQLiteOpenHelper {

static final String dbName="Database6";
	
	static final String contactTable="ContactListTable";
	static final String contactName="Name";
	static final String contactNo="ContactNo";
	static final String message="Message";
	static final String birthday="Birthday";
	private SQLiteDatabase db;
	private static final String TAG="Data";

	static final String viewEmps="ViewEmps";
	/**..
	 * This method create the database if it doesn't exist.
	 * @param context
	 */
	public DataHelper(Context context){
		super(context, dbName, null, 1);
		
	}

	/**..
	 * This method creates the required table to store the data of the contact sharer.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {		
		// TODO Auto-generated method stub
		 db.execSQL("CREATE TABLE "+contactTable+" ("+contactName+ " TEXT PRIMARY KEY , "+
				    contactNo+ " TEXT)");
		 
		 this.addContact("User", "Null");
	}
	
	/**..
	 * this method delete the database
	 * @return 
	 */
	public int deleteAll(){
		 return db.delete(contactTable, null, null);
		}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+contactTable);
		 
		  
		 
		  onCreate(db);
		
	}
	
	/**..
	 * This method return the contact details of the contact sharer.It returns  the contact name and the contact number.
	 * @return List contactList
	 */
	 public List<String> getContactData(){
		 List<String> all=new ArrayList<String>();
		 List<String> mes=new ArrayList<String>();
		 List<String> con=new ArrayList<String>();
		 SQLiteDatabase db = getReadableDatabase();
		              
		    Cursor cursor = db.query(contactTable, new String[] {"Name","ContactNo"},null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		         do {
		             con.add(cursor.getString(0)); 
		        	 mes.add(cursor.getString(1));
		        	 
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      all.addAll(con);
		      all.addAll(mes);
		      return all;
	 }
	 
	 
	 /**..
	  * This method is used to get the contact name of the contact sharer.
	  * @return List names
	  */
	 public List<String> getData(){
		 List<String> names=new ArrayList<String>();
		 
		 SQLiteDatabase db = getReadableDatabase();
		               
		    Cursor cursor = db.query(contactTable, new String[] {"ContactNo"},null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		         do {
		          
		        	 names.add(cursor.getString(0));
		        
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      return names;
	 }
	
	/**..
	 * This method delete the entry of the table given by the name
	 * @param name
	 */
	 public void delete(String name)
	 {
	 String[] args={name};
	 getWritableDatabase().delete(contactTable, "Name=?", args);
	 } 
		
	 
	public String[] cursorToStringArray(Cursor cursor, String ColomnName){
		
    	 ArrayList<String> TempArrayList= new ArrayList<String>();
    	 
    	if (cursor.moveToFirst()){
    	   do{
    	      String data = cursor.getString(cursor.getColumnIndex(ColomnName));
    	      TempArrayList.add(data);
    	      System.out.println("taken from database :"+data);
    	      
    	      
    	   }while(cursor.moveToNext());
    	}
    	
    	cursor.close();
    	String[] tempStringArray= new String[TempArrayList.size()];
    	int temp=0;
    	while(!TempArrayList.isEmpty()){
    		tempStringArray[temp]=TempArrayList.remove(0);
    		temp++;
    	}
    	return tempStringArray;
    	
	}
	
	
	/**..
	 * This method is used to add a entry to the table. It adds the contact name and the contact number of the sharer.
	 * @param name
	 * @param contactno
	 * @return
	 */
	public boolean addContact(String name, String  contactno){  //add contact details to the database
		try{
			 SQLiteDatabase db= getWritableDatabase();    //get writable database
			 ContentValues cv= new ContentValues();
			 	cv.put(contactName, name);
			 	cv.put(contactNo, contactno);
	        	db.insert(contactTable, null, cv);  //insert into the database
	        	db.close();
	        	return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}	



}
