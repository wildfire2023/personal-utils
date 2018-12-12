package com.example.design.pattern.creational.simplefactory;

/**
 * 简单工厂
 * @author Monster
 * @date 2018/12/12 23:00
 * @since
 */
public final class VideoFactory {

    /**
     * 缺点：不满足开闭原则，过于臃肿
     * @param type
     * @return
     */
    public Video getVideo(String type) {
        if ("Java".equalsIgnoreCase(type)) {
            return new JavaVideo();
        } else if ("Python".equalsIgnoreCase(type)) {
            return new PythonVideo();
        }
        return null;
    }


    public Video getVideo1(Class c) {
        Video video = null;
        try {
            video = (Video) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return video;
    }

}
