package com.example.mcdonald_order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    ArrayList<String> final_menu = new ArrayList<String>();
    ArrayList<Integer> final_count = new ArrayList<Integer>();
    ArrayList<Integer> final_cost = new ArrayList<Integer>();
    private ListView menulist;
    private String listMsg = "";
    private Button last;
    private TextView allcost,title;
    private int finaltotalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隱藏標籤列
        getSupportActionBar().hide();
        //隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        last = (Button)findViewById(R.id.last2);
        allcost = (TextView)findViewById(R.id.AllCost);
        menulist = (ListView)findViewById(R.id.menuList);
        title = (TextView)findViewById(R.id.first);

        final_menu = this.getIntent().getExtras().getStringArrayList("finalMenu");
        final_count = this.getIntent().getExtras().getIntegerArrayList("finalCount");
        final_cost = this.getIntent().getExtras().getIntegerArrayList("finalCost");

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main3Activity.this.finish();
            }
        });

        menulist.setAdapter(new listAdapter());
        for(int i=0 ; i < final_cost.size() ; i++){
            finaltotalCost += final_cost.get(i);
        }
        title.setText("餐點"+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+"份數"+"\t\t\t\t\t\t\t\t"+"總計");
        allcost.setText("總計 : "+"\t\t\t\t\t\t\t$"+finaltotalCost);
    }

    //自訂List Adapter
    class listAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return final_menu.size();
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.thirdactivity_listview, null);
                holder.item = (TextView) convertView.findViewById(R.id.item);
                holder.number = (TextView) convertView.findViewById(R.id.number);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                //跑馬燈設定
                holder.item.setSelected(true);
                convertView.setTag(holder);

            }else{
                holder = (Holder)convertView.getTag();
            }
            holder.item.setText(final_menu.get(position));
            holder.number.setText(final_count.get(position)+"份");
            holder.price.setText("$"+final_cost.get(position));
            return convertView;
        }

    }

    //Holder獨立出來
    private class Holder{
        TextView item;
        TextView number;
        TextView price;
    }


}
