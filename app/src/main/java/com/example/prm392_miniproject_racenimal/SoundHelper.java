package com.example.prm392_miniproject_racenimal;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundHelper {
    private static MediaPlayer mediaPlayer;

    public static void startBackgroundMusic(Context context, int musicResId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, musicResId);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
