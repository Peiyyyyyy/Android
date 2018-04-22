package com.example.peiyu.mediaplayer;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "myTag";
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button start, pause, stop;
    private TextView playingTime, totalTime, name;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private ImageView coverImage, loop;
    private ImageView v;
    private String s;

    private boolean isChanging=false;

    public void onClick(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.name1:
                        s = "米津玄師 - Lemon";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.lemon));
                        break;
                    case R.id.name2:
                        s = "DAOKO,米津玄師 - 打上花火";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.hanabi));
                        break;
//                    case R.id.name3:
//                        s = "レミオロメン - 3月9日";
//                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.marnineth));
//                        break;
                    case R.id.name4:
                        s = "レミオロメン - 粉雪";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                        break;
                    case R.id.name5:
                        s = "Goose house - 恋はヒラひらり";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.goosehouse));
                        break;
                    case R.id.name6:
                        s = "周杰伦 - 告白气球";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.gaobaiqiqui));
                        break;
                    case R.id.name7:
                        s = "花澤香菜 - sweets parade";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.sweetsparade));
                        break;
                    case R.id.name8:
                        s = "Ed Sheeran - Shape of You";
                        coverImage.setImageDrawable(getResources().getDrawable(R.drawable.shapeofyou));
                        break;
                }
                name.setText(s);
                return false;
            }
        });
        popup.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.v(TAG, "播放完成即停止");
            }
        });

        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        loop = (ImageView) findViewById(R.id.loop);
        coverImage = (ImageView) findViewById(R.id.coverImage);

        name = (TextView) findViewById(R.id.name);
        playingTime = (TextView) findViewById(R.id.playingTime);
        totalTime = (TextView) findViewById(R.id.totalTime);
        loop.setEnabled(false);
        pause.setEnabled(false);
        stop.setEnabled(false);

        v = findViewById(R.id.pop_list);
        v.setOnClickListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int duration = mediaPlayer.getDuration() / 1000;
                //获取音乐当前播放的位置
               int position = mediaPlayer.getCurrentPosition();
                //开始时间
                playingTime.setText(calculateTime(position / 1000));
                //结束时间
                totalTime.setText(calculateTime(duration));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isChanging = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                isChanging=false;
            }
        });

        //开始播放资源文件
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s == null)
                    Toast.makeText(MainActivity.this, "没有可播放的音乐", Toast.LENGTH_LONG).show();
                else {
                    try {
                        Log.v(TAG, "开始播放");
                        mediaPlayer.reset();
                        AssetManager assetManager = getAssets();
                        AssetFileDescriptor assetFileDescriptor =
                                assetManager.openFd(s + ".mp3");
                        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                                assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                seekBar.setMax(mediaPlayer.getDuration());
                mTimer = new Timer();
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if(isChanging == true) return;
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                };


                mTimer.schedule(mTimerTask, 0, 10);
                mediaPlayer.start();
                pause.setEnabled(true);
                stop.setEnabled(true);
                loop.setEnabled(true);
            }
        });

        //暂停播放
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    pause.setBackground(getResources().getDrawable(R.drawable.go));
                    mediaPlayer.pause();
                } else {
                    pause.setBackground(getResources().getDrawable(R.drawable.pause));
                    mediaPlayer.start();
                }
            }
        });

        //停止播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.stop();
                }
            }
        });

        //循环播放
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Looping");
                boolean loop = mediaPlayer.isLooping();
                mediaPlayer.setLooping(!loop);
                if(!loop)
                    Toast.makeText(MainActivity.this, "循环播放", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "一次播放", Toast.LENGTH_LONG).show();
            }
        });
    }

    //计算播放时间
    public String calculateTime(int time) {
        int minute;
        int second;

        if (time >= 60) {
            minute = time / 60;
            second = time % 60;

            //分钟在0-9
            if(minute>=0&&minute<10)
            {
                //判断秒
                if(second>=0&&second<10)
                {
                    return "0"+Integer.toString(minute)+":"+"0"+Integer.toString(second);
                }else
                {
                    return "0"+Integer.toString(minute)+":"+Integer.toString(second);
                }

            }else
            //分钟在10以上
            {
                //判断秒
                if(second>=0&&second<10)
                {
                    return Integer.toString(minute)+":"+"0"+Integer.toString(second);
                }else
                {
                    return Integer.toString(minute)+":"+Integer.toString(second);
                }
            }

        } else if (time < 60) {
            second = time;
            if(second>=0&&second<10)
            {
                return "00:"+"0"+Integer.toString(second);
            }else
            {
                return "00:" + Integer.toString(second) ;
            }

        }
        return null;
    }
}
