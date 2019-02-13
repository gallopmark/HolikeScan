package com.haolaike.hotlikescan.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.haolaike.hotlikescan.R;


/**
 * Created by wqj on 2017/12/5.
 * 播放效果声音工具类
 */

public class SoundPoolUtils {
    // SoundPool对象
    private SoundPool mSoundPool;
    private static SoundPoolUtils soundPoolUtils;
    private int currentSoundID;

    private SoundPoolUtils() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static SoundPoolUtils getInstance() {
        if (soundPoolUtils == null) {
            synchronized (SoundPoolUtils.class) {
                if (soundPoolUtils == null) {
                    soundPoolUtils = new SoundPoolUtils();
                }
            }
        }
        return soundPoolUtils;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        // 初始化声音,设置最多可容纳20个音频流，音频的品质为5
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_SYSTEM);
            SoundPool.Builder builder = new SoundPool.Builder().setMaxStreams(20).setAudioAttributes(attrBuilder.build());
            mSoundPool = builder.build();
        } else {
            mSoundPool = new SoundPool(20, AudioManager.STREAM_SYSTEM, 5);
        }
        mSoundPool.load(context, R.raw.success, 1);  // 1：成功
        mSoundPool.load(context, R.raw.warn, 1);   // 2：警告
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public void play(int soundID) {
        stop();
        currentSoundID = mSoundPool.play(soundID, 1, 1, 0, 0, 1);
    }

    /**
     * 停止播放当前声音
     */
    public void stop() {
        if (currentSoundID != 0) {
            mSoundPool.stop(currentSoundID);
        }
    }
}
