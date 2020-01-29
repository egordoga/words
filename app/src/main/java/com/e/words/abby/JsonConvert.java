package com.e.words.abby;

import com.e.words.abby.abbyEntity.dto.ExampleDto;
import com.e.words.abby.abbyEntity.dto.TranslWithExDto;
import com.e.words.abby.abbyEntity.dto.WordDto;
import com.e.words.abby.abbyEntity.genereted.Body;
import com.e.words.abby.abbyEntity.genereted.Item;
import com.e.words.abby.abbyEntity.genereted.JsonObj;
import com.e.words.abby.abbyEntity.genereted.Markup;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonConvert {

    public WordDto jsonToObj(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        WordDto wordDto = null;
        try {
            JsonObj jsonObj = mapper.readValue(json, JsonObj.class);
            wordDto = convertBody(jsonObj.bodies);
            wordDto.word = jsonObj.title;
            System.out.println(wordDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordDto;
    }


    private String convertSimpleParagraph(Markup markupIn) {
        StringBuilder sb = new StringBuilder();
        for (Markup markup : markupIn.markupList) {
            sb.append(markup.text);
        }
        return sb.toString();
    }

    private String convertParagraph(Markup markupIn) {
        StringBuilder sb = new StringBuilder();
        for (Markup markup : markupIn.markupList) {
            switch (markup.node) {
                case TEXT:
                    sb.append(markup.text);
                    break;
                case COMMENT:
                    sb.append(convertSimpleParagraph(markup));
                    break;
                case ABBREV:
                    sb.append("/")
                            .append(markup.text)
                            .append("/ ");
                    break;
                default:
                    return "";
            }
        }
        return sb.toString();
    }

    private TranslWithExDto convertListItem(Item item, List<TranslWithExDto> twes, StringBuilder sb) {
        TranslWithExDto twe = null;
        List<ExampleDto> exs = new ArrayList<ExampleDto>();
        String s = "";
        for (Markup markup : item.markupList) {
            switch (markup.node) {
                case PARAGRAPH:
                    s = convertParagraph(markup).trim();
                    if (!s.equals("") && !(s.startsWith("/") && s.endsWith("/"))) {
                        twe = new TranslWithExDto();
                        twe.transl = s;
                        twes.add(twe);
                    }
                    sb.append("TTTRRR").append(s).append("!*!");
               //     System.out.println("TR " + s);
                    break;
                case LIST:
                    for (Item markupItem : markup.items) {
                        convertListItem(markupItem, twes, sb);
                    }
                    break;
                case EXAMPLES:
                   // List<ExampleDto> exs = new ArrayList<ExampleDto>();
                    if (twe != null) {
                        twe.exs = convertExampleItem(markup.items, /*exs,*/ sb);
                    }
                    // twe.exs = exs;
                    break;
                default:
                    return null;
            }
          //  twes.add(twe);
        }
        return twe;
    }



    private List<ExampleDto> convertExampleItem(List<Item> items, /*List<ExampleDto> exDtos,*/ StringBuilder sb) {
        List<ExampleDto> exDtos = new ArrayList<ExampleDto>();
        ExampleDto exDto;
        String s = "";
        for (Item item : items) {
            exDto = new ExampleDto();
            switch (item.node) {
                case EXAMPLE_ITEM:
                    for (Markup markup : item.markupList) {
                        switch (markup.node) {
                            case EXAMPLE:
                                if (markup.markupList.size()  == 2) {
                                    exDto.en = markup.markupList.get(0).text;
                                    exDto.ru = markup.markupList.get(1).text;
                                }
                                //    exDtos.add(exDto);
                                s = convertSimpleParagraph(markup);
                                sb.append("EEEXXX").append(s).append("!*!");
                           //     System.out.println("   EX    " + s);
                                break;
                        }
                    }
                    exDtos.add(exDto);
                    break;
            }

        }
        return exDtos;
    }

    private WordDto convertBody(List<Body> bodies) {
        WordDto wordDto = new WordDto();
        List<TranslWithExDto> twes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Body body : bodies) {
            switch (body.node) {
                case LIST:
                    for (Item item : body.items) {
                        convertListItem(item, twes, sb);
                    }
                    break;
                case PARAGRAPH:
                    for (Markup markup : body.markups) {
                        switch (markup.node) {
                            case SOUND:
                                wordDto.sounds.add(markup.fileName);
                                break;
                            case TRANSCRIPTION:
                                wordDto.transcripts.add(markup.text);
                        }
                    }
                    break;
            }
        }
        wordDto.twes = twes;
        return wordDto;
    }


    private List<TranslWithExDto> convertSbToObj(StringBuilder sb) {
        List<TranslWithExDto> list = new LinkedList<>();
        String[] arr = sb.toString().split("!*!");
        TranslWithExDto twe = new TranslWithExDto();
        for (String s : arr) {
            if (s.startsWith("TTTRRR")) {
                s = s.substring(6).trim();
                if (s.startsWith("/") && s.endsWith("/")) {
                    twe.transl = "";
                   // continue;
                } else {
                    list.add(twe);
                    twe = new TranslWithExDto();
                    twe.transl = s;
                }
            } else if (s.startsWith("EEEXXX")) {
                s = s.substring(6);
                ExampleDto exDto = new ExampleDto();
                String[] exs = s.split("—");
                exDto.en = exs[0];
                exDto.ru = exs[1];
                twe.exs.add(exDto);
            }

        }
        list.remove(0);
        return list;
    }
}
