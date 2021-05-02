package jp.co.excite_software.s_ikeda.study;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Optional;

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

    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "excite_study";
    private static final String DB_USER = "excite";
    private static final String DB_PSWD = "excite";

    private final String url;

    /**
     * Default constructor. 
     */
    public IndexPageServlet() {

        String dbHost = System.getenv("DB_HOST");
        url = "jdbc:mariadb://" + dbHost + ":" + DB_PORT + "/" + DB_NAME + "?UTF-8&serverTimezone=JST";
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sql = "select"
                + " TE.EMPLOYEE_NUMBER"
                + ",TE.FAMILY_NAME"
                + ",TE.GIVEN_NAME"
                + ",TE.MAIL_ADDRESS"
                + ",MP.NAME"
                + ",TE.JOINING_DATE "
                + "from"
                + " TRN_EMPLOYEES TE "
                + "left join TRN_EMPLOYEE_POSITIONS TP"
                + " on TP.EMPLOYEE_ID = TE.ID "
                + "left join MST_POSITIONS MP"
                + " on MP.ID = TP.POSITION_ID "
                + "order by"
                + " DISPLAY_ORDER is null asc"
                + ",DISPLAY_ORDER"
                + ",JOINING_DATE";

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.append("<!doctype html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<style>"
                + "table { border-collapse: collapse; }"
                + "th, td { border: solid 1px; padding: 3px; }"
                + "</style>"
                + "</head>"
                + "<body>");

        out.append("<p>" + sql + "</p>");
        out.append("<table>");

        try (
                Connection connect = DriverManager.getConnection(url, DB_USER, DB_PSWD);
                PreparedStatement ps = connect.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            ArrayList<String> columnNameList = new ArrayList<String>();
            out.append("<tr>");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String columnName = meta.getColumnName(i);
                columnNameList.add(columnName);

                out.append("<th>" + columnName + "</th>");
            }
            out.append("</tr>");

            while (rs.next()) {
                out.append("<tr>");

                for (String columnName : columnNameList) {
                    String value = Optional.ofNullable(rs.getString(columnName)).orElse("");
                    out.append("<td>" + value + "</td>");
                }

                out.append("</tr>");
            }
        }
        catch (Exception e) {
            e.printStackTrace(out);
        }

        out.append("</table>");

        out.append("</body>"
                + "</html>");
    }
}
