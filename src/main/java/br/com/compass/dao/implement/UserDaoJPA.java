package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.UserDao;
import br.com.compass.entities.User;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDaoJPA implements UserDao {

    @Override
    public void insert(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error inserting: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public User findByCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM User u WHERE u.cpf = :cpf", User.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Error searching for user: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public User findByCpfAndPassword(String cpf, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "FROM User u WHERE u.cpf = :cpf AND u.password = :password", User.class)
                    .setParameter("cpf", cpf)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
