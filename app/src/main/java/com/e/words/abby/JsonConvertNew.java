package com.e.words.abby;

import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.abby.abbyEntity.genereted.Body;
import com.e.words.abby.abbyEntity.genereted.Item;
import com.e.words.abby.abbyEntity.genereted.JsonObj;
import com.e.words.abby.abbyEntity.genereted.Markup;
import com.e.words.entity.entityNew.Example;
import com.e.words.entity.entityNew.TranslationAndExample;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConvertNew {

  //  public List<String> list = new ArrayList<>();
    public List<String> sounds = new ArrayList<>();
    public WordObj wordObj = new WordObj();
    public int idxTransl;
    public int idxEx;

    public WordObj jsonToObj(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        try {
            JsonObj jsonObj = mapper.readValue(json, JsonObj.class);
            wordObj.word.word = jsonObj.title;
         //   wordObj.word.json = json;
            String w = convertBody(jsonObj.bodies);
//            wordObj.word.article = w;



            System.out.println("TRAN  " + wordObj.word.transcript);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordObj;
    }


    private void convertSimpleParagraph(Markup markupIn, StringBuilder sb) {
        for (Markup markup : markupIn.markupList) {
            sb.append(markup.text);
        }
    }

    private String convertParagraph(Markup markupIn, StringBuilder sb, boolean isExample) {
        if (!markupIn.isOptional && markupIn.markupList != null) {
            StringBuilder s = new StringBuilder();
            for (Markup markup : markupIn.markupList) {
                switch (markup.node) {
                    case CAPTION:
                    case CARD_REF:
                        return null;
                    case TEXT:
                        if (isExample) {
                            sb.append("   ");
                        }
                        sb.append(markup.text);
                        if (!markup.isItalics && !markup.text.equals(", ") && !markup.text.equals("; ") && !markup.isOptional) {
                            s.append(markup.text);
                        }
                        break;
                    case COMMENT:
                        convertSimpleParagraph(markup, sb);
                        break;
                    case ABBREV:
                        sb.append("/")
                                .append(markup.text)
                                .append("/ ");
                        break;
                }
            }
            //  addToList(s.toString(), isExample);
            return s.toString();
        }
        return null;
    }

    private void convertListItem(Item item, StringBuilder sb) {
        TranslationAndExample tae = new TranslationAndExample();
        for (Markup markup : item.markupList) {
            switch (markup.node) {
                case PARAGRAPH:
                    String s = convertParagraph(markup, sb, false);
                    if (s != null && s.length() > 0) {
                        tae.translation.translation = s;
                    }
                    sb.append("\n");
                    if (tae.translation != null && s != null && s.length() > 0) {
                        tae.translation.index = idxTransl++;
                        wordObj.translations.add(tae);
                    }
                    break;
                case EXAMPLES:
                    List<String> exs = convertExampleItem(markup.items, sb);
                 //   idxEx = 0;
                    for (String ex : exs) {
                        tae.examples.add(new Example(ex, tae.examples.size()  /*idxEx++*/));
                    }
                    break;
                case LIST:
                    for (Item markupItem : markup.items) {
                        convertListItem(markupItem, sb);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private List<String> convertExampleItem(List<Item> items, StringBuilder sb) {
        List<String> exs = new ArrayList<>();
        for (Item item : items) {
            switch (item.node) {
                case EXAMPLE_ITEM:
                    for (Markup markup : item.markupList) {
                        switch (markup.node) {
                            case EXAMPLE:
                                String s = convertParagraph(markup, sb, true);
                                exs.add(s);
                                sb.append("\n");
                                break;
                        }
                    }
                    break;
            }
        }
        return exs;
    }

    private String convertBody(List<Body> bodies) {
        List<String> trnscrs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Body body : bodies) {
            switch (body.node) {
                case LIST:
                    for (Item item : body.items) {
                        convertListItem(item, sb);
                    }
                    break;
                case PARAGRAPH:
                    for (Markup markup : body.markups) {
                        switch (markup.node) {
                            case SOUND:
                                sounds.add(markup.fileName);
                                break;
                            case TRANSCRIPTION:
                                trnscrs.add("[" + markup.text + "]");
                        }
                    }
                    break;
            }
        }
        wordObj.word.transcript = trnscrs.get(0);
        if (trnscrs.size() > 1) {
            wordObj.word.transcript += ("  " + trnscrs.get(1));
        }

//        System.out.println("TRNSCR SIZE  " + trnscrs.size());
//        for (String snd : trnscrs) {
//            System.out.println("CC " + snd);
//        }
//
//        for (String trnscr : trnscrs) {
//            if (!(trnscr == null/* || "".equals(trnscr) || "null".equals(trnscr)*/) ) {
//                wordObj.word.transcript += ("  " + trnscr);
//            }
//        }
        wordObj.word.article = sb.toString();
        return sb.toString();
    }

//    private void addToList(Markup markup, boolean isExample) {
//        if (!(markup.isOptional || markup.isItalics || markup.isAccent) && !isExample) {
//            list.add(markup.text);
//        }
//    }
//
//    private void addToList(String str, boolean isExample) {
//        if (!isExample) {
//            list.add(str);
//        }
//    }
}
