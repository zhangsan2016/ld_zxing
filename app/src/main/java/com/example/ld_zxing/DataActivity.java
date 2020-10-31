package com.example.ld_zxing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ld_zxing.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DataActivity extends Activity {

    private Gson gson = new Gson();
    private  List<String> datalist;
    private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ListView listView = this.findViewById(R.id.listdata);

        String strJson="";
        strJson = (String) SharedPreferencesUtils.getParam(DataActivity.this,"data",strJson);
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

        adapter = new ArrayAdapter(DataActivity.this,android.R.layout.simple_list_item_1,datalist);
        if(datalist!= null && datalist.size() > 0){
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(DataActivity.this,"当前没有保存的数据！", Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //对话框
                new AlertDialog.Builder(DataActivity.this).setTitle("操作选项")
                        .setItems(new CharSequence[] { "删除" }, new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        deliteListData(position);
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                return true;
            }
        });


    }

    private void deliteListData(int position) {
        /*String strJson = (String) SharedPreferencesUtils.getParam(DataActivity.this,"data",new String());
        List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());*/
        datalist.remove(position);

        SharedPreferencesUtils.setParam(DataActivity.this,"data",gson.toJson(datalist));
        adapter.notifyDataSetChanged();

            Toast.makeText(DataActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();


    }
}