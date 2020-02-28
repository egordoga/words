package com.e.words;

import android.os.Build;
import android.os.Bundle;

import com.e.words.abby.JsonConvert;
import com.e.words.abby.abbyEntity.dto.StrWithLocaleDto;
import com.e.words.abby.abbyEntity.dto.WordDto;
import com.e.words.abby.rest.AuthToken;
import com.e.words.abby.rest.RestRequest;
import com.e.words.adapter.VocabularyAdapter;
import com.e.words.sound.SoundTrack;
import com.e.words.temp.TempSoundFile;
import com.e.words.temp.TestTTS;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @BindView(R.id.rv_vocabulary)
    RecyclerView rvVocabulary;
    @BindView(R.id.button)
    Button test;
    private VocabularyAdapter adapter;
    private TextToSpeech tts;
    TestTTS testTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        rvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VocabularyAdapter(this, null);
        rvVocabulary.setAdapter(adapter);
        adapter.setItem(DataTest.getList());
        tts = new TextToSpeech(this, this);
        testTTS = new TestTTS();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = RestRequest.getWordJson("plum", "1033", "1049");
                System.out.println(s);
                JsonConvert jConv = new JsonConvert();
                WordDto word = jConv.jsonToObj(s);
//                tts.setLanguage(new Locale("ru"));
                testTTS.playTrack(StrWithLocaleDto.getTestData(word), tts);

             //   System.out.println(s);
           //     SoundTrack.playTrack();
           //     new TempSoundFile().recordFile();
//                RestRequest rr = new RestRequest();
//              new RestRequest.SoundTask().execute(RestRequest.getURLSound("look.wav"));
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {

    }
}
