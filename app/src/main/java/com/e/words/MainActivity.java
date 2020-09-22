package com.e.words;

import android.os.Bundle;

import com.e.words.adapter.VocabularyAdapter;
import com.e.words.fragment.ArticleFragment;
import com.e.words.fragment.MainFragment;
import com.e.words.temp.TestTTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;

public class MainActivity extends /*FragmentActivity*/AppCompatActivity implements TextToSpeech.OnInitListener {

//    @BindView(R.id.rv_vocabulary)
//    RecyclerView rvVocabulary;
//    @BindView(R.id.button)
//    Button test;
    private VocabularyAdapter adapter;
    private TextToSpeech tts;
    TestTTS testTTS;
    ArticleFragment artFrgm;
    MainFragment mainFragment;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

//

//        rvVocabulary.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new VocabularyAdapter(this, null);
//        rvVocabulary.setAdapter(adapter);
//        adapter.setItem(DataTest.getList());
//        tts = new TextToSpeech(this, this);
//        testTTS = new TestTTS();
//        Context ctx = this;
//        artFrgm = new ArticleFragment();
      //  artFrgm = new ArticleFragment();
        mainFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_act, mainFragment)
         //       .replace(R.id.main_act, artFrgm)
                .commit();

    /*    test.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

             //   String s = RestRequest.getWordJson("shirt", "1033", "1049");
              //  String s = JsonData.WISH;
//                JsonConvertNew jc = new JsonConvertNew();
//                jc.jsonToObj(s);
//                System.out.println(s);
//                JsonConvert jConv = new JsonConvert();
//                WordDto word = jConv.jsonToObj(s);
////                tts.setLanguage(new Locale("ru"));
//                testTTS.playTrack(StrWithLocaleDto.getTestData(word), tts);

             //   System.out.println(s);
           //     SoundTrack.playTrack();
           //     new TempSoundFile().recordFile();
//                RestRequest rr = new RestRequest();
//                String s = "NONE";
//                s =  RestRequest.getSoundString("issue.wav");
//                System.out.println(s);
//                System.out.println("AAA " +s.charAt(22) + "  " + s.length());
//                s = s.substring(1, s.length() - 1);
//
//                byte[] s1 = Base64.getDecoder().decode(s);
//                System.out.println(Arrays.toString(s1));


           //     testTTS.saveSoundFile("issue.wav", s, ctx);

           //   testTTS.testSaveFile(ctx, s);
              //  testTTS.readTestFile(ctx);
             //   testTTS.playSoundFile("sunrise.wav", ctx);
             //   testTTS.testFullTrack("sunrise.wav", DataTest.sunriseTtsList(), ctx, tts);
             //   testTTS.saveSoundFile("sunrise.wav", ctx);

//                AudioAttributes attributes = new AudioAttributes.Builder()
//                        .setUsage(AudioAttributes.USAGE_GAME)
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                        .build();
//                SoundPool sSoundPool = new SoundPool.Builder()
//                        .setAudioAttributes(attributes)
//                        .build();
//                int id = sSoundPool.load("issue.wav", 1);
//                sSoundPool.play(id, 1, 1, 0, 0, 1);
//                sSoundPool.setOnLoadCompleteListener(this);



//                artFrgm = new ArticleFragment();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.main_act, artFrgm)
//                        .replace(R.id.main_act, artFrgm)
//                        .commit();

            }
        });*/

      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_save) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onInit(int status) {

    }
}
