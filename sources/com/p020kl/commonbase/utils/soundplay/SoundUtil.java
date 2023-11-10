package com.p020kl.commonbase.utils.soundplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.LocaleUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.kl.commonbase.utils.soundplay.SoundUtil */
public class SoundUtil {
    private static volatile SoundUtil instance;
    public static Map<Integer, Integer> soundIdMap = new HashMap();
    /* access modifiers changed from: private */
    public Context context;
    protected ExecutorService executorService;
    /* access modifiers changed from: private */
    public volatile boolean isStop = false;
    /* access modifiers changed from: private */
    public List<Integer> soundId = new ArrayList();
    /* access modifiers changed from: private */
    public Map<String, Integer[]> soundMap = new HashMap();
    /* access modifiers changed from: private */
    public SoundPool soundPool;
    /* access modifiers changed from: private */
    public List<Integer> soundTime = new ArrayList();
    /* access modifiers changed from: private */
    public int streamId;

    private SoundUtil(Context context2) {
        this.context = context2;
        initSound();
    }

    public static SoundUtil getInstance(Context context2) {
        if (instance == null) {
            synchronized (SoundUtil.class) {
                if (instance == null) {
                    instance = new SoundUtil(context2);
                }
            }
        }
        return instance;
    }

    public void initSound() {
        this.soundPool = new SoundPool.Builder().build();
        this.executorService = Executors.newSingleThreadExecutor();
        if (LocaleUtils.isZh(this.context)) {
            this.executorService.execute(new Runnable() {
                public void run() {
                    Map access$200 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil = SoundUtil.this;
                    access$200.put("cur_tem", soundUtil.loadRaw(soundUtil.context, C1544R.raw.cur_tem));
                    Map access$2002 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil2 = SoundUtil.this;
                    access$2002.put("ssd", soundUtil2.loadRaw(soundUtil2.context, C1544R.raw.ssd));
                    Map access$2003 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil3 = SoundUtil.this;
                    access$2003.put("hsd", soundUtil3.loadRaw(soundUtil3.context, C1544R.raw.hsd));
                    Map access$2004 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil4 = SoundUtil.this;
                    access$2004.put("zero", soundUtil4.loadRaw(soundUtil4.context, C1544R.raw.mp3_0));
                    Map access$2005 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil5 = SoundUtil.this;
                    access$2005.put("one", soundUtil5.loadRaw(soundUtil5.context, C1544R.raw.mp3_1));
                    Map access$2006 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil6 = SoundUtil.this;
                    access$2006.put("two", soundUtil6.loadRaw(soundUtil6.context, C1544R.raw.mp3_2));
                    Map access$2007 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil7 = SoundUtil.this;
                    access$2007.put("three", soundUtil7.loadRaw(soundUtil7.context, C1544R.raw.mp3_3));
                    Map access$2008 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil8 = SoundUtil.this;
                    access$2008.put("four", soundUtil8.loadRaw(soundUtil8.context, C1544R.raw.mp3_4));
                    Map access$2009 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil9 = SoundUtil.this;
                    access$2009.put("five", soundUtil9.loadRaw(soundUtil9.context, C1544R.raw.mp3_5));
                    Map access$20010 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil10 = SoundUtil.this;
                    access$20010.put("six", soundUtil10.loadRaw(soundUtil10.context, C1544R.raw.mp3_6));
                    Map access$20011 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil11 = SoundUtil.this;
                    access$20011.put("seven", soundUtil11.loadRaw(soundUtil11.context, C1544R.raw.mp3_7));
                    Map access$20012 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil12 = SoundUtil.this;
                    access$20012.put("eight", soundUtil12.loadRaw(soundUtil12.context, C1544R.raw.mp3_8));
                    Map access$20013 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil13 = SoundUtil.this;
                    access$20013.put("nine", soundUtil13.loadRaw(soundUtil13.context, C1544R.raw.mp3_9));
                    Map access$20014 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil14 = SoundUtil.this;
                    access$20014.put("ten", soundUtil14.loadRaw(soundUtil14.context, C1544R.raw.mp3_10));
                    Map access$20015 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil15 = SoundUtil.this;
                    access$20015.put("hundred", soundUtil15.loadRaw(soundUtil15.context, C1544R.raw.hundred));
                    Map access$20016 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil16 = SoundUtil.this;
                    access$20016.put("point", soundUtil16.loadRaw(soundUtil16.context, C1544R.raw.point));
                    Map access$20017 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil17 = SoundUtil.this;
                    access$20017.put("cur_spo2", soundUtil17.loadRaw(soundUtil17.context, C1544R.raw.cur_spo2));
                    Map access$20018 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil18 = SoundUtil.this;
                    access$20018.put("percent", soundUtil18.loadRaw(soundUtil18.context, C1544R.raw.percent));
                    Map access$20019 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil19 = SoundUtil.this;
                    access$20019.put("cur_bg", soundUtil19.loadRaw(soundUtil19.context, C1544R.raw.cur_bg));
                    Map access$20020 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil20 = SoundUtil.this;
                    access$20020.put("after_meal", soundUtil20.loadRaw(soundUtil20.context, C1544R.raw.after_meal));
                    Map access$20021 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil21 = SoundUtil.this;
                    access$20021.put("fasting", soundUtil21.loadRaw(soundUtil21.context, C1544R.raw.fasting));
                    Map access$20022 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil22 = SoundUtil.this;
                    access$20022.put("cur_bp", soundUtil22.loadRaw(soundUtil22.context, C1544R.raw.cur_bp));
                    Map access$20023 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil23 = SoundUtil.this;
                    access$20023.put("high_pressure", soundUtil23.loadRaw(soundUtil23.context, C1544R.raw.high_pressure));
                    Map access$20024 = SoundUtil.this.soundMap;
                    SoundUtil soundUtil24 = SoundUtil.this;
                    access$20024.put("low_pressure", soundUtil24.loadRaw(soundUtil24.context, C1544R.raw.low_pressure));
                }
            });
            return;
        }
        this.soundMap.put("cur_tem", loadRaw(this.context, C1544R.raw.cur_tem_us));
        this.soundMap.put("ssd", loadRaw(this.context, C1544R.raw.ssd_us));
        this.soundMap.put("hsd", loadRaw(this.context, C1544R.raw.hsd_us));
        this.soundMap.put("zero", loadRaw(this.context, C1544R.raw.mp3_zero));
        this.soundMap.put("one", loadRaw(this.context, C1544R.raw.mp3_one));
        this.soundMap.put("two", loadRaw(this.context, C1544R.raw.mp3_two));
        this.soundMap.put("three", loadRaw(this.context, C1544R.raw.mp3_three));
        this.soundMap.put("four", loadRaw(this.context, C1544R.raw.mp3_four));
        this.soundMap.put("five", loadRaw(this.context, C1544R.raw.mp3_five));
        this.soundMap.put("six", loadRaw(this.context, C1544R.raw.mp3_six));
        this.soundMap.put("seven", loadRaw(this.context, C1544R.raw.mp3_seven));
        this.soundMap.put("eight", loadRaw(this.context, C1544R.raw.mp3_eight));
        this.soundMap.put("nine", loadRaw(this.context, C1544R.raw.mp3_nine));
        this.soundMap.put("ten", loadRaw(this.context, C1544R.raw.mp3_ten));
        this.soundMap.put("eleven", loadRaw(this.context, C1544R.raw.eleven));
        this.soundMap.put("twelve", loadRaw(this.context, C1544R.raw.twelve));
        this.soundMap.put("thirteen", loadRaw(this.context, C1544R.raw.thirteen));
        this.soundMap.put("fourteen", loadRaw(this.context, C1544R.raw.fourteen));
        this.soundMap.put("fifteen", loadRaw(this.context, C1544R.raw.fifteen));
        this.soundMap.put("sixteen", loadRaw(this.context, C1544R.raw.sixteen));
        this.soundMap.put("seventeen", loadRaw(this.context, C1544R.raw.seventeen));
        this.soundMap.put("eighteen", loadRaw(this.context, C1544R.raw.eighteen));
        this.soundMap.put("nineteen", loadRaw(this.context, C1544R.raw.nineteen));
        this.soundMap.put("twenty", loadRaw(this.context, C1544R.raw.twenty));
        this.soundMap.put("thirty", loadRaw(this.context, C1544R.raw.thirty));
        this.soundMap.put("forty", loadRaw(this.context, C1544R.raw.forty));
        this.soundMap.put("fifty", loadRaw(this.context, C1544R.raw.fifty));
        this.soundMap.put("sixty", loadRaw(this.context, C1544R.raw.sixty));
        this.soundMap.put("seventy", loadRaw(this.context, C1544R.raw.seventy));
        this.soundMap.put("eighty", loadRaw(this.context, C1544R.raw.eighty));
        this.soundMap.put("ninety", loadRaw(this.context, C1544R.raw.ninety));
        this.soundMap.put("hundred", loadRaw(this.context, C1544R.raw.hundred_us));
        this.soundMap.put("point", loadRaw(this.context, C1544R.raw.point_us));
        this.soundMap.put("cur_spo2", loadRaw(this.context, C1544R.raw.cur_spo2_us));
        this.soundMap.put("percent", loadRaw(this.context, C1544R.raw.percent_us));
        this.soundMap.put("cur_bg", loadRaw(this.context, C1544R.raw.cur_bg_us));
        this.soundMap.put("after_meal", loadRaw(this.context, C1544R.raw.after_meal_us));
        this.soundMap.put("fasting", loadRaw(this.context, C1544R.raw.fasting_us));
        this.soundMap.put("cur_bp", loadRaw(this.context, C1544R.raw.cur_bp_us));
        this.soundMap.put("high_pressure", loadRaw(this.context, C1544R.raw.high_pressure_us));
        this.soundMap.put("low_pressure", loadRaw(this.context, C1544R.raw.low_pressure_us));
        this.soundMap.put("and", loadRaw(this.context, C1544R.raw.and));
    }

