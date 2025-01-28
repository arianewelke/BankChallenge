# Bank Challenge

## Descrição
Este é um sistema bancário desenvolvido em **Java** com um banco de dados relacional (**MySQL**). Ele permite a abertura de contas, login, consultas de saldo, saques, depósitos, transferências e visualização de extrato.

## Funcionalidades
- **Abertura de Conta**: Permite a criação de contas de diferentes tipos.
-  **Login**: O usuário pode acessar sua conta mediante CPF e senha.
-  **Visualização de Saldo**: O saldo pode ser consultado após login.
-  **Realizar Saques**: Saque de valores conforme saldo disponível.
-  **Realizar Depósitos**: Adição de valores à conta do usuário.
-  **Realizar Transferências**: Envio de valores para outra conta.
-  **Visualizar Extrato**: Histórico completo de transações (saques, depósitos e transferências).

## Tecnologias Utilizadas
- **Java 17+**
- **JPA/Hibernate**
- **Banco de Dados Relacional** (MySQL)
- **Maven** (Gerenciamento de dependências)

## Modelagem do Banco de Dados
Abaixo está o diagrama ER do banco de dados:


![Diagrama ER](https://raw.githubusercontent.com/arianewelke/BankChallenge/refs/heads/main/assets/BankChallange-DB.png)

### Estrutura das Tabelas
#### 🔹 Tabela `usuario`
```sql
CREATE TABLE usuario(
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  cpf CHAR(11) NOT NULL UNIQUE, 
  dataNascimento DATE NOT NULL,
  senha CHAR(4) NOT NULL
);
```

#### 🔹 Tabela `conta`
```sql
CREATE TABLE conta (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuarioId INT NOT NULL,
  saldo DECIMAL(10,2) DEFAULT 0.00,
  tipo VARCHAR(20) NOT NULL,
  numero VARCHAR(50) UNIQUE NOT NULL,
  FOREIGN KEY (usuarioId) REFERENCES usuario(id)
);
```

#### 🔹 Tabela `historico`
```sql
CREATE TABLE historico ( 
  id INT AUTO_INCREMENT PRIMARY KEY,
  contaId INT NOT NULL,
  acao VARCHAR(50) NOT NULL,
  dataCriacao DATETIME DEFAULT CURRENT_TIMESTAMP,
  saldo DECIMAL(10,2) NOT NULL,
  mensagem VARCHAR(100) NOT NULL,
  FOREIGN KEY (contaId) REFERENCES conta(id)
);
```

## Como Executar o Projeto
1. **Clone o repositório:**
   ```bash
   git clone https://github.com/arianewelke/BankChallenge.git
   cd BankChallenge
   ```
2. **Configure o banco de dados:**
    - Crie um banco no MySQL.
    - Execute os scripts SQL fornecidos para criar as tabelas.
    - Configure `persistence.xml` com as credenciais do banco.
3. **Compile e execute o projeto:**
    - Abra o projeto no IntelliJ IDEA..
    - Navegue até a classe principal **App** (que contém o  `método public static void main(String[] args` ).
    - Clique no botão **Run** para iniciar o projeto.

---
✍️ **Autor:** Ariane Welke
