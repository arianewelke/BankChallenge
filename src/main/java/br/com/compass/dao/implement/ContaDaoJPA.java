package br.com.compass.dao.implement;

import br.com.compass.dao.ContaDao;
import br.com.compass.entities.Conta;
import javax.persistence.EntityManager;
import java.util.List;

public abstract class ContaDaoJPA implements ContaDao {

    private EntityManager em;

    public ContaDaoJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(Conta conta) {
        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
    }

    @Override
    public void update(Conta conta) {
        em.getTransaction().begin();
        em.merge(conta);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        em.getTransaction().begin();
        em.remove(em.find(Conta.class, conta.getId()));
        em.getTransaction().commit();
    }

    @Override
    public Conta findById(int id) {
        return em.find(Conta.class, id);
    }

    @Override
    public List<Conta> findAll() {
        return em.createQuery("FROM Conta", Conta.class).getResultList();
    }

    @Override
    public Conta findByNumero(String numero) {
        return em.createQuery("FROM Conta c WHERE c.numero = :numero", Conta.class)
                .setParameter("numero", numero)
                .getSingleResult();
    }
}

