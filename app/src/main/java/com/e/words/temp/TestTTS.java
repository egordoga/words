package com.e.words.temp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
//
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import androidx.annotation.RequiresApi;

import com.e.words.R;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.abby.rest.RestRequest;
import com.e.words.config.AppProperty;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.TranslationAndExample;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static android.content.Context.MODE_PRIVATE;

public class TestTTS {

    private final Context ctx;
    private List<String> files = new ArrayList<>();


    public TestTTS(Context ctx) {
        this.ctx = ctx;
    }

    public void test(TextToSpeech tts) {
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");
        //     tts.en
        tts.setLanguage(ruLoc);
        tts.setSpeechRate(0.5f);
        tts.speak("This project is licensed under the Apache Я использую этот вспомогательный класс вот так", TextToSpeech.QUEUE_FLUSH, null, null);
//        tts.setLanguage(enLoc);
//        tts.speak("This project is licensed under the Apache", TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void playWordObj(WordObj wordObj, TextToSpeech tts, Context ctx) {
        AppProperty props = AppProperty.getInstance(ctx);
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");
        tts.setPitch(props.pitch);
        tts.setSpeechRate(props.speedTts);
        tts.setLanguage(enLoc);
        for (int i = 0; i < props.countRepeat; i++) {
            tts.speak(wordObj.word.word, TextToSpeech.QUEUE_ADD, null, null);
            tts.playSilentUtterance(props.wordPause, TextToSpeech.QUEUE_ADD, null);
        }
        for (TranslationAndExample tae : wordObj.translations) {
            tts.setLanguage(ruLoc);
            tts.speak(tae.translation.translation, TextToSpeech.QUEUE_ADD, null, null);
            tts.setLanguage(enLoc);
            tts.playSilentUtterance(props.translPause, TextToSpeech.QUEUE_ADD, null);
            for (Example ex : tae.examples) {
                String[] arr = ex.example.split("—");
                tts.speak(arr[0], TextToSpeech.QUEUE_ADD, null, null);
                tts.playSilentUtterance(props.translPause, TextToSpeech.QUEUE_ADD, null);
            }
        }
        tts.playSilentUtterance(props.wordPause, TextToSpeech.QUEUE_ADD, null);

    }

    public void recordWordObj(WordObj wordObj, TextToSpeech tts, Context ctx) {
        AppProperty props = AppProperty.getInstance(ctx);
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");
//        tts.setPitch(props.pitch);
//        tts.setSpeechRate(props.speedTts);
        tts.setLanguage(enLoc);
        for (int i = 0; i < props.countRepeat; i++) {
            tts.speak(wordObj.word.word, TextToSpeech.QUEUE_ADD, null, null);
            tts.playSilentUtterance(props.wordPause, TextToSpeech.QUEUE_ADD, null);
        }
        for (TranslationAndExample tae : wordObj.translations) {
            tts.setLanguage(ruLoc);
            tts.speak(tae.translation.translation, TextToSpeech.QUEUE_ADD, null, null);
            tts.setLanguage(enLoc);
            tts.playSilentUtterance(props.translPause, TextToSpeech.QUEUE_ADD, null);
            for (Example ex : tae.examples) {
                String[] arr = ex.example.split("—");
                tts.speak(arr[0], TextToSpeech.QUEUE_ADD, null, null);
                tts.playSilentUtterance(props.translPause, TextToSpeech.QUEUE_ADD, null);
            }
        }
        tts.playSilentUtterance(props.wordPause, TextToSpeech.QUEUE_ADD, null);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playSoundFile(String fileName, Context ctx) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANT)
                //      .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        SoundPool sSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        File dir = ctx.getFilesDir();
        File file = new File(dir, fileName);
        int id = sSoundPool.load(file.getPath(), 1);
        //int id1 = sSoundPool.load(ctx, R.raw.sound11, 1);

        sSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sSoundPool.play(id, 1, 1, 0, 1, 1);
            }
        });

