package site.itcat.anno;

public class Child extends Parent{

    @Override
    public void test() {
        System.out.println("child test");
    }

    public void childTest() {
        System.out.println("child test");
    }
}
