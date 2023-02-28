import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class Insert extends HttpServlet {
    private final static String query = "insert into Employee(name,nic,department,designation,joinedDate) values(?,?,?,?,?)";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
      
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        
        //get the values
        String name = req.getParameter("name");
        String nic = req.getParameter("nic");
        String department = req.getParameter("department");
        String designation = req.getParameter("designation");
        String joinedDate = req.getParameter("joinedDate");
        //load the JDBC driver
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeeDB","root","");
                PreparedStatement ps = con.prepareStatement(query);){
            //set the values
            ps.setString(1, name);
            ps.setString(2, nic);
            ps.setString(3, department);
            ps.setString(4, designation);
            ps.setString(5, joinedDate);
            
            //execute the query
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if(count==1) {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Registered Successfully</h2>");
            }else {
                pw.println("<h2 class='bg-danger text-light text-center'>Record Not Registered</h2>");
            }
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='RegisterEmployee.html'><button class='btn'>Home</button></a>");
        pw.println("</div>");
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
