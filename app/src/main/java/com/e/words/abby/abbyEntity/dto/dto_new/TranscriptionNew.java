package com.e.words.abby.abbyEntity.dto.dto_new;

import androidx.annotation.NonNull;

public class TranscriptionNew {
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
