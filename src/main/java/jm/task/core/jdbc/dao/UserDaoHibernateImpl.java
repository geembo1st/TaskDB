package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.QueryProducer;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE test ( id int, name varchar(45), lastName varchar(45), age int)");
                    query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE test ");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        User user;
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class,id);
            if(user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null ) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null ) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
