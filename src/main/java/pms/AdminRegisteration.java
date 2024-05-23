package pms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
  

@WebServlet("/registerAdmin")
public class AdminRegisteration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String mobile = request.getParameter("mobile");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (!password.equals(repassword)) {
            out.println("<html><body><h3>Passwords do not match!</h3></body></html>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "root", "db_password");
            String sql = "INSERT INTO admin (firstname, lastname, username, password, mobile) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, firstname);
            pst.setString(2, lastname);
            pst.setString(3, username);
            pst.setString(4, password);
            pst.setString(5, mobile);

            int row = pst.executeUpdate();

            if (row > 0) {
                out.println("<html><body><h3>Registration successful! please </h3><a href=\"adminLogin.html\">login</a></body></html>");
                
            } else {
                out.println("<html><body><h3>Registration failed. Please try again.</h3></body></html>");
            }

            conn.close();
        }
       
        catch (Exception e) {
            out.println("<html><body><h3>Error: " + e.getMessage() + "</h3></body></html>");
        }
    }
}


