package br.com.compass.services.interfaces;

import br.com.compass.entities.Usuario;

public interface ContaService {
    String create(float saldo, String tipo, Usuario usuario);
}
