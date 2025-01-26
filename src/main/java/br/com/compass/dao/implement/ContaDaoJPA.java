package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.ContaDao;
import br.com.compass.entities.Conta;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContaDaoJPA implements ContaDao {

    @Override
    public void insert(Conta conta) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(conta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Conta conta) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(conta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Conta.class, id));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Conta findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Conta.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Conta> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Conta> query = em.createQuery("FROM Conta", Conta.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Conta findByNumero(String numero) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Conta c WHERE c.numero = :numero", Conta.class)
                    .setParameter("numero", numero)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Boolean existsAccountByUsuarioIdAndTipo(int usuarioId, String tipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Conta> contas = em.createQuery(
                            "FROM Conta c WHERE c.usuario.id = :usuarioId AND c.tipo = :tipo", Conta.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("tipo", tipo)
                    .getResultList();
            return !contas.isEmpty();
        } finally {
            em.close();
        }
    }

    @Override
    public Conta findByUsuarioIdAndNumero(int usuarioId, String numero) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "FROM Conta c WHERE c.usuario.id = :usuarioId AND c.numero = :numero", Conta.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("numero", numero)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}



