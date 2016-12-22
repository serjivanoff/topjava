package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 14.05.2014
 */
public class ValidationUtil {
    public static final List<User>USERS=Arrays.asList(new User(0,"Васисуалий","user@ya.ru","user", Role.ROLE_USER),
            new User(1,"Вазззя","admin@ya.ru","admin", Role.ROLE_ADMIN));
    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) throw new NotFoundException("Not found entity with " + msg);
    }
}
