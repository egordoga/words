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

    public List<String> sounds = new ArrayList<>();
    public List<String> trnscrs = new ArrayList<>();
    public WordObj wordObj = new WordObj();
    public int idxTransl;

    public WordObj jsonToObj(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        try {
            JsonObj jsonObj = mapper.readValue(json, JsonObj.class);
            wordObj.word.word = jsonObj.title;
            String w = convertBody(jsonObj.bodies);
            makeTranscriptStr();
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
                    case SOUND:
                        sounds.add(markup.fileName);
                        break;
                    case TRANSCRIPTION:
                        trnscrs.add("[" + markup.text + "]");
                        break;
                }
            }
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
                    for (String ex : exs) {
                        tae.examples.add(new Example(ex, tae.examples.size()));
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
        wordObj.word.article = sb.toString();
        return sb.toString();
    }

    private void makeTranscriptStr() {
        if (trnscrs.size() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(trnscrs.get(0));
            if (trnscrs.size() > 1) {
                for (int i = 1; i < trnscrs.size(); i++) {
                    sb.append("  ").append(trnscrs.get(i));
                }
            }
            wordObj.word.transcript = sb.toString();
        }
    }
}
