package com.example.jdk.version8.instance;

import com.example.jdk.version8.bean.Artist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanDriven {

    public void allArtists(List<Artist> artists) {
        artists.stream()
                .count();
    }

    public static void main(String[] args) {
        List<String> beginningWithNumbers = Stream.of("a", "ab", "abc").filter(value -> false)
                .collect(Collectors.toList());
    }
}
