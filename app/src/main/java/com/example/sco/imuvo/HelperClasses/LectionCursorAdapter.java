package com.example.sco.imuvo.HelperClasses;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sco.imuvo.R;

/**
 * Created by sco on 17.12.2016.
 */

public class LectionCursorAdapter extends ResourceCursorAdapter {
    public LectionCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

//    public LectionCursorAdapter(Context context, Cursor c, boolean autoRequery) {
//        super(context, c, autoRequery);
//    }
//
//    public LectionCursorAdapter(Context context, Cursor c, int flags) {
//        super(context, c, flags);
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.embedded_list_view_vocabulary,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView german = (TextView) view.findViewById(R.id.german);
        TextView foreign = (TextView) view.findViewById(R.id.foreign);
        TextView lection = (TextView) view.findViewById(R.id.lection);

        //int lectionNo = cursor.getInt(1);

        //german.setText("Lektion");
        //foreign.setText(Integer.toString(lectionNo));
        //lection.setText(cursor.getString(2));

        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setText(cursor.getString(cursor.getColumnIndex("number")));
        //foreign.setText(cursor.getString(cursor.getColumnIndex("number")));
        //lection.setText(cursor.getString(cursor.getColumnIndex("lection")));

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent menuIntent = new Intent(context,VocabularyList.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("lectionNumber",lectionNo);
//                menuIntent.putExtras(bundle);
//                context.startActivity(menuIntent);
//            }
//        });
    }
}
