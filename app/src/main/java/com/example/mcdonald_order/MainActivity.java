package com.example.mcdonald_order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView FOODMENU;
    private Button total;
    private String menu[] = {"大麥克($135)","麥香雞($105)","雙層牛肉吉事堡($105)","安格斯黑牛堡($125)","嫩煎雞腿堡($155)","千島黃金蝦堡($145)","勁辣雞腿堡($115)","麥香魚($95)","黃金起司豬排堡($99)","蕈菇安格斯黑牛堡($146)","麥克雞塊(6塊 $105)","凱薩脆雞沙拉($135)","義式烤雞沙拉($125)"};
    private int Image[] = {R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5
            ,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10
            ,R.drawable.b11,R.drawable.b12,R.drawable.b13};
    private int singlePrice[] = {115,105,105,125,155,145,115,95,99,146,105,135,125};
    ArrayList<String> choose_item = new ArrayList<String>();
    ArrayList<Integer> choose_item_price = new ArrayList<Integer>();
    ArrayList<Integer> choose_item_image = new ArrayList<Integer>();
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隱藏標籤列
        getSupportActionBar().hide();
        //隱藏狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FOODMENU = (ListView)findViewById(R.id.menu);
        total = (Button)findViewById(R.id.button);
        ArrayAdapter<String> MyAdapter = new ArrayAdapter<String>(this,R.layout.list_item,menu);
        FOODMENU.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        FOODMENU.setAdapter(MyAdapter);
        count = FOODMENU.getCount();

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_item.clear();
                choose_item_price.clear();
                choose_item_image.clear();
                for(int i = 0 ; i < count ; i++){
                    if(FOODMENU.isItemChecked(i)){
                        choose_item.add(menu[i]);
                        choose_item_price.add(singlePrice[i]);
                        choose_item_image.add(Image[i]);
                    }
                }
                Intent two = new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ChooseMenu",choose_item);
                bundle.putIntegerArrayList("ChoosePriceFirst",choose_item_price);
                bundle.putIntegerArrayList("ChooseImage",choose_item_image);
                two.putExtras(bundle);

                startActivity(two);
            }
        });
    }
}
