package site.itcat.concurrent.print;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintABC {

    static class Printer {
        private boolean flagA = true;
        private boolean flagB = false;
        private boolean flagC = false;


        public void printA() {
            int i = 0;
            while (i < 5) {
                synchronized (this) {
                    while (!flagA) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + " " + "A");
                    i++;
                    flagA = false;
                    flagB = true;
                    flagC = false;
                    this.notifyAll();
                }
            }
        }


        public void printB() {
            int i =0;
            while (i < 5) {
                synchronized (this) {
                    while (!flagB) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + " " + "B");
                    i++;
                    flagB = false;
                    flagA = false;
                    flagC = true;
                    this.notifyAll();
                }
            }
        }

        public void printC() {
            int i =0;
            while (i < 5) {
                synchronized (this) {
                    while (!flagC) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(Thread.currentThread().getName() + " " + "C");
                    i++;
                    flagC = false;
                    flagA = true;
                    flagB = false;
                    this.notifyAll();
                }
            }
        }
    }


    public static void main(String[] args) {
        Printer turnTake = new Printer();

        Thread threadA = new Thread(turnTake::printA);
        Thread threadB = new Thread(turnTake::printB);
        Thread threadC = new Thread(turnTake::printC);
        threadA.setName("ThreadA");
        threadB.setName("ThreadB");
        threadC.setName("ThreadC");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
