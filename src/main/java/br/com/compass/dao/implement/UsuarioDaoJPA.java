package br.com.compass.dao.implement;

import br.com.compass.dao.UsuarioDao;
import br.com.compass.entities.Usuario;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuarioDaoJPA implements UsuarioDao {

    private EntityManager em;

    public UsuarioDaoJPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insert(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    @Override
    public void update(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();

    }

    @Override
    public void deleteById(Usuario usuario) {
        em.getTransaction().begin();
        em.remove(em.find(Usuario.class, usuario.getId()));
        em.getTransaction().commit();

    }

    @Override
    public Usuario findById(int id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public Usuario findById(Integer id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> findAll() {
        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    @Override
    public Usuario findByCpf(String cpf) {
        return em.createQuery("FROM Usuario u WHERE u.cpf = :cpf", Usuario.class).setParameter("cpf", cpf).getSingleResult();
    }
}
