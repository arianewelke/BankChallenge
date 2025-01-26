package br.com.compass.dao.implement;

import br.com.compass.dao.Interfaces.UsuarioDao;
import br.com.compass.entities.Conta;
import br.com.compass.entities.Usuario;
import br.com.compass.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        } finally {
            em.close();
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
            System.out.println("Erro ao atualizar usuário" + e);
        } finally {
            em.close();
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
            System.out.println("Erro ao deletar usuário" + e);
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
        } finally {
            em.close();
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
        } finally {
            em.close();
        }
        return List.of();
    }

    @Override
    public Usuario findByCpf(String cpf) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Usuario u WHERE u.cpf = :cpf", Usuario.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Boolean existsAccountByUsuarioIdAndTipo(int usuarioId, String tipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            List<Conta> contas = em.createQuery("FROM Conta c WHERE c.usuario.id = :usuarioId AND c.tipo = :tipo", Conta.class).setParameter("usuarioId", usuarioId).setParameter("tipo", tipo).getResultList();
            return !contas.isEmpty();
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
