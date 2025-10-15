import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    public static void salvar(Colaborador colaborador) {
        String sql = "INSERT INTO colaborador (nome, matricula) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getMatricula());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar colaborador: " + e.getMessage());
        }
    }

    public static List<Colaborador> listarTodos() {
        List<Colaborador> lista = new ArrayList<>();
        String sql = "SELECT * FROM colaborador";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Colaborador c = new Colaborador(
                        rs.getString("nome"),
                        rs.getString("matricula")
                );
                c.setId(rs.getInt("id"));
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao listar colaboradores: " + e.getMessage());
        }

        return lista;
    }

    public static Colaborador buscarPorId(int id) {
        String sql = "SELECT * FROM colaborador WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Colaborador c = new Colaborador(
                        rs.getString("nome"),
                        rs.getString("matricula")
                );
                c.setId(rs.getInt("id"));
                return c;
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar colaborador por ID: " + e.getMessage());
        }

        return null;
    }
}