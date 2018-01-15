package com.example.mohit.personalassistant;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {




    SpeechToText speechToText ;
    TextToSpeech speech ;
    ImageButton recorderButton ;
    int result ;
    TextView textspeech;
    EditText text ;
    String speechText ;
    Button bspeak ,bstop ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textspeech  = (TextView)findViewById(R.id.speech_text);
        speechToText = new SpeechToText();
        bspeak = (Button)findViewById(R.id.bspeak);
        bstop = (Button)findViewById(R.id.bstop);
        text = (EditText)findViewById(R.id.edittext);
        recorderButton = (ImageButton)findViewById(R.id.imageButton);
        speech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i == TextToSpeech.SUCCESS)
                {
                    result = speech.setLanguage(Locale.UK);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"format is not supported ",Toast.LENGTH_LONG).show();
                }
            }
        });





    }

    public void SpeechText()
    {
        SpeechToText service = new SpeechToText();
        service.setUsernameAndPassword("<username>", "<password>");
        int samplerate = 16000;
        com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat format = new com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat("");

    }


    public void TTS(View view)
    {
        switch (view.getId())
        {
            case R.id.bspeak :
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(getApplicationContext(),"format is not supported ",Toast.LENGTH_LONG).show();
                }
                else {

                    speechText = text.getText().toString();
                    speech.speak(speechText,TextToSpeech.QUEUE_FLUSH,null);
                }

                break;
            case R.id.bstop :
                if (speech != null)
                {
                    speech.stop();
                }
                break;

            case R.id.speechbutton :
                //SpeechText();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RecordSound(View view)
    {
        int sampleRate =10;
        com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat format = new com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat(sampleRate,16,1,true,false);
        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //mediaRecorder.setOutputFile(mfile);


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (speech!=null)
        {
            speech.stop();
            speech.shutdown();
        }
    }
}

