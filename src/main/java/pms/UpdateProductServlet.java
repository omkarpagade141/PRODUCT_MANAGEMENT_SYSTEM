package pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String productCompany = request.getParameter("productCompany");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "root", "db_password");

            // SQL query to update the product details
            String sql = "UPDATE Products SET productName = ?, productPrice = ?, productCompany = ? WHERE productId = ?";

            // Create PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productName);
            preparedStatement.setBigDecimal(2, new BigDecimal(productPrice));
            preparedStatement.setString(3, productCompany);
            preparedStatement.setString(4, productId);

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            // Prepare response
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if (rowsUpdated > 0) {
                out.println("<h2>Product Updated Successfully</h2>");
                
            } else {
                out.println("<h2>Error Updating Product</h2>");
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

