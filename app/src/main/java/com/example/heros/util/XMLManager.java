package com.example.heros.util;

import android.content.Context;
import android.util.Xml;

import com.example.heros.bean.Hero;
import com.example.heros.bean.Skill;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/10.
 */

public class XMLManager {
    String[] bigImage;// 存放大图的路径的字符串数组
    String[] smallImage;// 存小图的图径的字符串数组
    String[] strjn;// 存放技能的路径的数组
    Context context;

    public XMLManager(Context context) {
        this.context = context;
    }

    public String createXML() throws Exception {
        bigImage = context.getAssets().list("bfile");
        smallImage = context.getAssets().list("sfile");
        String[] tfilelist = context.getAssets().list("tfile");
        strjn = context.getAssets().list("jnfile");
        String herosName[] = getHerosName("name.txt");

        // 创建文档
        HeroXml xml = new HeroXml();
        // 创建一个根节点
        Element root = xml.createRootElement("heros");
        for(int i = 0;i < bigImage.length;i++) {
            Element hero = xml.createElement("hero");
            // 获得所有英雄的名字
            String heroName = herosName[i];
            String names[] = heroName.split(" ");
            String name = names[0] + names[1];
            String type = names[2];

            // 获得英雄的详细信息
            String bpath = "bfile/" + bigImage[i];// 大图路径
            String spath = "sfile/" + smallImage[i];// 小图路径
            String tpath = "tfile/" + tfilelist[i];// 英雄介绍文档路径

            // 获得英雄的技能信息
            String jnpath = "jnfile/" + strjn[i];
            String str1[] = context.getAssets().list(jnpath);
            // 三级目录
            String jnImagePath = jnpath + "/" + str1[0];
            String jnTxtPath = jnpath + "/" + str1[1];
            // 三级目录下的文件
            String jnImages[] = context.getAssets().list(jnImagePath);
            String jnjieshao[] = context.getAssets().list(jnTxtPath);

            // 创建英雄的节点
            // 创建英雄的子元素
            Element ename = xml.createElement("name", name);
            Element ebpath = xml.createElement("bpath", bpath);
            Element espath = xml.createElement("spath", spath);
            Element heropath = xml.createElement("heropath", tpath);
            Element herotype = xml.createElement("type", type);
            // 创建一人技能节点
            Element skill = xml.createElement("skill");
            // 创建技能的子节点
            for (int j = 0; j < jnImages.length; j++) {
                Element skillname = xml.createElement("skillname", jnTxtPath + "/" + jnjieshao[j]);
                Element skillImage = xml.createElement("skillurl", jnImagePath + "/" + jnImages[j]);

                skill.appendChild(skillname);
                skill.appendChild(skillImage);
            }
            hero.appendChild(ename);
            hero.appendChild(espath);
            hero.appendChild(ebpath);
            hero.appendChild(heropath);
            hero.appendChild(herotype);
            hero.appendChild(skill);

            // 将英雄添加到根节点上
            root.appendChild(hero);
        }
        return xml.xmlToString();
    }

    // 读取name.txt文件获得字符串后按逗号分隔，拿到英雄的名字
    private String[] getHerosName(String str) throws IOException {
        InputStream is = context.getAssets().open(str);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String names = sb.toString();
        return names.split(",");
    }

    public List<Hero> parseXML(String strXml) throws Exception {
        List<Hero> heros = null;
        InputStream is = new ByteArrayInputStream(strXml.getBytes());
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        Hero hero = null;
        Skill skill = null;
        List<Skill> skills = null;
        int event = parser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    // 创建一个hero集合，存放解析出来的hero数据
                    heros = new ArrayList<Hero>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("hero".equals(parser.getName())) {
                        // 进入一个hero元素
                        hero = new Hero();
                        skills = new ArrayList<Skill>();
                    } else if ("name".equals(parser.getName())) {
                        hero.setName(parser.nextText());
                    } else if ("bpath".equals(parser.getName())) {
                        hero.setBpath(parser.nextText());
                    } else if ("spath".equals(parser.getName())) {
                        hero.setSpath(parser.nextText());
                    } else if ("heropath".equals(parser.getName())) {
                        hero.setHeroPath(parser.nextText());
                    } else if ("type".equals(parser.getName())) {
                        hero.setType(parser.nextText());
                    } else if ("skill".equals(parser.getName())) {
                    } else if ("skillname".equals(parser.getName())) {
                        // 解析技能，创建一个技能对象
                        skill = new Skill();
                        skill.setSkillName(parser.nextText());
                    } else if ("skillurl".equals(parser.getName())) {
                        skill.setSkillPath(parser.nextText());
                        if (skill.getSkillName() != null && skill.getSkillPath() != null) {
                            // 技能和图片都封装完成，把技能加到skills集合中
                            skills.add(skill);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("hero".equals(parser.getName())) {
                        // 一个英雄数据封装完成，将其添加到heros集合中
                        hero.setSkills(skills);
                        heros.add(hero);
                    }
                    break;
                default:
                    break;
            }
            event = parser.next();

        }

        return heros;
    }
}
