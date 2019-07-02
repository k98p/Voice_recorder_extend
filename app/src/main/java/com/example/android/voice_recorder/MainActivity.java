package com.example.android.voice_recorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
//import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.example.android.voice_recorder.R;

public class MainActivity extends AppCompatActivity {

    Button b1, b2, b3, b4;
    MediaPlayer mediaPlayer;
    MediaRecorder mediarecorder;
    String pathsave= null;
    //String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public final int REQUEST_PERMISSION_CODE =1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.record);
        b2 = (Button)findViewById(R.id.stop);
        b3 = (Button)findViewById(R.id.play);
        b4 = (Button)findViewById(R.id.stop_playing);
        b1.setVisibility(VISIBLE);
        b2.setVisibility(INVISIBLE);
        b3.setVisibility(INVISIBLE);
        b4.setVisibility(INVISIBLE);

        final MediaPlayer alpha = mediaPlayer.create(this, R.raw.audio_of_girl_moaning);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_permission()) {
                    pathsave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"AudioRecording.3gp";
                    MediaRecorderReady();
                    try {
                        mediarecorder.prepare();
                        mediarecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    b1.setEnabled(false);
                    b1.setVisibility(INVISIBLE);
                    b2.setEnabled(true);
                    b2.setVisibility(VISIBLE);
                    Toast.makeText(MainActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediarecorder.stop();
                b2.setEnabled(false);
                b3.setEnabled(true);
                b1.setEnabled(true);
                b4.setEnabled(false);
                b1.setVisibility(VISIBLE);
                b2.setVisibility(INVISIBLE);
                b3.setVisibility(VISIBLE);
                b4.setVisibility(INVISIBLE);


                Toast.makeText(MainActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                b2.setEnabled(false);
                b1.setEnabled(true);
                b4.setEnabled(true);
                b3.setEnabled(false);
                b1.setVisibility(VISIBLE);
                b2.setVisibility(INVISIBLE);
                b3.setVisibility(INVISIBLE);
                b4.setVisibility(VISIBLE);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(pathsave);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setEnabled(false);
                b1.setEnabled(true);
                b4.setEnabled(false);
                b3.setEnabled(true);

                b1.setVisibility(VISIBLE);
                b2.setVisibility(INVISIBLE);
                b3.setVisibility(VISIBLE);
                b4.setVisibility(INVISIBLE);
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });
    }

    public void MediaRecorderReady(){
        mediarecorder=new MediaRecorder();
        mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediarecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediarecorder.setOutputFile(pathsave);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode==REQUEST_PERMISSION_CODE){
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (RecordPermission && StoragePermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Permission_Denied", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
    public boolean check_permission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}













