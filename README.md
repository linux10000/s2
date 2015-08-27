# s2
INFORMACOES SOBRE INSTALACAO
----------------------------
Banco de dados: MySQL Community Edition 5.5.24
Server: Apache Tomcat 8.0.20
Java 8
Arquivo "base de dados script" na raiz do repositorio contem o DDL da base de dados

Tomcat deve ser instalado em um diretorio onde o projeto possa ter acesso de gravacao. Isso, porque o projeto gera automaticamente um arquivo de propriedades dentro de uma pasta com o mesmo nome do ContextPath dentro outra pasta chamada s2_conf no mesmo diretorio onde o tomcat estah instalado. Em outras palavras:
- tomcat
- - webapps
- -- {contextPath projeto}
- s2_conf
- - {contextPath projeto}
- -- s2.cfg

Se nao houver permissao de gravacao, uma exception sera lancada e nao sera possivel inputar o caminho de conexao da base de dados sem alterar o codigo-fonte.


INFORMACOES SOBRE O PROJETO
---------------------------
