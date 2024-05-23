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



@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
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

            // SQL query to insert data into the Products table
            String sql = "INSERT INTO Products (productId, productName, productPrice, productCompany) VALUES (?, ?, ?, ?)";

            // Create PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productId);
            preparedStatement.setString(2, productName);
            preparedStatement.setBigDecimal(3, new BigDecimal(productPrice));
            preparedStatement.setString(4, productCompany);

            // Execute the update
            int rowsInserted = preparedStatement.executeUpdate();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            if (rowsInserted > 0) {
                out.println("<html><body><h3>Product Added Successfully </h3></body></html>");
                
            } else {
                out.println("<html><body><h3>Product Added failed. Please try again.</h3></body></html>");
            }
            

            // Prepare response
            
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

