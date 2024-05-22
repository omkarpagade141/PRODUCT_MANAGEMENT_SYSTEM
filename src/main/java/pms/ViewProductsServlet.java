package pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;


@WebServlet("/ViewProductsServlet")
public class ViewProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productmanagesystem", "root", "Supriya");

            // Create a statement
            statement = connection.createStatement();

            // Execute a query to retrieve all products
            String sql = "SELECT * FROM Products";
            resultSet = statement.executeQuery(sql);

            // Prepare response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html><body>");
            out.println("<div class='container'>");
            out.println("<h2>View Products</h2>");
            out.println("<table style='border-collapse:collapse;border:1px solid black;text-align:center;'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Product ID</th>");
            out.println("<th>Product Name</th>");
            out.println("<th>Product Price</th>");
            out.println("<th>Product Company</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Iterate through the result set and build the HTML table rows
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("productId") + "</td>");
                out.println("<td>" + resultSet.getString("productName") + "</td>");
                out.println("<td>" + resultSet.getBigDecimal("productPrice") + "</td>");
                out.println("<td>" + resultSet.getString("productCompany") + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error interacting with database", e);
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new ServletException("Error closing database resources", e);
            }
        }
    }
}

