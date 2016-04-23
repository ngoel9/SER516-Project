package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem;

import android.app.ActionBar;
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
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.jar.Manifest;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements AIListener {

    private Button listenButton;
    int MY_PERMISSIONS_REQUEST_MIC = 1;
    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listenButton = (Button) findViewById(R.id.in_btn);
//        listenButton.setBackgroundDrawable(Drawable.createFromPath("@drawable/mic_art"));
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_MIC);

        final AIConfiguration config = new AIConfiguration("60159738fd294ade8955e8fcd88d3bb8",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }

    public void listenButtonOnClick(final View view) {
        android.util.Log.d(this.getClass().getSimpleName(),"in btn click");
        aiService.startListening();
    }
//-----------------------ACTION MANAGER--------------------------
    public void action_manager(String action){
        switch(action){
            case "turn_on_bluetooth":System.out.println("in switch case bluetooth on");
                break;
            case "turn_off_bluetooth":System.out.println("in switch case bluetooth off");
                break;
            default:
                android.util.Log.d(this.getClass().getSimpleName(),"in default--> check grammar or add a case ");
        }
    }
    //------------------------------------------

    @Override
    public void onResult(AIResponse response) {
        android.util.Log.d(this.getClass().getSimpleName(),"in result");
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
