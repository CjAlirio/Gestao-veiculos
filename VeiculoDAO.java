import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // Salvar novo veículo no banco
    public static void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa, modelo, ano, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setBoolean(4, veiculo.isDisponivel());

            stmt.executeUpdate();
            System.out.println("✅ Veículo salvo no banco com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar veículo: " + e.getMessage());
        }
    }

    //  Listar veículos disponíveis
    public static List<Veiculo> listarDisponiveis() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculo WHERE disponivel = 1";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo v = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                v.setId(rs.getInt("id"));
                v.setDisponivel(true);
                lista.add(v);
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar veículos: " + e.getMessage());
        }

        return lista;
    }

    //  Atualizar disponibilidade do veículo
    public static void atualizarDisponibilidade(int id, boolean disponivel) {
        String sql = "UPDATE veiculo SET disponivel = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar disponibilidade: " + e.getMessage());
        }
    }

    //  Listar todos os veículos (opcional)
    public static List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculo";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo v = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                v.setId(rs.getInt("id"));
                v.setDisponivel(rs.getBoolean("disponivel"));
                lista.add(v);
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar todos os veículos: " + e.getMessage());
        }

        return lista;
    }


    //  Buscar veículo por ID (opcional)
    public static Veiculo buscarPorId(int id) {
        String sql = "SELECT * FROM veiculo WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Veiculo v = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                v.setId(rs.getInt("id"));
                v.setDisponivel(rs.getBoolean("disponivel"));
                return v;
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar veículo por ID: " + e.getMessage());
        }

        return null;
    }
}