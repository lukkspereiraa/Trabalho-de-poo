import java.sql.Connection;
import util.ConexaoBD;

public class App {
    public static void main(String[] args) throws Exception {
         Connection conn = ConexaoBD.criarConexao();
    }
}
