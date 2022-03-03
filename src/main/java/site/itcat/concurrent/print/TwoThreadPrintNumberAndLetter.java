package site.itcat.concurrent.print;

/**
 * @author : xuebengang
 * @date : 2022/3/3
 * @description : 两个线程打印数字和字母；一个线程打印字母，另一个打印数字；打印顺序12A34B...5152Z
 */
public class TwoThreadPrintNumberAndLetter {

    static class Printer {
        private int num = 1;
        private char c = 'A';
        private boolean flag = true;

        public void printNum() {
            while (num <= 52 && c <= 'Z') {
                synchronized (this) {
                    while (!flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + " " + num++);
                    System.out.println(Thread.currentThread().getName() + " " + num++);
                    flag = false;
                    this.notifyAll();
                }
            }
        }

        public void printChar() {
            while (num <= 52 && c <= 'Z') {
                synchronized (this) {
                    while (flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + " " + c++);
                    flag = true;
                    this.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Printer turnTake = new Printer();

        Thread threadA = new Thread(turnTake::printNum);
        Thread threadB = new Thread(turnTake::printChar);
        threadA.setName("ThreadA");
        threadB.setName("ThreadB");
        threadA.start();
        threadB.start();
    }

}
