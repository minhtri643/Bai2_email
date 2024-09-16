package murach.email;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import murach.business.User;
//import murach.data.UserDB;

public class EmailListServlet extends HttpServlet  {

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {

        String url = "/index.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // In giá trị của action ra Console để gỡ lỗi
        System.out.println("Action received: " + action);

        // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.html";    // the "join" page
        }
        else if (action.equals("add")) {                
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String birthday = request.getParameter("birthday");
            String hearabout = request.getParameter("hearabout");
            String announcements = request.getParameter("announcements");
            String method = request.getParameter("method");


            // Chuyển đổi định dạng ngày sinh từ YYYY-MM-DD sang DD-MM-YYYY
            String formattedBirthday = formatBirthday(birthday);

            // store data in User object and save User object in db
            User user = new User(firstName, lastName, email, formattedBirthday, hearabout,announcements, method);
           // UserDB.insert(user);
            
            // set User object in request object and set URL
            request.setAttribute("user", user);
            url = "/thanks.jsp";   // the "thanks" page
        }
        
        // forward request and response objects to specified URL
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }    
    
    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
                         throws ServletException, IOException {
        // Gọi doPost từ doGet như yêu cầu của bài tập
        doPost(request, response);
    }    
    
    // Phương thức chuyển đổi định dạng ngày sinh
    private String formatBirthday(String birthday) {
        try {
            // Định dạng ban đầu của ngày sinh (YYYY-MM-DD)
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Định dạng mới muốn chuyển sang (DD-MM-yyyy)
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            // Chuyển đổi ngày sinh
            Date date = originalFormat.parse(birthday);
            return newFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return birthday;  // Trả về định dạng gốc nếu gặp lỗi
        }
    }
}
