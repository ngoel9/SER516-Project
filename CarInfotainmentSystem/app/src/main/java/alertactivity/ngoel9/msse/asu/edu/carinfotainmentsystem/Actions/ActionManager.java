package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.content.Context;
import android.util.Log;
import ai.api.model.Result;

/**
 * Created by tanmay on 4/23/16.
 */
public class ActionManager {

    static ActionManager __instance = null;
    static Context context;
    

    Bluetooth b = new Bluetooth();
    Volume v = new Volume();
    Call c = new Call();
    Screen s;

    private ActionManager(Context con){
        s = new Screen(con);


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

                break;

            case "device_on":
                Log.e("DEVICE_ON", "----- " + result.getParameters().get("device").toString() + "-------");
                switch (result.getParameters().get("device").toString().toLowerCase().replaceAll("\"", "")){
                    case "bluetooth":
                        success = b.startBluetooth(context);
                        break;

                    case "wifi":
                        break;

                    case "music":
                        break;

                    case "brightness":
                        success = s.onScreen();
                        break;

                    case "google maps":
                        break;

                }
                break;

            case "device_off":
                Log.e("DEVICE_OFF", result.getParameters().get("device").toString());
                switch (result.getParameters().get("device").toString().toLowerCase().replaceAll("\"", "")){
                    case "bluetooth":
                        success = b.stopBluetooth(context);
                        break;

                    case "wifi":
                        break;

                    case "music":
                        break;

                    case "brightness":
                        success = s.offScreen();
                        break;

                    case "google maps":
                        break;

                }
                break;

            case "mute":
                success = v.Mute(context);
                break;

            case "unmute":
                success = v.unMute(context);
                break;

            case "call":
                if(result.getParameters().get("name")==null){
                    return false;
                }
                Log.e("-----", result.getParameters().get("name").toString());
                success = c.callContact(context, result.getParameters().get("name").toString());
                break;

            default:
                android.util.Log.d(this.getClass().getSimpleName(),"in default--> check grammar or add a case ");
        }

        return success;
    }

}
