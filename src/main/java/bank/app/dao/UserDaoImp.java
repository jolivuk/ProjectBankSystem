package bank.app.dao;

import bank.app.entity.User;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.SessionScope;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory factory;
    @Override
    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        Query<User> query = session.createQuery("from User ",User.class);
        List<User> allUsers = query.getResultList();
        return allUsers;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser(int id) {
        Session session = factory.getCurrentSession();
        User user = session.get(User.class,id);
        return user;
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public String checkConnection() {
        return factory.getCurrentSession().toString();
    }
}
