package site.itcat.anno;

@InheritedTest(value = "parent class")
public class Parent {

    @InheritedTest(value = "parent method")
    public void test() {
        System.out.println("parent test");
    }
}
