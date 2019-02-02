
# ActivityFragment
Um aplicativo Android que mostra como enviar informações de um Fragment para sua Activity pai.

## Descrição
* Às vezes uma tela necessita de vários elementos complexos, os quais podem ser colocados em Fragments.
Dessa forma, precisamos de meios para transmitir dados desses Fragments para a Activity pai.
* Uma forma normal de fazer isso seria declarando um atributo estático que pudesse ser acessado de fora da Activity pai
pelos Fragments, porém existem formas mais elegantes e que já são até mesmo especificadas nos Padrões de Projeto.
* Este projeto trás a utilização na prática do Padrão de Projeto "Observer" com pequenas modificações de conceito.

## O Padrão "Observer"
Esse padrão de projeto visa auxiliar a comunicação de classes. Em linhas gerais ocorre o seguinte:
* Uma classe interessada em receber atualizações de um Subject se inscreve como Observer (observadora).
* A classe Subject possui uma lista de observadores e após uma mudança de estado, notifica esses observadores com as últimas atualizações.

## O que foi usado
* Neste projeto há uma Activity que possui dois Fragments. Essa Activity tem interesse em receber atualizações de seus Fragments.
* Após uma entrada de usuário, os Fragments notificam imediatamente sua classe pai, a Activity por meio de uma interface chamada OnInformationChanged.
* A Activity então exibe o resultado na tela que foi recebido como parâmetro do método.

## Capturas de Tela

<div align="center">
  <img src="https://i.postimg.cc/1R2XCvW0/Screenshot-20190202-163537.png" width="300" alt="Screenshot1" /> 
  <img src="https://i.postimg.cc/ncmz6q5y/Screenshot-20190202-163607.png" width="300" alt="Screenshot2" />
</div>

### Autor
> Ivan Filho
