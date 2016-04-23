//package alertactivity.ngoel9.msse.asu.edu.carinfotainmentsystem;
//
//import android.app.Activity;
//import android.widget.Toast;
//
//import javax.inject.Singleton;
//import javax.inject.Inject;
//
//import ai.api.AIConfiguration;
//import ai.api.model.AIError;
//import ai.api.model.AIResponse;
//import ai.api.model.Result;
//import ai.api.ui.AIDialog;
//
///**
// * Created by nitingoel on 4/23/16.
// */
//
//@Singleton
//public class api_ai_client {
//
//    private final AIDialog aiDialog;
//
//    @Inject
//    public api_ai_client(final Activity activity){
//        AIConfiguration aiConfiguration = new AIConfiguration(
//                "24d82d8c76de4f60b309c55d4fb52257",
//                AIConfiguration.SupportedLanguages.English,
//                AIConfiguration.RecognitionEngine.System
//        );
//
//        aiDialog = new AIDialog(activity, aiConfiguration);
//
//        aiDialog.setResultsListener(new AIDialog.AIDialogListener() {
//            @Override
//            public void onResult(AIResponse result) {
//                try {
//                    if(!result.isError()){
//                        Result res = result.getResult();
//                        switch (res.getAction()){
//                            case  "task.create":
//                                Task task = new Task();
//                                break;
//                            case "task.complete":
//                                //TODO
//                                break;
//                        }
//                    }
//
//                }catch (Exception e){
//
//                }
//
//
//            }
//
//            @Override
//            public void onError(AIError error) {
//
//                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
//                aiDialog.close();
//            }
//
//            @Override
//            public void onCancelled() {
//
//            }
//        });
//
//    }
//
//    public void startRecognition(){
//        aiDialog.showAndListen();
//    }
//}
