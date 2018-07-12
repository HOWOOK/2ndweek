package com.example.q.a2ndweek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import static android.net.Uri.parse;

public class Tab2Show extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tap2show);
        Intent intent = getIntent();
        ArrayList<String> imglist = (ArrayList<String>) intent.getSerializableExtra("imglist");
        // 커스텀 아답타 생성
        MyAdapter adapter = new MyAdapter (
                getApplicationContext(),
                R.layout.tap2row,       // GridView 항목의 레이아웃 row.xml
                imglist);    // 데이터

        GridView gv = (GridView)findViewById(R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

    } // end of onCreate
} // end of class

class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<String> imglist;
    LayoutInflater inf;

    public MyAdapter(Context context, int layout, ArrayList<String> imglist) {
        this.context = context;
        this.layout = layout;
        this.imglist = imglist;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imglist.size();
    }

    @Override
    public Object getItem(int position) {
        return imglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView==null)
        convertView = inf.inflate(R.layout.tap2row, parent,false);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        String uri = getItem(position).toString();
        iv.setImageURI(Uri.parse(getItem(position).toString()));
        Glide.with(convertView).load(uri).into(iv);
        return convertView;
    }
}