package com.example.sco.imuvo.HelperClasses;

/**
 * Created by sco on 19.12.2016.
 */
import android.util.Log;

import com.example.sco.imuvo.Model.Vocab;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class WebServiceHelper {
    public static byte[] getSpeechSync(String text) {
        VoiceProvider tts = new VoiceProvider("36911397cac94f028c2848220fa07eef");
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] result = null;
        try {
            result = tts.speech(params);
        } catch (Exception e) {
            //TODO Log
            Log.i("Imuvo",e.toString());
        }
        return result;

    }

    public static void getSpeechAsync(final Vocab vocab){
        try {
            VoiceProvider tts = new VoiceProvider("tf1698161f6b44202b06dd3fcc855ef52");
            VoiceParameters params = new VoiceParameters(vocab.getForeign(), Languages.English_UnitedStates);
            params.setCodec(AudioCodec.WAV);
            params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            params.setBase64(false);
            params.setSSML(false);
            params.setRate(0);

            tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
                @Override
                public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                    //TODO Log
                    Log.i("Imuvo",e.getException().getMessage());
                }
            });
            tts.addSpeechDataEventListener(new SpeechDataEventListener() {
                @Override
                public void handleSpeechDataEvent(SpeechDataEvent<?> e) {
                    try {
                        vocab.setSpeech((byte[]) e.getData());
                        VocabDatabaseHelper.update(vocab);
                    } catch (Exception ex) {
                        //TODO Log
                        Log.i("Imuvo",ex.toString());;
                    }
                }
            });

            tts.speechAsync(params);
        } catch (Exception ex) {
            //TODO Log
            Log.i("Imuvo",ex.toString());
        }
    }
}

