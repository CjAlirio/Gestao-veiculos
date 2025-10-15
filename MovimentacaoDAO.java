import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class MovimentacaoDAO {

    public static void salvar(Movimentacao m) {
        String sql = "INSERT INTO movimentacao (veiculo_id, colaborador_id, retirada, pneus_saida, oleo_saida, lataria_saida, combustivel_saida, observacoes_saida) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, m.getVeiculo().getId());
            stmt.setInt(2, m.getColaborador().getId());
            stmt.setString(3, m.getRetirada().toString());
            stmt.setBoolean(4, m.getChecklistSaida().isPneus());
            stmt.setBoolean(5, m.getChecklistSaida().isOleo());
            stmt.setBoolean(6, m.getChecklistSaida().isLataria());
            stmt.setBoolean(7, m.getChecklistSaida().isCombustivel());
            stmt.setString(8, m.getChecklistSaida().getObservacoes());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                m.setId(rs.getInt(1));
            }

            System.out.println("✅ Movimentação registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar movimentação: " + e.getMessage());
        }
    }

    public static List<Movimentacao> listarAbertas() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacao WHERE devolucao IS NULL";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo veiculo = VeiculoDAO.buscarPorId(rs.getInt("veiculo_id"));
                Colaborador colaborador = ColaboradorDAO.buscarPorId(rs.getInt("colaborador_id"));

                Checklist checklistSaida = new Checklist(
                        rs.getBoolean("pneus_saida"),
                        rs.getBoolean("oleo_saida"),
                        rs.getBoolean("lataria_saida"),
                        rs.getBoolean("combustivel_saida"),
                        rs.getString("observacoes_saida")
                );

                Movimentacao m = new Movimentacao(veiculo, colaborador, checklistSaida);
                m.setId(rs.getInt("id"));
                m.setRetirada(LocalDateTime.parse(rs.getString("retirada")));

                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar movimentações: " + e.getMessage());
        }

        return lista;
    }

    public static void atualizarDevolucao(Movimentacao m) {
        String sql = "UPDATE movimentacao SET devolucao = ?, pneus_retorno = ?, oleo_retorno = ?, lataria_retorno = ?, combustivel_retorno = ?, observacoes_retorno = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getDevolucao().toString());
            stmt.setBoolean(2, m.getChecklistRetorno().isPneus());
            stmt.setBoolean(3, m.getChecklistRetorno().isOleo());
            stmt.setBoolean(4, m.getChecklistRetorno().isLataria());
            stmt.setBoolean(5, m.getChecklistRetorno().isCombustivel());
            stmt.setString(6, m.getChecklistRetorno().getObservacoes());
            stmt.setInt(7, m.getId());

            stmt.executeUpdate();
            System.out.println("✅ Devolução registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar devolução: " + e.getMessage());
        }
    }
}