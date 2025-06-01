package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.AccountDao;
import br.com.compass.entities.Account;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class AccountDaoJPA implements AccountDao {

    @Override
    public void insert(Account account) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Account account) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Account findByNumber(String number) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Account c WHERE c.number = :number", Account.class)
                    .setParameter("number", number)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Boolean existsAccountByUserIdAndType(int userId, String type) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Account> accounts = em.createQuery(
                            "FROM Account c WHERE c.user.id = :userId AND c.type = :type", Account.class)
                    .setParameter("userId", userId)
                    .setParameter("type", type)
                    .getResultList();
            return !accounts.isEmpty();
        } finally {
            em.close();
        }
    }

    @Override
    public Account findByUserIdAndNumber(int userId, String number) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "FROM Account c WHERE c.user.id = :userId AND c.number = :number", Account.class)
                    .setParameter("userId", userId)
                    .setParameter("number", number)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}



