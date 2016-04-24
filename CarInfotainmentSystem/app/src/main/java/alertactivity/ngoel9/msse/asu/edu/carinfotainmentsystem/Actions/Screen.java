package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.MainActivity;

/**
 * Created by kadam on 4/23/2016.
 */
public class Screen {
    float brightness = 255;  // declare this variable in the mainactivity
    ContentResolver cResolver;  // // declare this variable in the mainactivity
    Window window;  // // declare this variable in the mainactivity
    WindowManager.LayoutParams layoutpars;
    ///<uses-permission android:name="android.permission.WRITE_SETTINGS"/> // put this in the menifest

    public Screen(Context context) {
        cResolver = context.getContentResolver(); // put this on the OnCreate
        window = MainActivity.activity.getWindow(); /// put this on the OnCreate
    }
    public boolean offScreen() {
        if(brightness >= 0) {
            layoutpars = window.getAttributes();
            brightness = 0;
            layoutpars.screenBrightness = brightness;
            window.setAttributes(layoutpars);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean onScreen() {
        if(brightness <= 255) {
            layoutpars = window.getAttributes();
            brightness = 255;
            layoutpars.screenBrightness = brightness;
            window.setAttributes(layoutpars);
            return true;
        }
        else {
            return false;
        }
    }

}
