package com.example.peiyu.mynote;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.journeyfu.www.personal_journal.R;

import java.util.List;



public class JournalAdapter extends ArrayAdapter<Journal> {
    private int resourceID;
    private MyDatabaseHelper Journal_dbHelper;
    public JournalAdapter(Context context,int textViewResourceID,List<Journal> objects){
        super(context,textViewResourceID,objects);
        resourceID = textViewResourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Journal journal = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView journal_title = (TextView) view.findViewById(R.id.tv_UserTitle);
        TextView journal_time = (TextView) view.findViewById(R.id.tv_time);
        TextView journal_content= (TextView) view.findViewById(R.id.tv_UserContent);
        if(journal.getTitle().isEmpty()){
            journal_title.setText("无标题");
        }else{
            journal_title.setText(journal.getTitle());
        }
        journal_time.setText("创建时间:".concat(journal.getTime()));
        if(journal.getContent().isEmpty()){
            journal_content.setText("未添加内容");
        }else{
            journal_content.setText(journal.getContent());
            journal_content.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        }
        return view;
    }

}
