package pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "root", "db_password");

            // SQL query to delete the product by productId
            String sql = "DELETE FROM Products WHERE productId = ?";

            // Create PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productId);

            // Execute the update
            int rowsDeleted = preparedStatement.executeUpdate();

            // Prepare response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if (rowsDeleted > 0) {
                out.println("<h2>Product Deleted Successfully</h2>");
                out.println("<p>Product ID: " + productId + "</p>");
            } else {
                out.println("<h2>Error Deleting Product</h2>");
                out.println("<p>Product ID: " + productId + " not found.</p>");
            }
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error interacting with database", e);
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
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

