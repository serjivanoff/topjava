package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
        ValidationUtil.USERS.forEach(this::save);
    }

    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if(repository.remove(id)==null) return false;else
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if(user.isNew())user.setId(counter.incrementAndGet());
        repository.put(user.getId(),user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User>result=new ArrayList<>(repository.values());
        Comparator<User>compareByName= (o1, o2) -> o1.getName().compareTo(o1.getName());
        Collections.sort(result,compareByName);
        return result;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        User result=null;
        for(Map.Entry<Integer,User> u:repository.entrySet()){
            if(u.getValue().getEmail().equals(email))result=u.getValue();
        }
        return result;
    }
}
