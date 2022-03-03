package site.itcat.concurrent.print;

/**
 * @author : xuebengang
 * @date : 2022/3/3
 * @description : 要求线程A执行完毕才开始执行线程B
 */
public class WaitThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("线程" + getName() + "执行...");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyThread myThreadA = new MyThread();
        MyThread myThreadB = new MyThread();

        myThreadA.setName("myThreadA");
        myThreadB.setName("myThreadB");

        myThreadA.start();
        // 等待myTheadA线程死亡
        myThreadA.join();
        myThreadB.start();
        myThreadB.join();
    }
}
