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

    private ActionManager(){

    }

    public static ActionManager getInstance(Context con){
        if(__instance == null){
            __instance = new ActionManager();
        }

        context = con;
        return __instance;
    }

    public Boolean manage(Result result){

        Boolean success = false;

        switch(result.getAction()){

            case "device.switch_on":
                System.out.println("in switch case bluetooth on");
//                Helper.startBluetooth(getApplicationContext());
                Volume vol = new Volume();
                vol.Mute(context);
                break;

            case "device.switch_off":
                System.out.println("in switch case bluetooth on");
//                Helper.stopBluetooth(getApplicationContext());
                Volume vol1 = new Volume();
                vol1.unMute(context);
                break;

            case "call.call":
                if(result.getParameters().get("q")==null){
                    return false;
                }
                Log.e("-----", result.getParameters().get("q").toString());
                Call c = new Call();
                c.callContact(context, result.getParameters().get("q").toString());
                break;

            default:
                android.util.Log.d(this.getClass().getSimpleName(),"in default--> check grammar or add a case ");
        }

        return success;
    }

}
