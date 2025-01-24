package br.com.compass.dao.implement;

import br.com.compass.dao.ContaDao;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class ContaDaoJPA implements ContaDao {

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
    public void deleteById(Conta conta) {
        em.getTransaction().begin();
        em.remove(em.find(Usuario.class, conta.getId()));
        em.getTransaction().commit();
    }

    @Override
    public Conta findById(int id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        em.getTransaction().begin();
        Conta conta = em.find(Conta.class, id);
        if (conta != null) {
            em.remove(conta);
        }
        em.getTransaction().commit();
    }

    @Override
    public Conta findById(Integer id) {
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

