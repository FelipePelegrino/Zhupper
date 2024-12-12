# Zhupper
Desafio técnico

## Api Key
- No diretório root do projeto existe um arquivo **key.properties**, que contém o seguinte código **API_KEY_MAPS_STATIC**=*INSIRA_A_CHAVE_AQUI*
- Substitua *INSIRA_A_CHAVE_AQUI* pela sua ApiKey

## Cobertura de Testes com JaCoCo
O projeto contém testes unitários com cobertura gerada pelo JaCoCo. Para verificar a cobertura, siga os passos abaixo:

1. **Execute os testes unitários**:
   - ./gradlew :app:test
2. **Execute a task de gerar relatório do jaCoCo**:
    - ./gradlew jacocoTestReport
3. No diretório **app\build\jacocoHtml** ira ter o arquivo **index.html** mostrando a cobertura de testes do projeto