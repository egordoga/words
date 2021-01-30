package com.e.words.entity.entityNew;

//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//import static androidx.room.ForeignKey.CASCADE;
//
//@Entity(foreignKeys = @ForeignKey(entity = Word.class, parentColumns = "id", childColumns = "wordId",
//        onDelete = CASCADE))
//public class Sound {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    public byte[] soundGB;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    public byte[] soundUS;
//    @ColumnInfo(index = true)
//    public long wordId;
//
//    public Sound(byte[] soundGB, byte[] soundUS, long wordId) {
//        this.soundGB = soundGB;
//        this.soundUS = soundUS;
//        this.wordId = wordId;
//    }
//}
