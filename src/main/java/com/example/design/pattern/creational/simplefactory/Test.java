package com.example.design.pattern.creational.simplefactory;

/**
 * 客户层代码
 * @author Monster
 * @date 2018/12/12 22:57
 * @since
 */
public class Test {

    public static void main(String[] args) {
        VideoFactory factory = new VideoFactory();
        Video video = factory.getVideo("Java");
        if (video == null) {
            return;
        }
        video.produce();
    }
}
