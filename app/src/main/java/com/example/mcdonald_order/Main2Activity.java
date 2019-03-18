package com.example.mcdonald_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ListView packages;
    private Button last,next;
    private GridView gridView;
    ArrayList<String> choose_menu = new ArrayList<String>();
    ArrayList<Integer> choose_prices = new ArrayList<Integer>();
    ArrayList<Integer> choose_image = new ArrayList<Integer>();
    ArrayList<Integer> final_cost = new ArrayList<Integer>();
    ArrayList<Integer> final_count = new ArrayList<Integer>();
    private int Count[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隱藏標籤列
        getSupportActionBar().hide();
        //隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        packages = (ListView)findViewById(R.id.packages);
        next = (Button)findViewById(R.id.next);
        last = (Button)findViewById(R.id.last);
        gridView = (GridView)findViewById(R.id.menuShow);

        next.setOnClickListener(handler);
        last.setOnClickListener(handler);

        choose_menu = this.getIntent().getExtras().getStringArrayList("ChooseMenu");
        choose_prices = this.getIntent().getExtras().getIntegerArrayList("ChoosePriceFirst");
        choose_image = this.getIntent().getExtras().getIntegerArrayList("ChooseImage");

        packages.setAdapter(new listAdapter());
        gridView.setAdapter(new ImageAdapter());
    }

    //自訂List Adapter
    class listAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return choose_menu.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            if(convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.secondactivity_listview, null);
                holder.name = (TextView) convertView.findViewById(R.id.MenuName);
                holder.count = (TextView) convertView.findViewById(R.id.count);
                holder.plus = (Button) convertView.findViewById(R.id.plus);
                holder.div = (Button) convertView.findViewById(R.id.div);
                //跑馬燈設定
                holder.name.setSelected(true);
                convertView.setTag(holder);
            }else{
                holder = (Holder)convertView.getTag();
            }

            holder.name.setText(choose_menu.get(position));

            final Holder finalHolder = holder;
            //加份數
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Count[position] += 1;
                    finalHolder.count.setText(Integer.toString(Count[position])+"份"+"\t\t");
                }
            });

            //減份數
            holder.div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Count[position] > 0){
                        Count[position] -= 1;
                    }
                    finalHolder.count.setText(Integer.toString(Count[position])+"份"+"\t\t");
                }
            });

            return convertView;
        }

    }

    //自訂Griview Adapter
    class ImageAdapter extends  BaseAdapter{
        @Override
        public int getCount() {
            return choose_image.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(Main2Activity.this);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            //使用Glide載入圖片並顯示，以此降低Gridview捲動卡頓問題
            Glide.with(getApplicationContext()).load(choose_image.get(position)).into(imageView);

            return imageView;
        }
    }

    //Holder獨立出來
    private class Holder{
        TextView name;
        TextView count;
        Button plus;
        Button div;
    }

    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.next :
                    final_cost.clear();
                    final_count.clear();
                    for(int i=0 ; i < choose_menu.size() ; i++){
                        final_count.add(Count[i]);
                        final_cost.add(choose_prices.get(i) * Count[i]);
                    }

                    Intent three = new Intent(Main2Activity.this,Main3Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("finalMenu",choose_menu);
                    bundle.putIntegerArrayList("finalCount",final_count);
                    bundle.putIntegerArrayList("finalCost",final_cost);
                    three.putExtras(bundle);

                    startActivity(three);
                    break;
                case R.id.last :
                    Main2Activity.this.finish();
                    break;
            }
        }
    };
}