    private int load(Context context2, int i) {
        return this.soundPool.load(context2, i, 1);
    }

    /* access modifiers changed from: private */
    public Integer[] loadRaw(Context context2, int i) {
        return new Integer[]{Integer.valueOf(this.soundPool.load(context2, i, 1)), Integer.valueOf(getMp3Duration(context2, i))};
    }

    private int getMp3Duration(Context context2, int i) {
        try {
            Uri parse = Uri.parse("android.resource://" + context2.getPackageName() + "/" + i);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context2, parse);
            mediaPlayer.setAudioStreamType(3);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public boolean getSoundIdMap(String[] strArr, SoundType soundType) {
        this.soundId.clear();
        this.soundTime.clear();
        if (soundType == SoundType.f849BT) {
            return getBTSoundList(strArr[0]);
        }
        if (soundType == SoundType.f847BG) {
            return getBGSoundMap(strArr);
        }
        if (soundType == SoundType.SPO2) {
            return getSPO2SoundMap(strArr[0]);
        }
        return getBPSoundMap(strArr);
    }

    private boolean getBTSoundList(String str) {
        int i;
        int i2;
        Integer[] numArr;
        if (str.contains(".")) {
            String[] split = str.split("\\.");
            i = Integer.parseInt(split[0]);
            i2 = Integer.parseInt(split[1]);
        } else {
            i = Integer.parseInt(str);
            i2 = 0;
        }
        int digit = DigitalUtil.getDigit(i);
        Integer[] numArr2 = this.soundMap.get("cur_tem");
        this.soundId.add(numArr2[0]);
        this.soundTime.add(numArr2[1]);
        getNumberSound(this.soundId, this.soundTime, i, digit);
        if (str.contains(".")) {
            Integer[] numArr3 = this.soundMap.get("point");
            this.soundId.add(numArr3[0]);
            this.soundTime.add(numArr3[1]);
            Integer[] soundIntegers = getSoundIntegers(DigitalUtil.getOneDigit(i2));
            this.soundId.add(soundIntegers[0]);
            this.soundTime.add(soundIntegers[1]);
        }
        if (SpManager.getTemperaTrueUnit() == 1) {
            numArr = this.soundMap.get("hsd");
        } else {
            numArr = this.soundMap.get("ssd");
        }
        this.soundId.add(numArr[0]);
        this.soundTime.add(numArr[1]);
        return true;
    }

    private boolean getBGSoundMap(String[] strArr) {
        int i;
        int i2;
        String str = strArr[0];
        String str2 = strArr[1];
        if (str.contains(".")) {
            String[] split = str.split("\\.");
            i = Integer.parseInt(split[0]);
            i2 = Integer.parseInt(split[1]);
        } else {
            i = Integer.parseInt(str);
            i2 = 0;
        }
        int digit = DigitalUtil.getDigit(i);
        Integer[] numArr = this.soundMap.get("cur_bg");
        this.soundId.add(numArr[0]);
        this.soundTime.add(numArr[1]);
        if (LocaleUtils.isZh(this.context)) {
            Integer[] numArr2 = this.soundMap.get("fasting");
            if (str2.equals("1")) {
                numArr2 = this.soundMap.get("after_meal");
            }
            this.soundId.add(numArr2[0]);
            this.soundTime.add(numArr2[1]);
        }
        getNumberSound(this.soundId, this.soundTime, i, digit);
        if (str.contains(".")) {
            Integer[] numArr3 = this.soundMap.get("point");
            this.soundId.add(numArr3[0]);
            this.soundTime.add(numArr3[1]);
            Integer[] soundIntegers = getSoundIntegers(DigitalUtil.getOneDigit(i2));
            this.soundId.add(soundIntegers[0]);
            this.soundTime.add(soundIntegers[1]);
        }
        if (!LocaleUtils.isZh(this.context)) {
            Integer[] numArr4 = this.soundMap.get("fasting");
            if (str2.equals("1")) {
                numArr4 = this.soundMap.get("after_meal");
            }
            this.soundId.add(numArr4[0]);
            this.soundTime.add(numArr4[1]);
        }
        return true;
    }

    private boolean getSPO2SoundMap(String str) {
        int parseInt = Integer.parseInt(str);
        int digit = DigitalUtil.getDigit(parseInt);
        Integer[] numArr = this.soundMap.get("cur_spo2");
        this.soundId.add(numArr[0]);
        this.soundTime.add(numArr[1]);
        if (LocaleUtils.isZh(this.context)) {
            Integer[] numArr2 = this.soundMap.get("percent");
            this.soundId.add(numArr2[0]);
            this.soundTime.add(numArr2[1]);
        }
        getNumberSound(this.soundId, this.soundTime, parseInt, digit);
        if (!LocaleUtils.isZh(this.context)) {
            Integer[] numArr3 = this.soundMap.get("percent");
            this.soundId.add(numArr3[0]);
            this.soundTime.add(numArr3[1]);
        }
        return true;
    }

    private boolean getBPSoundMap(String[] strArr) {
        int parseInt = Integer.parseInt(strArr[0]);
        int parseInt2 = Integer.parseInt(strArr[1]);
        int digit = DigitalUtil.getDigit(parseInt);
        int digit2 = DigitalUtil.getDigit(parseInt2);
        Integer[] numArr = this.soundMap.get("cur_bp");
        this.soundId.add(numArr[0]);
        this.soundTime.add(numArr[1]);
        Integer[] numArr2 = this.soundMap.get("high_pressure");
        this.soundId.add(numArr2[0]);
        this.soundTime.add(numArr2[1]);
        getNumberSound(this.soundId, this.soundTime, parseInt, digit);
        Integer[] numArr3 = this.soundMap.get("low_pressure");
        this.soundId.add(numArr3[0]);
        this.soundTime.add(numArr3[1]);
        getNumberSound(this.soundId, this.soundTime, parseInt2, digit2);
        return true;
    }

    private void getNumberSound(List<Integer> list, List<Integer> list2, int i, int i2) {
        if (i2 == 1) {
            Integer[] soundIntegers = getSoundIntegers(DigitalUtil.getOneDigit(i));
            list.add(soundIntegers[0]);
            list2.add(soundIntegers[1]);
        } else if (i2 == 2) {
            if (LocaleUtils.isZh(this.context)) {
                int twoDigit = DigitalUtil.getTwoDigit(i);
                if (twoDigit != 1) {
                    Integer[] soundIntegers2 = getSoundIntegers(twoDigit);
                    list.add(soundIntegers2[0]);
                    list2.add(soundIntegers2[1]);
                }
                Integer[] numArr = this.soundMap.get("ten");
                list.add(numArr[0]);
                list2.add(numArr[1]);
                int oneDigit = DigitalUtil.getOneDigit(i);
                if (oneDigit != 0) {
                    Integer[] soundIntegers3 = getSoundIntegers(oneDigit);
                    list.add(soundIntegers3[0]);
                    list2.add(soundIntegers3[1]);
                }
            } else if (DigitalUtil.getTwoDigit(i) == 1) {
                Integer[] soundIntegersTenIsOne = getSoundIntegersTenIsOne(DigitalUtil.getOneDigit(i));
                list.add(soundIntegersTenIsOne[0]);
                list2.add(soundIntegersTenIsOne[1]);
            } else {
                int oneDigit2 = DigitalUtil.getOneDigit(i);
                Log.e("haha", "getNumberSound: " + DigitalUtil.getTwoDigit(i) + " bt_value=" + i);
                Integer[] soundIntegersTen = getSoundIntegersTen(DigitalUtil.getTwoDigit(i));
                list.add(soundIntegersTen[0]);
                list2.add(soundIntegersTen[1]);
                if (oneDigit2 != 0) {
                    Integer[] soundIntegers4 = getSoundIntegers(DigitalUtil.getOneDigit(i));
                    list.add(soundIntegers4[0]);
                    list2.add(soundIntegers4[1]);
                }
            }
        } else if (i2 == 3) {
            Integer[] soundIntegers5 = getSoundIntegers(DigitalUtil.getThreeDigit(i));
            list.add(soundIntegers5[0]);
            list2.add(soundIntegers5[1]);
            Integer[] numArr2 = this.soundMap.get("hundred");
            list.add(numArr2[0]);
            list2.add(numArr2[1]);
            int twoDigit2 = DigitalUtil.getTwoDigit(i);
            int oneDigit3 = DigitalUtil.getOneDigit(i);
            if (LocaleUtils.isZh(this.context)) {
                Integer[] soundIntegers6 = getSoundIntegers(twoDigit2);
                Integer[] numArr3 = this.soundMap.get("ten");
                Integer[] soundIntegers7 = getSoundIntegers(DigitalUtil.getOneDigit(i));
                if (twoDigit2 != 0 || oneDigit3 != 0) {
                    if (twoDigit2 == 0 && oneDigit3 != 0) {
                        list.add(soundIntegers6[0]);
                        list2.add(soundIntegers6[1]);
                        list.add(soundIntegers7[0]);
                        list2.add(soundIntegers7[1]);
                    } else if (twoDigit2 == 0 || oneDigit3 != 0) {
                        list.add(soundIntegers6[0]);
                        list2.add(soundIntegers6[1]);
                        list.add(numArr3[0]);
                        list2.add(numArr3[1]);
                        list.add(soundIntegers7[0]);
                        list2.add(soundIntegers7[1]);
                    } else {
                        list.add(soundIntegers6[0]);
                        list2.add(soundIntegers6[1]);
                        list.add(numArr3[0]);
                        list2.add(numArr3[1]);
                    }
                }
            } else if (twoDigit2 != 0 || oneDigit3 != 0) {
                Integer[] numArr4 = this.soundMap.get("and");
                list.add(numArr4[0]);
                list2.add(numArr4[1]);
                if (twoDigit2 == 1) {
                    Integer[] soundIntegersTenIsOne2 = getSoundIntegersTenIsOne(DigitalUtil.getOneDigit(i));
                    list.add(soundIntegersTenIsOne2[0]);
                    list2.add(soundIntegersTenIsOne2[1]);
                    return;
                }
                if (twoDigit2 != 0) {
                    Integer[] soundIntegersTen2 = getSoundIntegersTen(DigitalUtil.getTwoDigit(i));
                    list.add(soundIntegersTen2[0]);
                    list2.add(soundIntegersTen2[1]);
                }
                Integer[] soundIntegers8 = getSoundIntegers(DigitalUtil.getOneDigit(i));
                list.add(soundIntegers8[0]);
                list2.add(soundIntegers8[1]);
            }
        }
    }

    private Integer[] getSoundIntegers(int i) {
        switch (i) {
            case 0:
                return this.soundMap.get("zero");
            case 1:
                return this.soundMap.get("one");
            case 2:
                return this.soundMap.get("two");
            case 3:
                return this.soundMap.get("three");
            case 4:
                return this.soundMap.get("four");
            case 5:
                return this.soundMap.get("five");
            case 6:
                return this.soundMap.get("six");
            case 7:
                return this.soundMap.get("seven");
            case 8:
                return this.soundMap.get("eight");
            case 9:
                return this.soundMap.get("nine");
            default:
                return null;
        }
    }

    private Integer[] getSoundIntegersTenIsOne(int i) {
        switch (i) {
            case 0:
                return this.soundMap.get("ten");
            case 1:
                return this.soundMap.get("eleven");
            case 2:
                return this.soundMap.get("twelve");
            case 3:
                return this.soundMap.get("thirteen");
            case 4:
                return this.soundMap.get("fourteen");
            case 5:
                return this.soundMap.get("fifteen");
            case 6:
                return this.soundMap.get("sixteen");
            case 7:
                return this.soundMap.get("seventeen");
            case 8:
                return this.soundMap.get("eighteen");
            case 9:
                return this.soundMap.get("nineteen");
            default:
                return null;
        }
    }

    private Integer[] getSoundIntegersTen(int i) {
        switch (i) {
            case 1:
                return this.soundMap.get("ten");
            case 2:
                return this.soundMap.get("twenty");
            case 3:
                return this.soundMap.get("thirty");
            case 4:
                return this.soundMap.get("forty");
            case 5:
                return this.soundMap.get("fifty");
            case 6:
                return this.soundMap.get("sixty");
            case 7:
                return this.soundMap.get("seventy");
            case 8:
                return this.soundMap.get("eighty");
            case 9:
                return this.soundMap.get("ninety");
            default:
                return null;
        }
    }

    public void play(final String[] strArr, final SoundType soundType) {
        this.isStop = false;
        this.executorService.execute(new Runnable() {
            public void run() {
                if (SoundUtil.this.getSoundIdMap(strArr, soundType)) {
                    for (int i = 0; i < SoundUtil.this.soundId.size() && !SoundUtil.this.isStop; i++) {
                        Log.e("haha", "run: play");
                        SoundUtil soundUtil = SoundUtil.this;
                        int unused = soundUtil.streamId = soundUtil.soundPool.play(((Integer) SoundUtil.this.soundId.get(i)).intValue(), 1.0f, 1.0f, 0, 0, 1.0f);
                        try {
                            Thread.sleep((long) (((Integer) SoundUtil.this.soundTime.get(i)).intValue() - 300));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void stop() {
        this.isStop = true;
        this.soundPool.stop(this.streamId);
    }

    public void release() {
        this.soundPool.release();
    }
}
