package site.itcat.design.proxy;

public class UserService implements IUserService{

    @Override
    public String queryUserNameById(String userId) {
        String s = "原名称: " + userId;
        System.out.println(s);
        return s;
    }
}
