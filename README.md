Repositório destinado a entrega da AT3/N1 - Atividade prática coletiva - Bimestre N1

# Sistema de Gerenciamento de Hotel

Este é um sistema de gerenciamento de hotel implementado em Java, que simula as operações básicas de um hotel, como reserva de quartos, check-in, check-out e limpeza de quartos.

## Funcionalidades

### Recepcionistas

Os recepcionistas são responsáveis por receber os hóspedes, atribuir quartos disponíveis a eles e gerenciar a lista de espera quando não há quartos disponíveis no momento.

- **Atribuir Quartos:** Os recepcionistas verificam a disponibilidade de quartos e atribuem quartos limpos aos hóspedes que chegam.
- **Lista de Espera:** Caso todos os quartos estejam ocupados, os recepcionistas colocam os hóspedes na lista de espera e os alocam em quartos assim que estiverem disponíveis.

### Camareiras

As camareiras são responsáveis pela limpeza dos quartos e garantem que os quartos estejam prontos para receber novos hóspedes após o check-out.

- **Limpeza de Quartos:** As camareiras recebem notificações sobre quartos que precisam ser limpos e se encarregam de prepará-los para os próximos hóspedes.
- **Manutenção da Disponibilidade:** Após a limpeza, as camareiras garantem que os quartos estejam marcados como disponíveis para novos hóspedes.

### Hóspedes

Os hóspedes são os clientes do hotel e podem solicitar quartos, realizar check-in e check-out, além de fazer reclamações em caso de insatisfação.

- **Solicitação de Quartos:** Os hóspedes chegam ao hotel e solicitam quartos aos recepcionistas.
- **Check-in:** Após atribuição de um quarto, os hóspedes realizam o check-in e recebem a chave do quarto.
- **Check-out:** No momento de saída, os hóspedes devolvem a chave do quarto e liberam o quarto para limpeza.
- **Reclamações:** Em caso de problemas ou insatisfação, os hóspedes podem fazer reclamações aos recepcionistas.

## Participantes

- [Guilherme Nunes Lobo](https://github.com/GuilhermeNL01) 
- [Felipe Vasconcelos Cardoso](https://github.com/Fiboliriodem) 

## Funcionamento

O sistema cria uma série de threads para representar os diferentes participantes do hotel. Os hóspedes chegam ao hotel e são atendidos pelos recepcionistas, que atribuem quartos disponíveis a eles. As camareiras ficam responsáveis por limpar os quartos após o check-out dos hóspedes e garantir que estejam prontos para receber novos clientes.

## Como Usar

1. Compile todos os arquivos Java.
2. Execute a classe `Main` para iniciar a simulação do sistema de gerenciamento de hotel.
