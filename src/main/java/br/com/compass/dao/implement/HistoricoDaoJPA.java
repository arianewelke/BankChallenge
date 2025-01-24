package br.com.compass.dao.implement;

import br.com.compass.dao.HistoricoDao;
import br.com.compass.entities.Historico;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class HistoricoDaoJPA implements HistoricoDao {
    private EntityManager em;
    public HistoricoDaoJPA(EntityManager em) {
        this.em = em;
    }


    @Override
    public void insert(Historico historico) {
        em.getTransaction().begin();
        em.persist(historico);
        em.getTransaction().commit();

    }

    @Override
    public void update(Historico historico) {
        em.getTransaction().begin();
        em.merge(historico);
        em.getTransaction().commit();

    }

    @Override
    public void deleteById(Integer id) {
        em.getTransaction().begin();
        em.remove(em.find(Historico.class, historico.getId()));
        em.getTransaction().commit();
    }

    @Override
    public Historico findById(int id) {
        return null;
    }

    @Override
    public List<Historico> findAll() {
        return List.of();
    }

    @Override
    public List<Historico> findByContaId(Integer contaId) {
        return List.of();
    }
}
