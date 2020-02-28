package com.e.words.temp;

import android.speech.tts.TextToSpeech;

import com.e.words.abby.abbyEntity.dto.StrWithLocaleDto;
import com.e.words.abby.abbyEntity.dto.WordDto;

import java.util.HashMap;
import java.util.List;

public class TestTTS {

    public void playTrack(List<StrWithLocaleDto> trackString, TextToSpeech textToSpeech) {
        HashMap<String, String> myHash = new HashMap<String, String>();

        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "done");

        for (int i = 0; i < trackString.size(); i++) {

            if (i == 0) { // Use for the first splited text to flush on audio stream

                textToSpeech.setLanguage(trackString.get(i).locale);
                textToSpeech.speak(trackString.get(i).str.trim(),TextToSpeech.QUEUE_FLUSH, /*myHash*/  null,null);

            } else { // add the new test on previous then play the TTS

                textToSpeech.setLanguage(trackString.get(i).locale);
                textToSpeech.speak(trackString.get(i).str.trim(), TextToSpeech.QUEUE_ADD, /*myHash*/null,null);
            }

            textToSpeech.playSilentUtterance(750, TextToSpeech.QUEUE_ADD, null);
        }
    }
}
