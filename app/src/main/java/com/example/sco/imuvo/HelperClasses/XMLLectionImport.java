package com.example.sco.imuvo.HelperClasses;

import android.content.Context;
import android.net.Uri;

import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.Model.Lection;
import com.example.sco.imuvo.Model.Vocab;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by sco on 06.03.2017.
 */

public class XMLLectionImport {

    public static ArrayList<Lection> lections;
    public static ArrayList<Vocab> vocabs;
    public static void importLection(Context context, Uri uri){
        XmlPullParserFactory factory = null;
        lections = new ArrayList<>();
        vocabs = new ArrayList<>();
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            InputStream fis = context.getContentResolver().openInputStream(uri);
            xpp.setInput(fis, null);
            parse(xpp,context);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parse(XmlPullParser xpp,Context context) throws XmlPullParserException, IOException {
        Vocab vocab = null;
        Lection lection = null;
        String text = null;
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = xpp.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("lection")) {
                        lection = new Lection();
                    }else if (tagname.equalsIgnoreCase("vocab")) {
                        vocab = new Vocab();
                        vocab.setLection(lection.getNumber());
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = xpp.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("vocab")) {
                        vocabs.add(vocab);
                    } else if (tagname.equalsIgnoreCase("lection")) {
                        lections.add(lection);
                    } else if (tagname.equalsIgnoreCase("german")) {
                        vocab.setGerman(text);
                    } else if (tagname.equalsIgnoreCase("english")) {
                        vocab.setForeign(text);
                    } else if (tagname.equalsIgnoreCase("number")) {
                        lection.setNumber(Integer.parseInt(text));
                    }
                    break;

                default:
                    break;
            }
            eventType = xpp.next();
        }
        insertLections(lections, context);
        insertVocabs(vocabs, context);
    }

    public static void insertVocabs(ArrayList<Vocab> vocabs, Context context){
        for (Vocab vocab:
             vocabs) {
            if(VocabDatabaseHelper.getByBaseLanguage(vocab.getGerman()) == null){
                vocab.setSqlID(VocabDatabaseHelper.insert(vocab));
                try {
                    InputStream is2 = context.getAssets().open("picture/" + vocab.getForeign() + ".png");
                    int size2 = is2.available();
                    byte[] buffer2 = new byte[size2];
                    is2.read(buffer2);
                    is2.close();
                    vocab.setPicture(buffer2);
                    VocabDatabaseHelper.update(vocab);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public static void insertLections(ArrayList<Lection> lections, Context context){
        for (Lection lection:
             lections) {
            if(LectionDatabaseHelper.get(lection.getNumber()) == null){
                LectionDatabaseHelper.insert(lection);
            }
        }
    }
}
