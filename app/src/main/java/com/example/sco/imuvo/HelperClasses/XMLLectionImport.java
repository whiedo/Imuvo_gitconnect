package com.example.sco.imuvo.HelperClasses;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.Model.Lection;
import com.example.sco.imuvo.Model.Vocab;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLLectionImport {

    public static ArrayList<Lection> lections;
    public static ArrayList<Vocab> vocabs;
    private static int numberImportedLections;
    private static int numberImportedVocabs;

    public static int getNumberImportedLections() {
        return numberImportedLections;
    }

    public static int getNumberImportedVocabs() {
        return numberImportedVocabs;
    }

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
            parse(xpp);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parse(XmlPullParser xpp) throws XmlPullParserException, IOException {
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
                    } else if (tagname.equalsIgnoreCase("picture")){
                        vocab.setPicture(Base64.decode(text, Base64.DEFAULT));
                    }
                    break;

                default:
                    break;
            }
            eventType = xpp.next();
        }
        insertLections(lections);
        insertVocabs(vocabs);
    }

    public static void insertVocabs(ArrayList<Vocab> vocabs){
        numberImportedVocabs = 0;
        for (Vocab vocab:
             vocabs) {
            if(VocabDatabaseHelper.getByBaseLanguage(vocab.getGerman()) == null){
                VocabDatabaseHelper.insert(vocab);
                numberImportedVocabs += 1;
            }

        }
    }
    public static void insertLections(ArrayList<Lection> lections){
        numberImportedLections = 0;
        for (Lection lection:
             lections) {
            if(LectionDatabaseHelper.get(lection.getNumber()) == null){
                LectionDatabaseHelper.insert(lection);
                numberImportedLections += 1;
            }
        }
    }
}
