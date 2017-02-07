package com.example.sco.imuvo.HelperClasses;

/**
 * Created by sco on 19.12.2016.
 */
import android.util.Log;

import com.example.sco.imuvo.Activities.readVocabs;
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

    private final static String APIKEY = "9c51e2ccdb01408e8f0caf7e90e60f7f";
    private static WebServiceHelperListener webServiceHelperListener;


    public static byte[] getSpeechSync(String text) {
        VoiceProvider tts = new VoiceProvider(APIKEY);
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

    public void getSpeechAsync(final Vocab vocab){
        try {
            VoiceProvider tts = new VoiceProvider(APIKEY);
            VoiceParameters params = new VoiceParameters(vocab.getForeign(), Languages.English_UnitedStates);
            params.setCodec(AudioCodec.WAV);
            params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            params.setBase64(false);
            params.setSSML(false);
            params.setRate(0);
            tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
                @Override
                public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                }
            });
            tts.addSpeechDataEventListener(new SpeechDataEventListener() {
                @Override
                public void handleSpeechDataEvent(SpeechDataEvent<?> e) {
                    try {
                        webServiceHelperListener.onWebServiceReturnResult((byte[]) e.getData());
                    } catch (Exception ex) {;
                    }
                }
            });
            tts.speechAsync(params);

        } catch (Exception ex) {
        }
    }

    public void setWebServiceHelperListener(WebServiceHelperListener webServiceHelperListener) {
        this.webServiceHelperListener = webServiceHelperListener;
    }
}

