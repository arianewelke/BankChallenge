package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.UsuarioDao;
import br.com.compass.entities.Usuario;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDaoJPA implements UsuarioDao {
    
    @Override
    public void insert(Usuario usuario) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    @Override
    public void update(Usuario usuario) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Usuario.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    @Override
    public Usuario findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("FROM Usuario", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return List.of();
    }

    @Override
    public Usuario findByCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Usuario u =  em.createQuery("FROM Usuario u WHERE u.cpf = :cpf", Usuario.class).setParameter("cpf", cpf).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuario por cpf: " + e.getMessage());
        }
        return null;
    }
}
