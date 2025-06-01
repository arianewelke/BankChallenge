# Bank Challenge

## Descrição
Este é um sistema bancário desenvolvido em **Java** com um banco de dados relacional (**MySQL**). Ele permite a abertura de accounts, login, consultas de saldo, saques, depósitos, transferências e visualização de extrato.

## Funcionalidades
- **Abertura de Conta**: Permite a criação de accounts de diferentes tipos.
-  **Login**: O usuário pode acessar sua account mediante CPF e senha.
-  **Visualização de Saldo**: O saldo pode ser consultado após login.
-  **Realizar Saques**: Saque de valores conforme saldo disponível.
-  **Realizar Depósitos**: Adição de valores à account do usuário.
-  **Realizar Transferências**: Envio de valores para outra account.
-  **Visualizar Extrato**: Histórico completo de transações (saques, depósitos e transferências).

## Tecnologias Utilizadas
- **Java 17+**
- **JPA/Hibernate**
- **Banco de Dados Relacional** (MySQL)
- **Maven** (Gerenciamento de dependências)

## Modelagem do Banco de Dados
Abaixo está o diagrama ER do banco de dados:


![Diagrama ER](https://raw.githubusercontent.com/arianewelke/BankChallenge/refs/heads/main/assets/EER.png)

### Estrutura das Tabelas
#### 🔹 Tabela `user`
```sql
CREATE TABLE user(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  cpf CHAR(11) NOT NULL UNIQUE, 
  dateBirth DATE NOT NULL,
  password CHAR(4) NOT NULL
);
```

#### 🔹 Tabela `account`
```sql
CREATE TABLE account (
  id INT AUTO_INCREMENT PRIMARY KEY,
  userId INT NOT NULL,
  balance DECIMAL(10,2) DEFAULT 0.00,
  type VARCHAR(20) NOT NULL,
  number VARCHAR(50) UNIQUE NOT NULL,
  FOREIGN KEY (userId) REFERENCES user(id)
);
```

#### 🔹 Tabela `statement`
```sql
CREATE TABLE statement ( 
  id INT AUTO_INCREMENT PRIMARY KEY,
  accountId INT NOT NULL,
  action VARCHAR(50) NOT NULL,
  dateCreation DATETIME DEFAULT CURRENT_TIMESTAMP,
  balance DECIMAL(10,2) NOT NULL,
  message VARCHAR(100) NOT NULL,
  FOREIGN KEY (accountId) REFERENCES account(id)
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
