package com.example.sco.imuvo.HelperClasses;

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
    private WebServiceHelperListener webServiceHelperListener;

    public WebServiceHelper(){
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
                    } catch (Exception ex) {
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

