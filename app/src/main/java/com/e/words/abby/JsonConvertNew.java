package com.e.words.abby;

import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.abby.abbyEntity.dto.dto_new.ExampleDto;
import com.e.words.abby.abbyEntity.dto.dto_new.TranscriptionNew;
import com.e.words.abby.abbyEntity.dto.dto_new.WordObj;
import com.e.words.abby.abbyEntity.genereted.Body;
import com.e.words.abby.abbyEntity.genereted.Item;
import com.e.words.abby.abbyEntity.genereted.JsonObj;
import com.e.words.abby.abbyEntity.genereted.Markup;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConvertNew {

    public List<String> list = new ArrayList<>();
    public WordObj wordObj = new WordObj();
    public int idxTransl;
    public int idxEx;

    public void jsonToObj(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        try {
            JsonObj jsonObj = mapper.readValue(json, JsonObj.class);
            wordObj.word = jsonObj.title;
            String w = convertBody(jsonObj.bodies);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        TranslAndEx tae = new TranslAndEx();
        for (Markup markup : item.markupList) {
            switch (markup.node) {
                case PARAGRAPH:
                    String s = convertParagraph(markup, sb, false);
                    if (s != null && s.length() > 0) {
                        tae.transl = s;
                    }
                    sb.append("\n");
                    if (tae.transl != null && s != null && s.length() > 0) {
                        tae.index = idxTransl++;
                        wordObj.translations.add(tae);
                    }
                    break;
                case EXAMPLES:
                    List<String> exs = convertExampleItem(markup.items, sb);
                 //   idxEx = 0;
                    for (String ex : exs) {
                        tae.exampleDtos.add(new ExampleDto(ex, tae.exampleDtos.size()  /*idxEx++*/));
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
        List<String> snds = new ArrayList<>();
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
                                snds.add(markup.fileName);
                                break;
                            case TRANSCRIPTION:
                                trnscrs.add("[" + markup.text + "]");
                        }
                    }
                    break;
            }
        }
        wordObj.transcriptions.addAll(trnscrs);
        wordObj.article = sb.toString();
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
