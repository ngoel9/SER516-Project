package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AIListener {

    private Button listenButton;
    int MY_PERMISSIONS_REQUEST_MIC = 1;
    private AIService aiService;

    public Button map, screen;


    public static Activity activity;

    float brightness = 255;  // declare this variable in the mainactivity
    ContentResolver cResolver;  // // declare this variable in the mainactivity
    Window window;  // // declare this variable in the mainactivity
    WindowManager.LayoutParams layoutpars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        ActionManager.getInstance(getApplication()).turnDefaults();

        listenButton = (Button) findViewById(R.id.in_btn);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_MIC);

        final AIConfiguration config = new AIConfiguration("e4268f32a7b14b9bbb291de09bc665ed",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        //----------------------------------------------------------------------

        screen = (Button) findViewById(R.id.screen_btn);
        //----------------------------------------------------------------------
    }

    public void listenButtonOnClick(final View view) {
        android.util.Log.d(this.getClass().getSimpleName(), "in btn click");
        aiService.startListening();
    }

    public void screenbtn(View v) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/" + "tempe" + "/" + "phoenix" ));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
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
        android.util.Log.d(this.getClass().getSimpleName(), "Query:" + result.getResolvedQuery() + "\nAction: " + result.getAction() + "\nParameters: " + parameterString);

        ActionManager.getInstance(getApplicationContext()).manage(result);
    }

    @Override
    public void onError(AIError error) {
        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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
