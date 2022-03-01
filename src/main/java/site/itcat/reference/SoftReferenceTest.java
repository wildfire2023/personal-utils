package site.itcat.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        // 字符串常量池
        String s = new String("1");
        String s1 = s.intern();
        String s2 = "1";
        System.out.println(s == s1);
        System.out.println(s1 == s2); // true
    }

    /**
     * 虚引用：无法通过虚引用获取对象。当出现垃圾回收时，对象会被回收，同时会把该对象对应的虚引用添加到ReferenceQueue中
     * 可用于监控对象被垃圾回收器回收
     */
    private static void phantomReference() throws InterruptedException {
        ReferenceQueue referenceQue = new ReferenceQueue();
        PhantomReference<XBGTest> xhouyuPhantomReference = new PhantomReference<>(new XBGTest(), referenceQue);
        System.out.println(xhouyuPhantomReference.get());

        System.out.println(referenceQue.poll());
        System.gc();
        Thread.sleep(1000);
        System.out.println(referenceQue.poll());


    }

    /**
     * 弱引用：弱引用指向的对象，在垃圾回收发生时，一定会被回收掉
     */
    private static void weakReference() throws InterruptedException {
        WeakReference<XBGTest> zhouyuWeakReference = new WeakReference<XBGTest>(new XBGTest());
        System.out.println(zhouyuWeakReference.get());
        // gc后，XBGTest这个对象被垃圾回收
        System.gc();
        Thread.sleep(1000);
        System.out.println(zhouyuWeakReference.get());
    }

    /**
     * 软引用，对于一个对象，如果只有软引用指向了它，在堆空间足够时，它不会被垃圾回收掉，但是如果堆空间不够了，那么就回收该对象
     * 注意：并不是垃圾回收一定会回收掉软引用指向的对象。只有在堆空间不足够时，该对象才会被垃圾回收
     */
    private static void softReferenceTest() throws InterruptedException {

        SoftReference<XBGTest> xbgTestSoftReference = new SoftReference<XBGTest>(new XBGTest());
        System.out.println(xbgTestSoftReference.get());
        // 手动gc
        System.gc();
        Thread.sleep(1000);
        System.out.println(xbgTestSoftReference.get());
    }

    /**
     * 对于一个对象，只要有强引用指向了它，它就不会被垃圾回收器回收，就算堆空间不够，也不会被回收掉，而是抛出OOM
     */
    private static void strongReferenceTest() {
        XBGTest xbgTest = new XBGTest();
        XBGTest xbgTest1 = new XBGTest();
    }


}
