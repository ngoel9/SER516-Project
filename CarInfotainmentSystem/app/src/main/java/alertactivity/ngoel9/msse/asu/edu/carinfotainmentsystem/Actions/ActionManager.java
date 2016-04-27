package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import ai.api.model.Result;
import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.MainActivity;
import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.R;

public class ActionManager {

    static ActionManager __instance = null;
    static Context context;

    public Switch volume, bluetooth, voice;
    public boolean vol_bool=true, blue_bool=false, voice_bool=true;
    

    Bluetooth b = new Bluetooth();
    Volume v = new Volume();
    Call c = new Call();
    VoiceControl vc = new VoiceControl();
    Screen s;

    private ActionManager(Context con){
        s = new Screen(con);

        voice = (Switch) MainActivity.activity.findViewById(R.id.power_switch);
        bluetooth = (Switch) MainActivity.activity.findViewById(R.id.bluetooth_switch);
        volume = (Switch) MainActivity.activity.findViewById(R.id.mute_switch);

        volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vol_bool = true;
                    v.unMute(context);
                }else{
                    vol_bool = false;
                    v.Mute(context);
                }
            }
        });

        bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    blue_bool = true;
                    b.startBluetooth(context);
                }else{
                    blue_bool = false;
                    b.stopBluetooth(context);
                }
            }
        });

        voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    voice_bool = true;
                    vc.startListening(context);
                }else{
                    voice_bool = false;
                    vc.stopListening(context);
                }
            }
        });

    }

    private ActionManager(){
    }

    public static ActionManager getInstance(Context con){
        if(__instance == null){
            __instance = new ActionManager(con);
        }

        context = con;
        return __instance;
    }

    public Boolean manage(Result result){

        Boolean success = false;

        switch(result.getAction().toLowerCase()){

            case "voice_off":
                voice_bool = false;
                voice.setChecked(false);
                vc.stopListening(context);
                break;

            case "device_on":
                Log.e("DEVICE_ON", "----- " + result.getParameters().get("device").toString() + "-------");
                switch (result.getParameters().get("device").toString().toLowerCase().replaceAll("\"", "")){
                    case "bluetooth":
                        success = b.startBluetooth(context);
                        blue_bool = true;
                        bluetooth.setChecked(blue_bool);
                        break;

                    case "wifi":
                        break;

                    case "music":
                        break;

                    case "brightness":
                        success = s.onScreen();
                        break;

                }
                break;

            case "device_off":
                Log.e("DEVICE_OFF", result.getParameters().get("device").toString());
                switch (result.getParameters().get("device").toString().toLowerCase().replaceAll("\"", "")){
                    case "bluetooth":
                        success = b.stopBluetooth(context);
                        blue_bool = false;
                        bluetooth.setChecked(blue_bool);
                        break;

                    case "wifi":
                        break;

                    case "music":
                        break;

                    case "brightness":
                        success = s.offScreen();
                        break;

                }
                break;

            case "mute":
                success = v.Mute(context);
                vol_bool = false;
                volume.setChecked(vol_bool);
                break;

            case "unmute":
                success = v.unMute(context);
                vol_bool = true;
                volume.setChecked(vol_bool);
                break;

            case "call":
                if(result.getParameters().get("name")==null){
                    return false;
                }
                Log.e("-----", result.getParameters().get("name").toString());
                success = c.callContact(context, result.getParameters().get("name").toString());
                break;


            case "map.directions":
                if(result.getParameters().get("to")==null){
                    return false;
                }
                if(result.getParameters().get("from")==null){
                    return false;
                }
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/" + result.getParameters().get("to") + "/" + result.getParameters().get("from")));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
//                in the button click in main activity
//                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/"));
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                context.startActivity(intent);
//                ----------------------------
                break;

            default:
                Toast.makeText(context, "Command not recognized.", Toast.LENGTH_LONG).show();
                android.util.Log.d(this.getClass().getSimpleName(),"in default--> check grammar or add a case ");
        }

        return success;
    }

    public void turnDefaults(){
        b.stopBluetooth(context);
        v.unMute(context);
        s.onScreen();
    }

}
