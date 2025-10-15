import java.sql.Connection;
import java.sql.Statement;

public class BancoInicializador {
    public static void criarTabelas() {
        try (Connection conn = Conexao.conectar()) {
            Statement stmt = conn.createStatement();

            // Tabela de veículos
            stmt.execute("CREATE TABLE IF NOT EXISTS veiculo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "placa TEXT NOT NULL," +
                    "modelo TEXT NOT NULL," +
                    "ano INTEGER NOT NULL," +
                    "disponivel BOOLEAN NOT NULL)");

            System.out.println("✅ Tabela 'veiculo' criada com sucesso!");

            //  Tabela de colaboradores
            stmt.execute("CREATE TABLE IF NOT EXISTS colaborador (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "matricula TEXT NOT NULL)");

            System.out.println("✅ Tabela 'colaborador' criada com sucesso!");

            //  Tabela de movimentações
            stmt.execute("CREATE TABLE IF NOT EXISTS movimentacao (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "veiculo_id INTEGER NOT NULL," +
                    "colaborador_id INTEGER NOT NULL," +

                    "pneus_saida BOOLEAN," +
                    "oleo_saida BOOLEAN," +
                    "lataria_saida BOOLEAN," +
                    "combustivel_saida BOOLEAN," +
                    "observacoes_saida TEXT," +

                    "pneus_retorno BOOLEAN," +
                    "oleo_retorno BOOLEAN," +
                    "lataria_retorno BOOLEAN," +
                    "combustivel_retorno BOOLEAN," +
                    "observacoes_retorno TEXT," +

                    "FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)," +
                    "FOREIGN KEY (colaborador_id) REFERENCES colaborador(id))");

            System.out.println("✅ Tabela 'movimentacao' criada com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao criar tabelas: " + e.getMessage());
        }
    }
}