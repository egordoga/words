package com.e.words.entity.entityNew;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

//@Entity(foreignKeys = @ForeignKey(entity = Word.class, parentColumns = "id", childColumns = "wordId", onDelete = CASCADE))
//@Entity(foreignKeys = @ForeignKey(entity = Word.class, parentColumns = "id", childColumns = "wordId"/*, onDelete = CASCADE*/))
@Entity
public class Translation {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String translation;
    @ColumnInfo(name = "idx")
    public int index;
    @ColumnInfo(index = true)
    public long wordId;
    @Ignore
    public Boolean isChecked = false;

    public Translation() {
    }

    @Ignore
    public Translation(String translation, int index, long wordId) {
        this.translation = translation;
        this.index = index;
        this.wordId = wordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Translation)) return false;
        Translation that = (Translation) o;
        return translation.equals(that.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translation);
    }
}
