package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions;

import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by tanmay on 4/23/16.
 */
public class Volume {
    AudioManager manager;

    public Boolean Mute(Context context){
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (manager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            Toast.makeText(context, "Device is already in Muted state.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Toast.makeText(context, "Device Muted.", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    public Boolean unMute(Context context){
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (manager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context, "Device Unmuted.", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(context, " Device is already in Unmuted state. " , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
