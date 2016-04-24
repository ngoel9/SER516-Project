package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.drawable.Drawable;
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
import alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem.Actions.ActionManager;

import com.google.gson.JsonElement;
import java.util.Map;
import java.util.jar.Manifest;
import android.media.AudioManager;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AIListener {

    private Button listenButton;
    int MY_PERMISSIONS_REQUEST_MIC = 1;
    private AIService aiService;
    public Switch volume, bluetooth, voice;
    public Button call, screen;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        listenButton = (Button) findViewById(R.id.in_btn);
//        listenButton.setBackgroundDrawable(Drawable.createFromPath("@drawable/mic_art"));
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_MIC);

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
        //----------------------------------------------------------------------
    }

    public void listenButtonOnClick(final View view) {
        android.util.Log.d(this.getClass().getSimpleName(),"in btn click");
        aiService.startListening();
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

        ActionManager.getInstance(getApplicationContext()).manage(result);
//        resultTextView.setText("Query:" + result.getResolvedQuery() +
//                "\nAction: " + result.getAction() +
//                "\nParameters: " + parameterString);

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
