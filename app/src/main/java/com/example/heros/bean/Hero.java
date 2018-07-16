package com.example.heros.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2018/7/10.
 */

// 实现序列化 Serializable
public class Hero implements Serializable {
    private String name;
    private String spath;
    private String bpath;
    private String heroPath;
    private String type;
    private List<Skill> skills;

    public Hero() {
    }

    public Hero(String name, String spath, String bpath, String heroPath, String type, List<Skill> skills) {
        this.name = name;
        this.spath = spath;
        this.bpath = bpath;
        this.heroPath = heroPath;
        this.type = type;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpath() {
        return spath;
    }

    public void setSpath(String spath) {
        this.spath = spath;
    }

    public String getBpath() {
        return bpath;
    }

    public void setBpath(String bpath) {
        this.bpath = bpath;
    }

    public String getHeroPath() {
        return heroPath;
    }

    public void setHeroPath(String heroPath) {
        this.heroPath = heroPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Hero [name=" + name + ", spath=" + spath + ", bpath=" + bpath + ", heroPath=" + heroPath + ", type="
                + type + ", skills=" + skills + "]";
    }

}
