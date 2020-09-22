package com.e.words.abby.abbyEntity.dto.dto_new;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TranscriptionNew implements Serializable {
    public String transcript;
    public String soundFile;

    public TranscriptionNew() {
    }

    public TranscriptionNew(String transcript, String soundFile) {
        this.transcript = transcript;
        this.soundFile = soundFile;
    }

    @NonNull
    @Override
    public String toString() {
        return transcript + "   " + soundFile;
    }
}
