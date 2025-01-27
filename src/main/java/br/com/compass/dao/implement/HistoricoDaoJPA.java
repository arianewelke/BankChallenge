package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.HistoricoDao;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Historico;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDaoJPA implements HistoricoDao {
    private EntityManager em;

    @Override
    public void insert(Historico historico) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(historico);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Historico historico) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(historico);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Historico historico) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Historico.class, historico.getId()));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Historico findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Historico.class, id);
        } finally {
            em.close();
        }
    }



//    @Override
//    public List<Historico> findByAcao(String acao) {
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            TypedQuery<Historico> query = em.createQuery("FROM Historico ", Historico.class);
//            query.setParameter("acao", acao);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<Historico> consultarPorConta(int contaId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Historico h WHERE h.conta.id = :contaId", Historico.class)
                    .setParameter("contaId", contaId)
                    .getResultList();
        } catch (NoResultException e) {
            return null; // Retorna lista vazia se nenhum resultado for encontrado
        } finally {
            em.close();
        }
    }
}



