package com.p020kl.commonbase.utils;

import android.media.AudioAttributes;
import android.media.SoundPool;

/* renamed from: com.kl.commonbase.utils.WarnSoundUtil */
public class WarnSoundUtil {
    private static AudioAttributes audioAttributes;

    public static SoundPool getSingleSoundPool() {
        audioAttributes = new AudioAttributes.Builder().setUsage(1).setContentType(2).build();
        return new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build();
    }
}
