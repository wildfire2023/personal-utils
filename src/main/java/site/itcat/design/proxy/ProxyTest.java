package site.itcat.design.proxy;

public class ProxyTest {

    public static void main(String[] args) {
//        IUserService proxy = JDKInvocationHandler.getProxy(IUserService.class);
//        proxy.queryUserNameById("test");
        CglibProxy c = new CglibProxy();
        UserService userService = (UserService) c.newInstance(new UserService());
        userService.queryUserNameById("test");
    }
}