//        FileInputStream fin = null;
//        try {
//            fin = ctx.openFileInput("testFile");
//            byte[] bytes = new byte[fin.available()];
//            fin.read(bytes);
//            String text = new String (bytes);
//         //   System.out.println(text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        MediaPlayer mp=new MediaPlayer();
//        try{
////            FileInputStream fis = new FileInputStream(new File("issue.wav"));
//            FileDescriptor fd = fin.getFD();
//            mp.setDataSource(fd);//Write your location here
//         //   mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
////            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                @Override
////                public void onPrepared(MediaPlayer mp) {
////                    mp.start();
////                }
////            });
//
//                mp.prepare();
//            mp.start();
//            System.out.println("OOO");
//
//        }catch(Exception e){e.printStackTrace();}

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveSoundFile(String fileName, Context ctx) {
        String str = RestRequest.getSoundString(fileName);
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
        //    str = Arrays.toString(Base64.getDecoder().decode(str));
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(fileName, MODE_PRIVATE);
            fos.write(Base64.getDecoder().decode(str));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testSaveFile(Context ctx, String str) {
        //String str = "FFDDFFDFDFDFDFFFD";
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput("testFile", MODE_PRIVATE);
            fos.write(/*str.getBytes() */   Base64.getDecoder().decode(str));
            fos.flush();
            fos.close();
            System.out.println("FILE SAVED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTestFile(Context ctx) {
        FileInputStream fin = null;
        try {
            fin = ctx.openFileInput("issue.wav");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testAddFile(Context ctx) {
//        try {
//            AudioInputStream clip1 = AudioSystem.getAudioInputStream(new File(wavFile1));
//            AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(wavFile2));
//
//            AudioInputStream appendedFiles =
//                    new AudioInputStream(new SequenceInputStream(clip1, clip2),
//                            clip1.getFormat(),
//                            clip1.getFrameLength() + clip2.getFrameLength());
//
//            AudioSystem.write(appendedFiles,
//                    AudioFileFormat.Type.WAVE,
//                    new File("D:\\wavAppended.wav"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public List<String> ttsToFiles(WordObj wordObj, TextToSpeech tts) {
        List<String> fileNames = new ArrayList<>();
        int count = 1;
        AppProperty props = AppProperty.getInstance(ctx);
        Locale enLoc = new Locale("en");
        Locale ruLoc = new Locale("ru");
//        tts.setPitch(props.pitch);
//        tts.setSpeechRate(props.speedTts);
//        tts.setLanguage(enLoc);
       /*for (int i = 0; i < props.countRepeat; i++) {
           tts.speak(wordObj.word.word, TextToSpeech.QUEUE_ADD, null, null);
           tts.playSilentUtterance(props.wordPause, TextToSpeech.QUEUE_ADD, null);
       }*/
        File dir = ctx.getFilesDir();
//        String fileName = wordObj.word.word + count++ + ".vaw";
//        fileNames.add(fileName);
//        File file = new File(dir, fileName);
        File file;
        String fileName;
//        File temp = null;
//        try {
//            temp = File.createTempFile("aaaaaaaa", "wav");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
 //       tts.synthesizeToFile(wordObj.word.word, null, file, null);
       for (TranslationAndExample tae : wordObj.translations) {
           tts.setLanguage(ruLoc);
           fileName = wordObj.word.word + count++ + ".wav";
           fileNames.add(fileName);
           file = new File(dir, fileName);
           tts.synthesizeToFile(tae.translation.translation, null, file, null);
           tts.setLanguage(enLoc);
           fileNames.add("Silent");
           for (Example ex : tae.examples) {
               String[] arr = ex.example.split("—");
               fileName = wordObj.word.word + count++ + ".wav";
               fileNames.add(fileName);
               file = new File(dir, fileName);
               tts.synthesizeToFile(arr[0], null, file, null);
               fileNames.add("Silent");
           }
       }
        files.addAll(fileNames);
        return fileNames;
    }

    public void tMediaPlayer() {
        File dir = ctx.getFilesDir();
        File file = new File(dir, "outputCombined.wav");
        MediaPlayer mp = new MediaPlayer();
        Uri uri = Uri.fromFile(file);
        mp.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            float speed = 1f;
            mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(speed));
            mp.prepare();
            mp.setDataSource(ctx, uri);
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            FileDescriptor fd = fis.getFD();
//            mp.setDataSource(fd);//Write your location here
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.start();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public void tMediaPlayer1(WordObj wordObj) {
//        try {
//            File outputFile = File.createTempFile("prefix", "extension");
//            FileOutputStream fos = new FileOutputStream(outputFile);
//            byte[] soundArr = new SoundRepo(ctx).findSoundByWordId(wordObj.word.id).soundGB;
//            fos.write(soundArr);
//        File dir = ctx.getFilesDir();
//     //   File file = new File(dir, "outputCombined.wav");
//        MediaPlayer mp = new MediaPlayer();
//        Uri uri = Uri.fromFile(outputFile);
//        mp.setAudioAttributes(
//                new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//        );
//
//            float speed = 1f;
////            mp.setPlaybackParams(mp.getPlaybackParams().setSpeed(speed));
//            mp.prepare();
//
//
//            for (int i = 0; i < 3; i++) {
//
//                mp.setDataSource(ctx, uri);
//                mp.start();
//            }
//
//
//        } catch (IOException | InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

//    public void makeAudioFile(List<String> fileNames, WordObj wordObj) {
//        AppProperty props = AppProperty.getInstance(ctx);
//        SoundRepo repo = new SoundRepo(ctx);
//        List<Byte> list = new ArrayList<>();
//        try {
//            byte[] soundArr = repo.findSoundByWordId(wordObj.word.id).soundGB;
//            File dir = ctx.getFilesDir();
//            File file = new File(dir, wordObj.word.word + ".wav");
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(soundArr);
//            fos.flush();
//            fos.close();
//            for (int i = 1; i < props.countRepeat; i++) {
//
//            }
//            Uri url = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + "raw/silent200.wav");
//            File file11 = new File(url.getPath());
//            System.out.println(file11.getAbsolutePath());
//            System.out.println("JJ " + url.toString());
//            byte[] soundArrSmall = new byte[soundArr.length - 44];
//            InputStream fis = getSilentFile(props.wordPause);
//            byte[] silentArr = new byte[fis.available() - 44];
//            fis.skip(44L);
//            fis.read(silentArr);
//            System.arraycopy(soundArr, 44, soundArrSmall, 0,
//                    soundArrSmall.length);
//            for (byte b : soundArr) {
//                list.add(b);
//            }
//            for (int i = 1; i < props.countRepeat; i++) {
//                for (byte b : silentArr) {
//                    list.add(b);
//                }
//                for (byte b : soundArrSmall) {
//                    list.add(b);
//                }
//            }
//            for (byte b : soundArrSmall) {
//                list.add(b);
//            }
//
//
//
//
//            System.out.println("SS " + soundArr.length);
//            System.out.println("DD " + soundArrSmall.length);
//            System.out.println("FF " + silentArr.length);
//            System.out.println("AA " + list.size());
//        } catch (ExecutionException | InterruptedException /*| IOException*/ e) {
//            e.printStackTrace();
//        }
//    }

    public void addFiles(File file1, File file2, String outFile) {
        Executors.newFixedThreadPool(4).execute(() -> {
//            List<Byte> list = new LinkedList<>();
//            File dir = ctx.getFilesDir();
//            File file = new File(dir, "issue.wav");
//            File file2 = new File(dir, "look.wav");
//            File file1 = new File(dir, "outputCombined.wav");
            FileInputStream in1 = null, in2 = null;
            final int RECORDER_BPP = 32; //8,16,32..etc
            int RECORDER_SAMPLERATE = 11025; //8000,11025,16000,32000,48000,96000,44100..et
            FileOutputStream out = null;
            long totalAudioLen = 0;
            long totalDataLen = totalAudioLen + 36;
            long longSampleRate = RECORDER_SAMPLERATE;
            int channels = 1;  //mono=1,stereo=2
            long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;
            int bufferSize = 1024;
            byte[] data = new byte[bufferSize];
            try {
                in1 = new FileInputStream(file1);
                in2 = new FileInputStream(file2);
                out = new FileOutputStream(file1);
                totalAudioLen = in1.getChannel().size() + in2.getChannel().size() - 88;
                totalDataLen = totalAudioLen + 36;
                WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                        longSampleRate, channels, byteRate, RECORDER_BPP);
                boolean isFirst = true;
                in1.skip(44L);
                in2.skip(44L);
                while (in1.read(data) != -1) {
                    out.write(data);
                }
                while (in2.read(data) != -1) {
                    out.write(data);
                }
                out.flush();
                out.close();
                in1.close();
                in2.close();
                bufferSize = 1024;
                data = new byte[bufferSize];
                out.close();

//                SequenceInputStream sis = new SequenceInputStream()
//                System.out.println("TTTTTTTTTTTTTT");

                //    Toast.makeText(this, "Done!!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                     long totalDataLen, long longSampleRate, int channels, long byteRate, int RECORDER_BPP)
            throws IOException {

        byte[] header = new byte[44];

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte)(totalDataLen & 0xff);
        header[5] = (byte)((totalDataLen >> 8) & 0xff);
        header[6] = (byte)((totalDataLen >> 16) & 0xff);
        header[7] = (byte)((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte)(longSampleRate & 0xff);
        header[25] = (byte)((longSampleRate >> 8) & 0xff);
        header[26] = (byte)((longSampleRate >> 16) & 0xff);
        header[27] = (byte)((longSampleRate >> 24) & 0xff);
        header[28] = (byte)(byteRate & 0xff);
        header[29] = (byte)((byteRate >> 8) & 0xff);
        header[30] = (byte)((byteRate >> 16) & 0xff);
        header[31] = (byte)((byteRate >> 24) & 0xff);
        header[32] = (byte)(2 * 16 / 8);
        header[33] = 0;
        header[34] = (byte) RECORDER_BPP;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte)(totalAudioLen & 0xff);
        header[41] = (byte)((totalAudioLen >> 8) & 0xff);
        header[42] = (byte)((totalAudioLen >> 16) & 0xff);
        header[43] = (byte)((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }

    private InputStream getSilentFile(int duration) {
        InputStream is = null;
        if (duration < 150) {
            is = ctx.getResources().openRawResource(R.raw.silent100);
        } else if (duration < 250) {
            is = ctx.getResources().openRawResource(R.raw.silent200);
        } else if (duration < 350) {
            is = ctx.getResources().openRawResource(R.raw.silent300);
        } else if (duration < 450) {
            is = ctx.getResources().openRawResource(R.raw.silent400);
        } else if (duration < 550) {
            is = ctx.getResources().openRawResource(R.raw.silent500);
        } else if (duration < 650) {
            is = ctx.getResources().openRawResource(R.raw.silent600);
        } else if (duration < 750) {
            is = ctx.getResources().openRawResource(R.raw.silent700);
        } else if (duration < 850) {
            is = ctx.getResources().openRawResource(R.raw.silent800);
        } else if (duration < 950) {
            is = ctx.getResources().openRawResource(R.raw.silent900);
        } else if (duration < 1050) {
            is = ctx.getResources().openRawResource(R.raw.silent1000);
        } else if (duration < 1150) {
            is = ctx.getResources().openRawResource(R.raw.silent1100);
        } else if (duration < 1250) {
            is = ctx.getResources().openRawResource(R.raw.silent1200);
        } else if (duration < 1350) {
            is = ctx.getResources().openRawResource(R.raw.silent1300);
        } else if (duration < 1450) {
            is = ctx.getResources().openRawResource(R.raw.silent1400);
        } else if (duration < 1550) {
            is = ctx.getResources().openRawResource(R.raw.silent1500);
        } else if (duration < 1650) {
            is = ctx.getResources().openRawResource(R.raw.silent1600);
        } else if (duration < 1750) {
            is = ctx.getResources().openRawResource(R.raw.silent1700);
        } else if (duration < 1850) {
            is = ctx.getResources().openRawResource(R.raw.silent1800);
        } else if (duration < 1950) {
            is = ctx.getResources().openRawResource(R.raw.silent1900);
        } else if (duration < 2050) {
            is = ctx.getResources().openRawResource(R.raw.silent2000);
        }
        return is;
    }

    public void elseType() {
       /* @Override
        protected Void doInBackground(Void... params) {

            isProcessingOn=true;
            try {
                DataOutputStream amplifyOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory() + "/Soundrecpluspro/"  + year +"-"+ month +"-"+ date +"-"+ hour+"-" + min +"-"+ sec+"ME.wav")));
                DataInputStream[] mergeFilesStream = new DataInputStream[selection.size()];
                long[] sizes=new long[selection.size()];
                for(int i=0; i<selection.size(); i++) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/Soundrecpluspro/" +selection.get(i));
                    sizes[i] = (file.length()-44)/2;
                }
                for(int i =0; i<selection.size(); i++) {
                    mergeFilesStream[i] =new DataInputStream(new BufferedInputStream(new FileInputStream(Environment.getExternalStorageDirectory() + "/Soundrecpluspro/" +selection.get(i))));

                    if(i == selection.size()-1) {
                        mergeFilesStream[i].skip(24);
                        byte[] sampleRt = new byte[4];
                        mergeFilesStream[i].read(sampleRt);
                        ByteBuffer bbInt = ByteBuffer.wrap(sampleRt).order(ByteOrder.LITTLE_ENDIAN);
                        RECORDER_SAMPLERATE = bbInt.getInt();
                        mergeFilesStream[i].skip(16);
                    }
                    else {
                        mergeFilesStream[i].skip(44);
                    }

                }

                for(int b=0; b<selection.size(); b++) {
                    for(int i=0; i<(int)sizes[b]; i++) {
                        byte[] dataBytes = new byte[2];
                        try {
                            dataBytes[0] = mergeFilesStream[b].readByte();
                            dataBytes[1] = mergeFilesStream[b].readByte();
                        }
                        catch (EOFException e) {
                            amplifyOutputStream.close();
                        }
                        short dataInShort = ByteBuffer.wrap(dataBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
                        float dataInFloat= (float) dataInShort/37268.0f;


                        short outputSample = (short)(dataInFloat * 37268.0f);
                        byte[] dataFin = new byte[2];
                        dataFin[0] = (byte) (outputSample & 0xff);
                        dataFin[1] = (byte)((outputSample >> 8) & 0xff);
                        amplifyOutputStream.write(dataFin, 0 , 2);

                    }
                }
                amplifyOutputStream.close();
                for(int i=0; i<selection.size(); i++) {
                    mergeFilesStream[i].close();
                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            long size =0;
            try {
                FileInputStream fileSize = new FileInputStream(Environment.getExternalStorageDirectory() + "/Soundrecpluspro/"+year +"-"+ month +"-"+ date +"-"+ hour+"-" + min +"-"+ sec+"ME.wav");
                size = fileSize.getChannel().size();
                fileSize.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            final int RECORDER_BPP = 16;

            long datasize=size+36;
            long byteRate = (RECORDER_BPP * RECORDER_SAMPLERATE)/8;
            long longSampleRate = RECORDER_SAMPLERATE;
            byte[] header = new byte[44];


            header[0] = 'R';  // RIFF/WAVE header
            header[1] = 'I';
            header[2] = 'F';
            header[3] = 'F';
            header[4] = (byte) (datasize & 0xff);
            header[5] = (byte) ((datasize >> 8) & 0xff);
            header[6] = (byte) ((datasize >> 16) & 0xff);
            header[7] = (byte) ((datasize >> 24) & 0xff);
            header[8] = 'W';
            header[9] = 'A';
            header[10] = 'V';
            header[11] = 'E';
            header[12] = 'f';  // 'fmt ' chunk
            header[13] = 'm';
            header[14] = 't';
            header[15] = ' ';
            header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
            header[17] = 0;
            header[18] = 0;
            header[19] = 0;
            header[20] = 1;  // format = 1
            header[21] = 0;
            header[22] = (byte) 1;
            header[23] = 0;
            header[24] = (byte) (longSampleRate & 0xff);
            header[25] = (byte) ((longSampleRate >> 8) & 0xff);
            header[26] = (byte) ((longSampleRate >> 16) & 0xff);
            header[27] = (byte) ((longSampleRate >> 24) & 0xff);
            header[28] = (byte) (byteRate & 0xff);
            header[29] = (byte) ((byteRate >> 8) & 0xff);
            header[30] = (byte) ((byteRate >> 16) & 0xff);
            header[31] = (byte) ((byteRate >> 24) & 0xff);
            header[32] = (byte) ((RECORDER_BPP) / 8);  // block align
            header[33] = 0;
            header[34] = RECORDER_BPP;  // bits per sample
            header[35] = 0;
            header[36] = 'd';
            header[37] = 'a';
            header[38] = 't';
            header[39] = 'a';
            header[40] = (byte) (size & 0xff);
            header[41] = (byte) ((size >> 8) & 0xff);
            header[42] = (byte) ((size >> 16) & 0xff);
            header[43] = (byte) ((size >> 24) & 0xff);
            // out.write(header, 0, 44);

            try {
                RandomAccessFile rFile = new RandomAccessFile(Environment.getExternalStorageDirectory() + "/Soundrecpluspro/"  +year +"-"+ month +"-"+ date +"-"+ hour+"-" + min +"-"+ sec+ "ME.wav", "rw");
                rFile.seek(0);
                rFile.write(header);
                rFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return null;
        }  */
    }
}
