package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import com.google.gson.JsonElement;

import java.util.Map;
import java.util.jar.Manifest;

import android.media.AudioManager;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Context;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements AIListener, LocationListener {

    private Button listenButton;
    int MY_PERMISSIONS_REQUEST_MIC = 1;
    private AIService aiService;
    private boolean VolIsMute;
    AudioManager manager;
    int currentVolume;
    public Switch volume, bluetooth, voice;
    public Button call, screen;
    LocationManager locationManager;
    public String str = "";
//    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listenButton = (Button) findViewById(R.id.in_btn);
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        listenButton.setBackgroundDrawable(Drawable.createFromPath("@drawable/mic_art"));
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_MIC);

        final AIConfiguration config = new AIConfiguration("c8a74fff9ad9404aa407e41c2733933c",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        //----------------------------------------------------------------------
        voice = (Switch) findViewById(R.id.power_switch);
        bluetooth = (Switch) findViewById(R.id.bluetooth_switch);
        volume = (Switch) findViewById(R.id.mute_switch);
        call = (Button) findViewById(R.id.call_button);
        screen = (Button) findViewById(R.id.screen_btn);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        location=locationManager.getLastKnownLocation(locationManager.)
        //----------------------------------------------------------------------
    }

    public void listenButtonOnClick(final View view) {
        android.util.Log.d(this.getClass().getSimpleName(), "in btn click");
        aiService.startListening();
    }

    //-----------------------ACTION MANAGER--------------------------
    public void action_manager(String action) {
        switch (action) {
            case "device.switch_on":
                Mute();
                break;
            case "device.switch_off":
                unMute();
                break;

            default:
                android.util.Log.d(this.getClass().getSimpleName(), "in default--> check grammar or add a case ");
        }
    }
    //------------------------------------------

    public void screenbtn(View v) {

//        str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        str = "Latitude: "+loc.getLatitude()+" Longitude: "+loc.getLongitude();
//        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/" + "tempe" + "/" + "phoenix" ));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
//        MapView
    }

    @Override
    public void onLocationChanged(Location location) {
//        this.location=location;

         str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();

        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onResult(AIResponse response) {
        android.util.Log.d(this.getClass().getSimpleName(), "in result");
        Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }


        // Show results in TextView.
        android.util.Log.d(this.getClass().getSimpleName(),"Query:" + result.getResolvedQuery() + "\nAction: " + result.getAction() + "\nParameters: " + parameterString);
        this.action_manager(result.getAction());
//        resultTextView.setText("Query:" + result.getResolvedQuery() +
//                "\nAction: " + result.getAction() +
//                "\nParameters: " + parameterString);

    }

    public void Mute(){


        currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume == 0) {
            Toast.makeText(getApplicationContext(),
                    " Volume is " + currentVolume +"Press unmute", Toast.LENGTH_SHORT).show();
        } else {
            isMute();
        }
    }

    public void unMute(){
        currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume == 0) {
            isMute();
        } else {
            Toast.makeText(getApplicationContext(),
                    " Volume is " + currentVolume +"Already muted!!", Toast.LENGTH_SHORT).show();

        }

    }
    public void isMute() {
        if (VolIsMute) {
            //manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            VolIsMute = false;
        } else {
            //  manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            VolIsMute = true;
        }
    }
    @Override
    public void onError(AIError error) {
        android.util.Log.d(this.getClass().getSimpleName(),"on Error API ka:" + error.toString());

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}