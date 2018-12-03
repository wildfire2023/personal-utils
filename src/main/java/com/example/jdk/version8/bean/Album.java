package com.example.jdk.version8.bean;

import java.util.List;

/**
 * <p>专辑</p>
 * @author Monster
 * @since  1.8
 * @date 2018/11/24
 */
public class Album {
    /**
     * 专辑名
     */
    private String name;
    /**
     * 专辑上的所有曲目的列表
     */
    private List<Track> tracks;
    /**
     * 参与创作本专辑的艺术家列表
     */
    private List<String> musicians;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<String> getMusicians() {
        return musicians;
    }

    public void setMusicians(List<String> musicians) {
        this.musicians = musicians;
    }
}
