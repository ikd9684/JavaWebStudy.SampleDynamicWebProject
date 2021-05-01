package jp.co.excite_software.s_ikeda.study;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexPageServlet
 */
@WebServlet({
        "/Index"
})
public class IndexPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String database = "excite_study";
    private static final String user = "excite";
    private static final String password = "excite";

    private static final String sql = "select version() as \"VERSION\";";

    private final String url;

    /**
     * Default constructor. 
     */
    public IndexPageServlet() {

        String dbHost = System.getenv("DB_HOST");
        url = "jdbc:mariadb://" + dbHost + ":3306/" + database + "?UTF-8&serverTimezone=JST";
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (
                Connection connect = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = connect.prepareStatement(sql);) {

            String dbVersion = "unknown";

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dbVersion = rs.getString("VERSION");
            }

            out.append("<!doctype html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"utf-8\">"
                    + "</head>"
                    + "<body>");

            out.append("<p>" + dbVersion + "<p>");

            out.append("</body>"
                    + "</html>");
        }
        catch (Exception e) {
            e.printStackTrace(out);
        }
    }

}
