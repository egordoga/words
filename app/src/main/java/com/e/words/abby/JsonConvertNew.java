package com.e.words.abby;

import com.e.words.abby.abbyEntity.NodeType;
import com.e.words.abby.abbyEntity.dto.TranslAndEx;
import com.e.words.abby.abbyEntity.dto.WordDtoNew;
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

import static com.e.words.abby.abbyEntity.NodeType.EXAMPLE;
import static com.e.words.abby.abbyEntity.NodeType.EXAMPLE_ITEM;

public class JsonConvertNew {

    public List<String> list = new ArrayList<>();
    public WordObj wordObj = new WordObj();

    public void jsonToObj(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        WordDtoNew wordDto = null;
        try {
            JsonObj jsonObj = mapper.readValue(json, JsonObj.class);
            String w = convertBody(jsonObj.bodies);
        //    wordDto.word.str = jsonObj.title;
            System.out.println(w);
//            for (String s : list) {
//                System.out.println(s);
//            }
           System.out.println(wordObj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void convertSimpleParagraph(Markup markupIn, StringBuilder sb/*, boolean isExample*/) {
        for (Markup markup : markupIn.markupList) {

      //      System.out.println("    CSP " + markup.text);

            sb.append(markup.text);
            // addToList(markup, isExample);

           /* if (markup.isAccent) {
                System.out.println("isAccent" + markup.text);
            }
            if (markup.isItalics) {
                System.out.println("isItalics" + markup.text);
            }
            if (markup.isOptional) {
                System.out.println("isOptional" + markup.text);
            }*/
           /* if (markup.isAccent) {
                System.out.println("isAccent" + markup.text);
            }*/
        }
    }

    private String convertParagraph(Markup markupIn, StringBuilder sb, boolean isExample) {
        if (!markupIn.isOptional && markupIn.markupList != null) {
            StringBuilder s = new StringBuilder();
            for (Markup markup : markupIn.markupList) {

          //      System.out.println("   CP " + markup.text);

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
                        convertSimpleParagraph(markup, sb/*, false*/);
                        break;
                    case ABBREV:
                        sb.append("/")
                                .append(markup.text)
                                .append("/ ");
                        break;
                }
                //list.add(s.toString());
                // addToList(markup, isExample);
                //  sb.append("\n");
            }
            addToList(s.toString(), isExample);
            return s.toString();
        }
        return null;
    }

    private void convertListItem(Item item, StringBuilder sb) {
        TranslAndEx tae = new TranslAndEx();
     //   TranslWithExDto twe = null;
        //   List<ExampleDto> exs = new ArrayList<ExampleDto>();
        //  String s = "";
        for (Markup markup : item.markupList) {
         //   TranslAndEx tae = new TranslAndEx();

            //     System.out.println("    CLI " + markup.text);


            switch (markup.node) {
                case PARAGRAPH:
                    String s = convertParagraph(markup, sb, false);
                    if (s!= null && s.length() > 0) {
                        tae.transl = s;
                    }
                    //     System.out.println("TTRR " + s);
                    sb.append("\n");
                    if (tae.transl != null && s != null && s.length() > 0) {
                        wordObj.translations.add(tae);
                    }
                    break;
                case EXAMPLES:
                    // List<ExampleDto> exs = new ArrayList<ExampleDto>();
                    // if (twe != null) {
                    List<String> exs = convertExampleItem(markup.items, /*tae,*/ sb);
                    tae.examples.addAll(exs);
                    //   }
                    // twe.exs = exs;
                    break;
                case LIST:
                    for (Item markupItem : markup.items) {
                        convertListItem(markupItem, sb);
//                        if (tae.transl != null) {
//                            wordObj.translations.add(tae);
//                        }
                    }
                    break;
                default:
                    break;
            }

//            if (tae.transl != null) {
//                //continue;
//                wordObj.translations.add(tae);
//            }
        }
    }



    private List<String> convertExampleItem(List<Item> items, /*TranslAndEx tae,*/ StringBuilder sb) {
        List<String> exs = new ArrayList<>();
//        List<ExampleDto> exDtos = new ArrayList<ExampleDto>();
//        ExampleDto exDto;
//        String s = "";
        for (Item item : items) {
          //  exDto = new ExampleDto();
            switch (item.node) {
                case EXAMPLE_ITEM:
                    for (Markup markup : item.markupList) {
                        switch (markup.node) {
                            case EXAMPLE:
//                                if (markup.markupList.size()  == 2) {
//                                    exDto.en.str = markup.markupList.get(0).text;
//                                    exDto.ru.str = markup.markupList.get(1).text;
//                                }
                                //    exDtos.add(exDto);
                                String s =  convertParagraph(markup, sb, true);
                                //    tae.examples.add(s);
                                exs.add(s);
                                sb.append("\n");
                                //sb.append("EEEXXX").append(s).append("!*!");
                                //     System.out.println("   EX    " + s);
                                break;
                        }
                    }
                   // exDtos.add(exDto);
                    break;
            }

        }
        return exs;
    }

    private String convertBody(List<Body> bodies) {
     //   WordDto wordDto = new WordDto();
     //   List<TranslWithExDto> twes = new ArrayList<>();
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
                    TranscriptionNew transcr = new TranscriptionNew();
                    for (Markup markup : body.markups) {
                      //  convertParagraph(markup, sb, false);
                      //  sb.append("\n");
                        switch (markup.node) {
                            case SOUND:
                            //    transcr.soundFile = markup.fileName;
                                snds.add(markup.fileName);
                                break;
                            case TRANSCRIPTION:
                                trnscrs.add("[" + markup.text + "]");
                        }
                    }
                    break;
            }
        }
        for (int i = 0; i < trnscrs.size(); i++) {
            wordObj.transcriptions.add(new TranscriptionNew(trnscrs.get(i), snds.get(i)));
        }
        //wordDto.twes = twes;
        wordObj.article = sb.toString();
        return sb.toString();
    }

    private void addToList(Markup markup, boolean isExample) {
        if (!(markup.isOptional || markup.isItalics || markup.isAccent) && !isExample) {
            list.add(markup.text);
        }
    }

    private void addToList(String str, boolean isExample) {
        if (!isExample) {
            list.add(str);
        }
    }
}
