package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.MainActivity;
import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.R;

/**
 * Created by tanmay on 4/23/16.
 */
public class VoiceControl {
    Button listenButton;
    public void stopListening(Context context){
        listenButton = (Button) MainActivity.activity.findViewById(R.id.in_btn);
//        listenButton.setEnabled(false);
        listenButton.setVisibility(View.INVISIBLE);
    }

    public void startListening(Context context){
        listenButton = (Button) MainActivity.activity.findViewById(R.id.in_btn);
//        listenButton.setEnabled(true);
        listenButton.setVisibility(View.VISIBLE);
    }

}
