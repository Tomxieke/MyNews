package com.example.scxh.mynews.radio_type_news.vidao_content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.uitl.StrDataInter;

public class VidaoPlayActivity extends Activity {
    private VideoView mVideoView;
    private String videoUrl;   //视频地址
    private String videoTitle;  //视频标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidao_play);
        Intent intent = getIntent();
        if (intent != null){
            videoUrl = intent.getStringExtra(StrDataInter.VIDEO_URL);
            videoTitle = intent.getStringExtra(StrDataInter.VIDEO_TITLE);
        }

        initView();
    }

    public void initView(){
        mVideoView = (VideoView) findViewById(R.id.vd_videoview);
        Log.i("tag","-----setVideoPath---" + videoUrl);
        MediaController controller = new MediaController(this);
        Log.i("tag","-----controller.isShowing();---" + controller.isShowing());

        mVideoView.setMediaController(controller);
        mVideoView.setVideoPath(videoUrl);
        mVideoView.requestFocus();
        mVideoView.start();

        /*//播放完成后调用这个方法。
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });*/

    }
}
