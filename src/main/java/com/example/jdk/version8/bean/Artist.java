package com.example.jdk.version8.bean;

import java.util.List;

/**
 * <p>创作音乐的个人或团队</p>
 *
 * @author Monster
 * @date 2018/11/24
 * @since 1.8
 */
public class Artist {
    /**
     * 艺术家的名字
     */
    private String name;
    /**
     * 乐队成员,该字段可为空
     */
    private List<String> members;
    /**
     * 乐队来自哪里
     */
    private String origin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
