package site.itcat.jdk.version8.construc;

/**
 *         // 代码执行顺序
 *         // 父类静态代码块
 *         // 子类静态代码块
 *         // 父类非静态代码块
 *         // 父类构造函数
 *         // 子类非静态代码块
 *         // 子类构造函数
 * @author Monster
 * @since  1.8
 * @date 2018/11/26
 */
public class Sub extends Super {
    public Sub(){
        System.out.println("sub construct");
    }

    static {
        System.out.println("sub static");
    }

    {
        System.out.println("sub non static");
    }
}
