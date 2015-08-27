# s2
INFORMACOES SOBRE INSTALACAO
----------------------------
Banco de dados: MySQL Community Edition 5.5.24
Server: Apache Tomcat 8.0.20
Java 8
Arquivo "base de dados script" na raiz do repositorio contem o DDL da base de dados

Tomcat deve ser instalado em um diretorio onde o projeto possa ter acesso de gravacao. Isso, porque o projeto gera automaticamente um arquivo de propriedades dentro de uma pasta com o mesmo nome do ContextPath dentro outra pasta chamada s2_conf no mesmo diretorio onde o tomcat estah instalado. Em outras palavras:
- -tomcat
- -- webapps
- --- {contextPath projeto}
- -s2_conf
- -- {contextPath projeto}
- --- s2.cfg

Se nao houver permissao de gravacao, uma exception sera lancada e nao sera possivel inputar o caminho de conexao da base de dados sem alterar o codigo-fonte.

No caso de testes, os dados de conexao da base estao hardcoded em br.com.s2.mercadorias.test.helper.JpaUtilsTeste


INFORMACOES SOBRE O PROJETO
---------------------------
Foi utilizado webservice rest com spring. Todos o contexto do spring esta a partir de "{contextPath}/sistema/".

Endpoints:

- {contextPath}/sistema/prop
requer usuario "admin" e senha "123". Pagina utilizada para inputar caminho, usuario e senha da base de dados. Lembre-se de clickar no botao "Salvar".

- {contextPath}/sistema/rest/mapa/inserir_malha 
Requisito: metodo = POST; content-type = application/json
Utilizado para cadastrar um novo mapa e suas rotas. 
Exemplo de formato de entrada: 
[{"nome":"SP","rotas":[{"origem":"A","destino":"B","distancia":10},{"origem":"B","destino":"C","distancia":10}]},{"nome":"RJ","rotas":[{"origem":"A","destino":"B","distancia":13},{"origem":"B","destino":"C","distancia":11.5}]}]
Formato de saida:
Se houve sucesso, será retornado o parametro de entrada com HTTP 200.

- {contextPath}/sistema/rest/mapa/inserir_malha
Requisito: metodo = POST; content-type = application/json
Utilizado para obter custo e rota das rotas com menor distancia.
Exemplo de formato de entrada:
{"autonomia":11.5,"mapa":"SP","valor_combustivel_litro":2.5,"origem":"A","destino":"B"}
Formato de saida:
{"custo":11.95, "rota":"B C D F"} com HTTP 200.


Em caso de erros, sera retornado o seguinte json:
{"erro":[mensagem amigavel ou stack trace]} com HTTP 500.
