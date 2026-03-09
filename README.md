# Motor de Simulação para RPG de Texto Sandbox

Este projeto é o núcleo lógico de um RPG de texto de mundo aberto, fortemente inspirado nas mecânicas sistêmicas de D&D. 
Em vez de depender de árvores de diálogo estáticas ou eventos pré-roteirizados, este sistema constrói um universo procedimental e autossuficiente regido por leis termodinâmicas e climáticas. 
O objetivo é oferecer liberdade total ao jogador em um mundo dinâmico, onde as narrativas e os desafios emergem organicamente das reações do motor de simulação.

## Arquitetura de Sistemas

A arquitetura foi projetada com uma separação rigorosa de responsabilidades. O estado do mundo funciona de forma estritamente independente da interface, preparando o terreno para a futura camada de tradução em texto.

### 1. Núcleo de Dados e Estado Global (`Mundo` e `MacroChunk`)
A fundação da simulação. O `Mundo` orquestra a matriz de coordenadas, enquanto cada `MacroChunk` atua como um módulo atômico, armazenando as variáveis fundamentais (altitude, umidade, temperatura, densidade mágica e pressão atmosférica) que definem a realidade física e mágica daquele local específico.

### 2. Geração Procedural (`GeradorRuido2d` e `Sementes`)
O construtor do mundo. Utiliza algoritmos de ruído fractal em múltiplas oitavas para transformar sementes numéricas em topografia e clima coesos. A geração determinística garante a criação de regiões geográficas lógicas, evitando transições abruptas e criando um terreno de jogo imersivo.

### 3. Motor de Simulação Reativa (`Simulação` e `ClimaDiario`)
O ecossistema em movimento. Um sistema operando em ciclos temporais independentes processa a física do mundo continuamente:
* Sistemas de evaporação e escoamento de umidade.
* Formação de frentes de vento guiadas por pressão atmosférica e inércia térmica.
* O clima local reage de forma autônoma à latitude (distância do equador) e à passagem do tempo (dia/noite, estações).

### 4. Classificação Vetorial de Biomas
Uma abordagem matemática robusta para a definição de ecossistemas: o sistema calcula a distância euclidiana entre o estado exato de um chunk (seu vetor de variáveis) e as condições ideais de biomas pré-definidos. Se a simulação climática alterar o ambiente, o bioma se adapta dinamicamente, sem a necessidade de lógicas condicionais engessadas.

### 5. Interface de Depuração Visual (`DashboardDebug`)
O painel de controle do arquiteto do sistema. Uma ferramenta em Swing construída para inspecionar a matriz de dados brutos. Permite visualizar o mundo através de mapas de calor, topografia e umidade, e avançar o tempo para validar a lógica de simulação antes de acoplar a interface final de texto do jogador.

## Executando o Ambiente de Testes

1. Compile o projeto respeitando a estrutura de pacotes Java.
2. Execute a classe `Main` para abrir o Dashboard de validação estrutural.
3. Utilize os controles interativos para gerar novos mundos, inspecionar as propriedades intrínsecas dos módulos e acionar a simulação de tempo contínuo.