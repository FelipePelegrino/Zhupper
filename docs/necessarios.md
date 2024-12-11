# Requisitos da Aplicação

Este documento contém checklists para garantir que os requisitos da aplicação sejam atendidos em cada uma das telas descritas, bem como os comportamentos esperados.
- API: https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws

### Origem:
- Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031
- Av. Thomas Edison, 365 - Barra Funda, São Paulo - SP, 01140-000
- Av. Brasil, 2033 - Jardim America, São Paulo - SP, 01431-001

### Destino:
- Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200

---

## Geral
- [x] Solicitação de Viagem
- [x] Opções de Viagem
- [ ] Histórico de Viagens
- [x] Debounce
- [x] Loading para cada Request | visual + texto ? 
- [x] Dialog de erro

---

## Solicitação de Viagem

- [x  O formulário contém os campos:
  - [x] ID do usuário.
  - [x] Endereço de origem.
  - [x] Endereço de destino.
- [x] Deve ter um botão para estimar o valor da viagem.
  - [x] Request API | POST /ride/estimate
    - [x] 200 - exibir tela opções de viagem
	- [x] 400 - invalid data

---

## Opções de Viagem
- [x] Deve mostrar um mapa estático com a rota retornada na estimativa plotada, indicando o ponto A e o ponto B.
- [x] A lista de opções apresenta para cada motorista:
  - [x] Nome.
  - [x] Descrição.
  - [x] Veículo.
  - [x] Avaliação.
  - [x] Valor da viagem.
- [x] Para cada motorista há um botão "Escolher".
  - [x] Request API | PATCH /ride/confirm
    - [x] Lembrar dos ids do motorista e o min de km aceito (apenas informativo)
    - [x] 200 - exibir tela histórico de viagens
	- [x] 400 - invalid data
	- [x] 404 - driver not found
	- [x] 406 - invalid distance

---

## Histórico de Viagens

- [ ] Fitros
  - [ ] Existe um campo para informar o ID do usuário.
  - [ ] Há um seletor de motorista com uma opção para "Mostrar todos".
- [ ] Há um botão para aplicar o filtro.
  - [ ] Request API | GET /ride/{customer_id}?driver_id={id do motorista}
    - [ ] 200 - lista de cada viagem
	  - [ ] Data e hora da viagem.
	  - [ ] Nome do motorista.
	  - [ ] Origem.
	  - [ ] Destino.
	  - [ ] Distância.
	  - [ ] Tempo.
	  - [ ] Valor.
	- [ ] 400 - invalid driver
	- [ ] 404 - no rides found

---
