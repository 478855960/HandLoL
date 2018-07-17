package com.example.heros;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.heros.adapter.HeroAdapter;
import com.example.heros.bean.Hero;
import com.example.heros.media.HeroMedia;
import com.example.heros.util.XMLManager;

import java.util.ArrayList;
import java.util.List;

public class HeroActivity extends Activity {
    GridView gvLogo;// 英雄头像
    HeroAdapter adapter;
    List<Hero> heros;// 保存英雄信息的集合
    HeroThread thread;
    ProgressDialog dialog; // 英雄分类弹框
    ImageView imgType;
    List<Hero> tempHeros;
    Button buttonQuery = null; // 搜索按钮实现
    EditText etQuery = null; // 输入框
    ImageView imgPlay = null;// 音乐播放
    ImageView imgPause = null;// 音乐暂停
    HeroMedia media = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        initialUI();
        // 创作启动工作线程
        thread = new HeroThread();
        thread.start();

        setListener();
    }

    private void initialUI(){
        media = new HeroMedia(this);
        gvLogo = (GridView) findViewById(R.id.gridview_logo);
        imgType = (ImageView)findViewById(R.id.imageviewDialog);
        buttonQuery = (Button) findViewById(R.id.buttonSousuo);
        etQuery = (EditText) findViewById(R.id.edittextSousuo);
        imgPlay = (ImageView) findViewById(R.id.imageviewPlay);
        imgPause = (ImageView) findViewById(R.id.imageviewPause);
        // 实例化tempHeros
        tempHeros = new ArrayList<Hero>();

        // 打开一个进度条对话框，显示当前正在加载数据
        dialog = new ProgressDialog(this);
        dialog.setTitle("系统提示");
        dialog.setMessage("数据加载中，请稍候...");
        // 显示对话框
        dialog.show();
    }

    private void setListener() {
        imgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(HeroActivity.this).inflate(R.layout.herotype_dialog, null);
                ListView lvType = (ListView) view.findViewById(R.id.listViewHeroDialog);

                String[] types = { "全部英雄", "战士", "法师", "刺客", "坦克", "射手", "辅助" };
                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(HeroActivity.this,
                        android.R.layout.simple_list_item_1, types);
                lvType.setAdapter(typeAdapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(HeroActivity.this);
                builder.setTitle("英雄分类");
                builder.setView(view);
                final AlertDialog typeDialog = builder.create();
                typeDialog.show();
                // 注册listview项的点击事件
                lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        // 获得指定类型英雄的信息
                        getHeroByPosition(position);
                        // tempHeros已经封装了所有符合的英雄信息
                        // 重新设置adapter中的数据
                        adapter = new HeroAdapter(HeroActivity.this, tempHeros);
                        // 重新设置一个适配器
                        gvLogo.setAdapter(adapter);
                        typeDialog.dismiss();
                    }
                });
            }
        });
        // 模糊查询
        buttonQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etQuery.getText().toString().trim();
                getHeroByName(name);
                // 把适配器中的集合进行更新
                adapter = new HeroAdapter(HeroActivity.this, tempHeros);
                // 重新设置一个适配器
                gvLogo.setAdapter(adapter);
            }
        });
        // 音乐播放
        imgPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgPlay.setVisibility(View.GONE);
                imgPause.setVisibility(View.VISIBLE);
                // 音乐开启
                media.start();
            }
        });
        // 音乐停止
        imgPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgPlay.setVisibility(View.VISIBLE);// 播放显示
                imgPause.setVisibility(View.GONE); // 暂停隐藏
                // 音乐停止
                media.pause();
            }
        });
        gvLogo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int position, long arg3) {
                // 获得选择的英雄对象
                Hero hero = (Hero) adapter.getItem(position);
                // 实现activity对象的跳转
                Intent intent = new Intent(HeroActivity.this, HeroMessActivity.class);
                // 传载Serializable对象，把序列化特性的hero对象传给下一个activity
                intent.putExtra("hero", hero);
                startActivity(intent);
            }

        });
    }

    private void getHeroByName(String name) {
        tempHeros.clear();
        for (int i = 0; i < heros.size(); i++) {
            Hero hero = heros.get(i);
            if (hero.getName().contains(name)) {
                // 把符合条件的英雄添加到集合中
                tempHeros.add(hero);
            }
        }
    }

    private void getHeroByPosition(int position) {
        tempHeros.clear();
        if (position == 0) {
            // 查找所有的英雄
            tempHeros.addAll(heros);
        } else {
            // 按指定类型查
            for (int i = 0; i < heros.size(); i++) {
                if (position == Integer.parseInt(heros.get(i).getType())) {
                    tempHeros.add(heros.get(i));
                }
            }
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            heros = (List<Hero>) msg.obj;// 获得从工作子线程中加载完成后传回的集合
            adapter = new HeroAdapter(HeroActivity.this, heros);
            gvLogo.setAdapter(adapter);
            // 关闭对话框
            dialog.dismiss();
        };
    };

    // XML构造与解析
    class HeroThread extends Thread {
        @Override
        public void run() {
            // 执行数据创建和解析
            XMLManager manager = new XMLManager(HeroActivity.this);
            try {
                String strXml = manager.createXML();
                heros = manager.parseXML(strXml);
                // 从消息值中拿一个空闲可用的消息
                Message msg = handler.obtainMessage();
                // 把数据封装到消息中
                msg.obj = heros;
                // 当数据加载完成后发消息给主线程
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击回退键，进行判断
        if (keyCode == KeyEvent.KEYCODE_BACK){
            media.stop();
        }
        return super.onKeyDown(keyCode, event);
    }
}
