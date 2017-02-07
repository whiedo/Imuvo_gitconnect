package com.example.sco.imuvo.HelperClasses;

import android.content.Context;

import com.example.sco.imuvo.Model.Lection;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.Model.Vocab;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Simon Cox on 17.12.2016.
 */

public class InitData {
    static Context glbContext;

    private static void insertLection(){
        if(!LectionDatabaseHelper.getAll().moveToFirst()){
            insertLection(1,"Englisch");
            insertLection(2,"Englisch");
            insertLection(3,"Englisch");
            insertLection(4,"Englisch");
            insertLection(5,"Englisch");
            insertLection(6,"Englisch");
        }
    }

    private static void insertLection(int i, String englisch) {
        LectionDatabaseHelper.insert(new Lection(i,englisch));
    }

    private static void insertUser(String username, String password) {
        UserDatabaseHelper.insert(new User(username,password));
    }

    private static void insertUser(){

        if(!UserDatabaseHelper.getAll().moveToFirst()){
            insertUser("Simon","Simon");
            insertUser("User","Passwort");
        }
    }

    private static void insertVocabs() {

        if(!VocabDatabaseHelper.getAll().moveToFirst()) {

            insertVocab("Hallo", "Hello", 1);
            insertVocab("guten Morgen", "good morning", 1);
            insertVocab("Wie heißt du", "What is your name", 1);
            insertVocab("Ich heiße", "My name is", 1);
            insertVocab("Name", "name", 1);
            insertVocab("wie geht es dir", "How are you", 1);
            insertVocab("Mir geht es gut", "I am fine", 1);
            insertVocab("Dankeschön", "thank you", 1);
            insertVocab("Bitte", "please", 1);
            insertVocab("Auf wiedersehen", "Good bye", 1);
            insertVocab("Bis bald", "See you soon", 1);
            insertVocab("Ich wohne in", "I live in", 1);
            insertVocab("schön dich kennen zu lernen", "Nice to meet you", 1);
            insertVocab("Ich bin", "I am", 1);
            insertVocab("Du bist", "you are", 1);
            insertVocab("Wir sind", "we are", 1);
            insertVocab("ihr seit", "you are", 1);
            insertVocab("er ist", "he is", 1);
            insertVocab("sie ist", "she is", 1);
            insertVocab("es ist", "it is", 1);
            insertVocab("Sie sind", "they are", 1);
            insertVocab("und", "and", 1);

            insertVocab("Mutter", "mother", 2);
            insertVocab("Vater", "father", 2);
            insertVocab("Bruder", "brother", 2);
            insertVocab("Schwester", "sister", 2);
            insertVocab("Oma", "Grandma", 2);
            insertVocab("Opa", "Grandpa", 2);
            insertVocab("Cousine /Cousin", "cousine", 2);
            insertVocab("Tante", "aunt", 2);
            insertVocab("Onkel", "uncle", 2);
            insertVocab("Das ist mein /meine", "This is my", 2);
            insertVocab("Ich habe", "I have", 2);
            insertVocab("ein", "a", 2);
            insertVocab("Baby", "baby", 2);
            insertVocab("Familie", "family", 2);
            insertVocab("Verwandte", "relatives", 2);

            insertVocab("1", "one", 3);
            insertVocab("2", "two", 3);
            insertVocab("3", "three", 3);
            insertVocab("4", "four", 3);
            insertVocab("5", "five", 3);
            insertVocab("6", "six", 3);
            insertVocab("7", "seven", 3);
            insertVocab("8", "eight", 3);
            insertVocab("9", "nine", 3);
            insertVocab("10", "ten", 3);
            insertVocab("rot", "red", 3);
            insertVocab("grün", "green", 3);
            insertVocab("gelb", "yellow", 3);
            insertVocab("blau", "blue", 3);
            insertVocab("schwarz", "black", 3);
            insertVocab("weiß", "white", 3);
            insertVocab("braun", "brown", 3);
            insertVocab("orange", "orange", 3);
            insertVocab("grau", "grey", 3);
            insertVocab("lila", "purple", 3);
            insertVocab("rosa", "pink", 3);

            insertVocab("Hund", "dog", 4);
            insertVocab("Katze", "cat", 4);
            insertVocab("Maus", "mouse", 4);
            insertVocab("Vogel", "bird", 4);
            insertVocab("Ente", "duck", 4);
            insertVocab("Schmetterling", "butterfly", 4);
            insertVocab("Schildkröte", "turtle", 4);
            insertVocab("Kuh", "cow", 4);
            insertVocab("Pferd", "horse", 4);
            insertVocab("Huhn", "chicken", 4);
            insertVocab("Schwein", "pig", 4);
            insertVocab("Schaf", "sheep", 4);
            insertVocab("Ameisenbär", "anteater", 4);
            insertVocab("Antilope", "antelope", 4);
            insertVocab("Bär", "bear", 4);
            insertVocab("Biber", "beaver", 4);
            insertVocab("Dachs", "badger", 4);
            insertVocab("Dackel", "dachshund", 4);
            insertVocab("Delfin", "dolphin", 4);
            insertVocab("Eichhörnchen", "squirrel", 4);
            insertVocab("Eisbär", "polar bear", 4);
            insertVocab("Elefant", "elephant", 4);
            insertVocab("Elch", "moose", 4);
            insertVocab("Faultier", "sloth", 4);
            insertVocab("Feldhase", "hare", 4);
            insertVocab("Fischotter", "otter", 4);
            insertVocab("Fledermaus", "bat", 4);
            insertVocab("Fuchs", "fox", 4);

            insertVocab("Lehrer", "teacher", 5);
            insertVocab("Schüler", "pupil", 5);
            insertVocab("Schreibtisch", "desk", 5);
            insertVocab("Stuhl", "chair", 5);
            insertVocab("Stift", "pencil", 5);
            insertVocab("Radiergummi", "rubber", 5);
            insertVocab("Buch", "book", 5);
            insertVocab("Blatt", "paper", 5);
            insertVocab("Mülleimer", "bin", 5);
            insertVocab("Schere", "scissors", 5);
            insertVocab("Klassenraum", "classroom", 5);
            insertVocab("Tafel", "blackboard", 5);
            insertVocab("Schwamm", "sponge", 5);
            insertVocab("Kreide", "chalk", 5);
            insertVocab("Klassenarbeit", "classtest", 5);
            insertVocab("Kleber", "glue", 5);
            insertVocab("Fenster", "window", 5);
            insertVocab("Schulstunde", "lesson", 5);
            insertVocab("Schule", "school", 5);
            insertVocab("Stundenplan", "schedule", 5);
            insertVocab("Pause", "break", 5);

            insertVocab("Frühstück", "breakfast", 6);
            insertVocab("Mittagessen", "lunch", 6);
            insertVocab("Abendessen", "dinner", 6);
            insertVocab("Eis", "icecream", 6);
            insertVocab("Schokolade", "chocolate", 6);
            insertVocab("Suppe", "soup", 6);
            insertVocab("Spaghetti", "spaghetti", 6);
            insertVocab("Pommes frites", "chips", 6);
            insertVocab("Apfel", "apple", 6);
            insertVocab("Banane", "banana", 6);
            insertVocab("Birne", "pear", 6);
            insertVocab("Trauben", "grape", 6);
            insertVocab("Nachtisch", "desert", 6);
            insertVocab("Brot", "bread", 6);
            insertVocab("Butter", "butter", 6);
            insertVocab("Käse", "cheese", 6);
            insertVocab("Marmelade", "jelly", 6);
            insertVocab("essen", "eat", 6);
            insertVocab("kochen", "cook", 6);
            insertVocab("Küche", "kitchen", 6);
        }

    }

    public static void insertVocabWithSpeech(final Vocab currVocab){
        currVocab.setSqlID(VocabDatabaseHelper.insert(currVocab));
        try {
            InputStream is2 = glbContext.getAssets().open("picture/" + currVocab.getForeign() + ".png");
            int size2 = is2.available();
            byte[] buffer2 = new byte[size2];
            is2.read(buffer2);
            is2.close();
            currVocab.setPicture(buffer2);
            VocabDatabaseHelper.update(currVocab);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void insertVocab(String deutsch, String german, int i) {
        insertVocabWithSpeech(new Vocab(deutsch,german,i));
    }

    public static void initSQLData(Context context) {
        GeneralDatabaseHelper generalDatabaseHelper = GeneralDatabaseHelper.getInstance(context);
        generalDatabaseHelper.Create();
        glbContext =context;
       if(generalDatabaseHelper.checkDatabase()){
            insertLection();
            insertUser();
            insertVocabs();
       }

    }
}
