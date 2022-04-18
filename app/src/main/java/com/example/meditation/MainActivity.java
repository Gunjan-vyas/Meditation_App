package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timer_sb;
    TextView timer_tv;
    Button  start_btn;
    Button instr_btn;
    CountDownTimer countDownTimer;
    Boolean counterIsActive=false;
    Boolean forInstruction=false;
    MediaPlayer mediaPlayer;
    MediaPlayer mediaSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer_sb=findViewById(R.id.timer_sb);
        timer_tv=findViewById(R.id.timer_tv);
        start_btn=findViewById(R.id.start_btn);
        instr_btn=findViewById(R.id.instr_btn);
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.alarm);
        mediaSecond=MediaPlayer.create(getApplicationContext(),R.raw.intro);
        timer_sb.setMax(600);
        timer_sb.setProgress(60);
        timer_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                update(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                    mediaSecond.start();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void update(int i) {
        int minutes=i/60;
        int seconds=i%60;
        String secondsFinal="";
        if(seconds<=9){
            secondsFinal="0"+seconds;
        }
        else{
            secondsFinal=""+seconds;
        }
        timer_sb.setProgress(i);
        timer_tv.setText(""+minutes+":"+secondsFinal);
    }

    public void start_timer(View view) {
        if(counterIsActive==false){
            counterIsActive=true;
            timer_sb.setEnabled(false);
            start_btn.setText("STOP");
            countDownTimer=new CountDownTimer(timer_sb.getProgress()*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    update((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    mediaSecond.stop();
                    resetInstr();
                    reset();
                    if(mediaPlayer!=null)
                        mediaPlayer.start();

                }
            }.start();
        }else{
            reset();
        }
    }

    private void reset() {
        timer_tv.setText("0:60");
        timer_sb.setProgress(60);
        countDownTimer.cancel();
//        mediaSecond.pause();
        start_btn.setText("START");
        timer_sb.setEnabled(true);
        counterIsActive=false;
        forInstruction=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }

    public void start_instr(View view) {
        if(forInstruction==false){
            mediaSecond.start();
            instr_btn.setText("STOP INSTRUCTION");
            forInstruction=true;
        }
        else{
            resetInstr();
//            mediaSecond.stop();
        }
    }

    private void resetInstr() {
        instr_btn.setText("INSTRUCTION");
        mediaSecond.pause();
        forInstruction=false;

    }
}