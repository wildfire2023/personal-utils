package site.itcat.concurrent.print;

/**
 * @author : xuebengang
 * @date : 2022/3/3
 * @description : 两个线程轮流打印100
 */
public class TwoThreadPrint100 {


    static class TurnTake {
        private int count;
        private volatile boolean flag;
        private final Object lock;

        public TurnTake(int count) {
            this.count = count;
            flag = true;
            lock = new Object();
        }

        public void printA(){
            while (count < 100) {
                synchronized (lock) {
                    while (!flag) {

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + count++);
                    flag = false;
                    lock.notifyAll();
                }
            }
        }

        public void printB() {
            while (count < 100) {
                synchronized (lock) {
                    while (flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + count++);
                    flag = true;
                    lock.notifyAll();
                }

            }
        }



    }


    public static void main(String[] args) {
        TurnTake turnTake = new TurnTake(1);

        Thread threadA = new Thread(turnTake::printA);
        Thread threadB = new Thread(turnTake::printB);
        threadA.setName("ThreadA");
        threadB.setName("ThreadB");
        threadA.start();
        threadB.start();
    }

}
