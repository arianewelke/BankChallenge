package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.UsuarioDao;
import br.com.compass.entities.Usuario;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UsuarioDaoJPA implements UsuarioDao {

    @Override
    public void insert(Usuario usuario) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error inserting: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario findByCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Usuario u WHERE u.cpf = :cpf", Usuario.class)
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
    public Usuario findByCpfAndPassword(String cpf, String senha) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "FROM Usuario u WHERE u.cpf = :cpf AND u.senha = :senha", Usuario.class)
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
