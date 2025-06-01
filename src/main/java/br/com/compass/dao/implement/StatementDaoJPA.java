package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.StatementDao;
import br.com.compass.entities.Statement;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class StatementDaoJPA implements StatementDao {
    private EntityManager em;

    @Override
    public void insert(Statement statement) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(statement);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Statement> consultationAccount(int accountId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Statement h WHERE h.account.id = :accountId", Statement.class)
                    .setParameter("accountId", accountId)
                    .getResultList();
        } catch (NoResultException e) {
            return null; // Retorna lista vazia se nenhum resultado for encontrado
        } finally {
            em.close();
        }
    }
}



