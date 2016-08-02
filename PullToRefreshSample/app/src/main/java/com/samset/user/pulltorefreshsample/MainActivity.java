package com.samset.user.pulltorefreshsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.samset.user.pulltorefreshsample.pullrefresh.PullRefreshLayout;
import com.samset.user.pulltorefreshsample.pullrefresh.PullRefreshListener;
import com.samset.user.pulltorefreshsample.pullrefresh.SunLayout;

public class MainActivity extends AppCompatActivity {

    String[] array;
    private PullRefreshLayout materialRefreshLayout;
    SunLayout sunLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sunLayout=new SunLayout(this);


        array = new String[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = "samsetdev" + i;
        }

        final ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
        materialRefreshLayout = (PullRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setSunStyle(true);
        materialRefreshLayout.setMaterialRefreshListener(new PullRefreshListener() {
            @Override
            public void onRefresh(final PullRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        array = new String[8];
                        for (int i = 0; i < array.length; i++) {
                            array[i] = "samsetdev.blog" + i;
                        }
                        listView.setAdapter(new android.widget.ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array));
                    }
                }, 3000);

            }

            @Override
            public void onfinish() {
                Toast.makeText(MainActivity.this, "Update", Toast.LENGTH_LONG).show();
            }


        });

}
}
