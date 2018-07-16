package com.example.heros.bean;

import java.io.Serializable;

/**
 * Created by DELL on 2018/7/10.
 */

public class Skill implements Serializable {
    private String skillName;
    private String skillPath;

    public Skill() {
    }

    public Skill(String skillName, String skillPath) {
        this.skillName = skillName;
        this.skillPath = skillPath;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillPath() {
        return skillPath;
    }

    public void setSkillPath(String skillPath) {
        this.skillPath = skillPath;
    }

    @Override
    public String toString() {
        return "Skill [skillName=" + skillName + ", skillPath=" + skillPath + "]";
    }
}
