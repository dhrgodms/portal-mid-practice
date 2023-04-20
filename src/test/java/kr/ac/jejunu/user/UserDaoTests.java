package kr.ac.jejunu.user;

import org.junit.jupiter.api.Test;

public class UserDaoTests {
    @Test
    public void get(){
        Long id = 1l;
        String name = "haeeun";
        String password = "겟1234";
        UserDao userDao = new UserDao();
        User user = userDao.get(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }
}
