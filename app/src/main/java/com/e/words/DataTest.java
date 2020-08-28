package com.e.words;

import com.e.words.abby.abbyEntity.dto.StrWithLocaleDto;
import com.e.words.entity.ForeignWord;
import com.e.words.entity.ForeignWordWithTranslate;
import com.e.words.entity.Translate;
import com.e.words.entity.apiEntity.Minicard;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataTest {

    public static List<ForeignWordWithTranslate> getList() {
        List<ForeignWordWithTranslate> wordWithTranslateList = new ArrayList<>();
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("aa"), Arrays.asList(new Translate("A"), new Translate("AA"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("bb"), Arrays.asList(new Translate("B"), new Translate("BB"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("cc"), Arrays.asList(new Translate("C"), new Translate("CC"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("dd"), Arrays.asList(new Translate("D"), new Translate("DD"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ee"), Arrays.asList(new Translate("E"), new Translate("EE"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("ff"), Arrays.asList(new Translate("F"), new Translate("FF"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("gg"), Arrays.asList(new Translate("G"), new Translate("GG"))));
        wordWithTranslateList.add(new ForeignWordWithTranslate(new ForeignWord("hh"), Arrays.asList(new Translate("H"), new Translate("HH"))));

        return wordWithTranslateList;
    }

    public final static String jsonMinicard =
            "{" +
                    "SourceLanguage: 1049," +
                    "    \"TargetLanguage\": 1033,\n" +
                    "    \"Heading\": \"зима\",\n" +
                    "    \"Translation\": {\n" +
                    "        \"Heading\": \"зима\",\n" +
                    "        \"Translation\": \"winter\",\n" +
                    "        \"DictionaryName\": \"LingvoUniversal (Ru-En)\",\n" +
                    "        \"SoundName\": \"05084.wav\",\n" +
                    "        \"Type\": 1,\n" +
                    "        \"OriginalWord\": \"\"\n" +
                    "    },\n" +
                    "    \"SeeAlso\": []\n" +
                    "}";

    public static Minicard getMinicard(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonMinicard, Minicard.class);
    }

     public static List<StrWithLocaleDto> sunriseTtsList() {
         List<StrWithLocaleDto> list = new ArrayList<>();
         Locale enLoc = new Locale("en");
         Locale ruLoc = new Locale("ru");
         String w = "sunrise";
         String s = "восход солнца; утренняя заря";
         String p = "scarlet shafts of sunrise";
         String s1 = "утро, начало дня";
         list.add(new StrWithLocaleDto(w, enLoc));
         list.add(new StrWithLocaleDto(w, enLoc));
         list.add(new StrWithLocaleDto(s, ruLoc));
         list.add(new StrWithLocaleDto(p, enLoc));
         list.add(new StrWithLocaleDto(s1, ruLoc));
         return list;
     }


}
