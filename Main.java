import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BancoInicializador.criarTabelas();
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== Sistema de Controle de Veículos ===");
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Cadastrar colaborador");
            System.out.println("3. Registrar retirada");
            System.out.println("4. Registrar devolução");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Placa do veículo: ");
                    String placa = scanner.nextLine();

                    System.out.print("Modelo do veículo: ");
                    String modelo = scanner.nextLine();

                    System.out.print("Ano do veículo: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();

                    Veiculo novoVeiculo = new Veiculo(placa, modelo, ano);
                    VeiculoDAO.salvar(novoVeiculo);
                    break;

                case 2:
                    System.out.print("Nome do colaborador: ");
                    String nome = scanner.nextLine();

                    System.out.print("Matrícula do colaborador: ");
                    String matricula = scanner.nextLine();

                    Colaborador novoColaborador = new Colaborador(nome, matricula);
                    ColaboradorDAO.salvar(novoColaborador);
                    System.out.println("✅ Colaborador cadastrado com sucesso!");
                    break;

                case 3:
                    List<Veiculo> veiculosDisponiveis = VeiculoDAO.listarDisponiveis();
                    if (veiculosDisponiveis.isEmpty()) {
                        System.out.println("⚠️ Nenhum veículo disponível.");
                        break;
                    }

                    System.out.println("Veículos disponíveis:");
                    for (int i = 0; i < veiculosDisponiveis.size(); i++) {
                        Veiculo v = veiculosDisponiveis.get(i);
                        System.out.println(i + " - " + v.getModelo() + " (" + v.getPlaca() + ")");
                    }

                    System.out.print("Escolha o número do veículo: ");
                    int indiceVeiculo = scanner.nextInt();
                    scanner.nextLine();
                    Veiculo veiculoSelecionado = veiculosDisponiveis.get(indiceVeiculo);
                    VeiculoDAO.atualizarDisponibilidade(veiculoSelecionado.getId(), false);

                    List<Colaborador> colaboradores = ColaboradorDAO.listarTodos();
                    System.out.println("Colaboradores:");
                    for (int i = 0; i < colaboradores.size(); i++) {
                        System.out.println(i + " - " + colaboradores.get(i).getNome());
                    }

                    System.out.print("Escolha o número do colaborador: ");
                    int indiceColaborador = scanner.nextInt();
                    scanner.nextLine();
                    Colaborador colaboradorSelecionado = colaboradores.get(indiceColaborador);

                    System.out.println("Checklist de saída:");
                    System.out.print("Pneus ok (sim/não)? ");
                    boolean pneus = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Óleo ok (sim/não)? ");
                    boolean oleo = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Lataria ok (sim/não)? ");
                    boolean lataria = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Combustível ok (sim/não)? ");
                    boolean combustivel = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Observações: ");
                    String observacoes = scanner.nextLine();

                    Checklist checklistSaida = new Checklist(pneus, oleo, lataria, combustivel, observacoes);
                    Movimentacao movimentacao = new Movimentacao(veiculoSelecionado, colaboradorSelecionado, checklistSaida);
                    MovimentacaoDAO.salvar(movimentacao);

                    System.out.println("✅ Retirada registrada com sucesso!");
                    break;

                case 4:
                    List<Movimentacao> abertas = MovimentacaoDAO.listarAbertas();
                    if (abertas.isEmpty()) {
                        System.out.println("⚠️ Nenhuma movimentação em aberto.");
                        break;
                    }

                    System.out.println("Movimentações em aberto:");
                    for (int i = 0; i < abertas.size(); i++) {
                        Movimentacao m = abertas.get(i);
                        System.out.println(i + " - Veículo: " + m.getVeiculo().getModelo() +
                                " | Colaborador: " + m.getColaborador().getNome());
                    }

                    System.out.print("Escolha o número da movimentação: ");
                    int indiceMov = scanner.nextInt();
                    scanner.nextLine();
                    Movimentacao movimentacaoDevolucao = abertas.get(indiceMov);

                    System.out.println("Checklist de retorno:");
                    System.out.print("Pneus ok (sim/não)? ");
                    pneus = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Óleo ok (sim/não)? ");
                    oleo = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Lataria ok (sim/não)? ");
                    lataria = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Combustível ok (sim/não)? ");
                    combustivel = scanner.nextLine().trim().equalsIgnoreCase("sim");

                    System.out.print("Observações: ");
                    observacoes = scanner.nextLine();

                    Checklist checklistRetorno = new Checklist(pneus, oleo, lataria, combustivel, observacoes);
                    movimentacaoDevolucao.registrarDevolucao(checklistRetorno);
                    MovimentacaoDAO.atualizarDevolucao(movimentacaoDevolucao);

                    VeiculoDAO.atualizarDisponibilidade(movimentacaoDevolucao.getVeiculo().getId(), true);
                    System.out.println("✅ Devolução registrada com sucesso!");
                    break;

                case 0:
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }
}