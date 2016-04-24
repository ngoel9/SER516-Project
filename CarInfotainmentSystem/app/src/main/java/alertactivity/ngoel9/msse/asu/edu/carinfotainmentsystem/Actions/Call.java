package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.MainActivity;

/**
 * Created by tanmay on 4/23/16.
 */
public class Call {


    public Boolean callContact(Context context, String input){

        input = input.replaceAll("\"", "");
        Log.e("Input - Call Contact", input);

//        String input = "bival";

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[] {
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID
        }, ContactsContract.Contacts.HAS_PHONE_NUMBER, null, ContactsContract.Contacts.DISPLAY_NAME);

        cursor.moveToFirst();
        Boolean found = false;
        int position = 0;
        List<String> contactsID = new ArrayList<String>();
        while (cursor.moveToNext()) {
            position += 1;
            contactsID.add(cursor.getString(2));



            if(cursor.getString(1).toLowerCase().contains(input.toLowerCase())){
                Log.e("======", cursor.getString(1));
                found = true;
                break;
            }
        }

        if(!found){
            Toast.makeText(context, "Contact with name " + input + " not found.", Toast.LENGTH_LONG).show();
            return false;
        }

        Cursor cursor1 = context.getContentResolver()
                .query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
                                ContactsContract.CommonDataKinds.Phone.NUMBER,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME },
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { contactsID.get(position - 1) }, null);

        cursor1.moveToNext();


        Log.e("Call Phone:----", cursor1.getString(0));
        Log.e("Call Phone:----", cursor1.getString(1));
        Log.e("Call Phone:----", cursor1.getString(2));

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + cursor1.getString(0)));
        try{
            MainActivity.activity.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
