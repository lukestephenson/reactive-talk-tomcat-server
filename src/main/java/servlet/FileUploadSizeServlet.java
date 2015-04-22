package servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "FileUploadSizeServlet",
        urlPatterns = {"/size"}

)
public class FileUploadSizeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        byte[] buffer = new byte[1024 * 10];
        int bytesRead;
        int totalBytesRead = 0;
        do {
            bytesRead = req.getInputStream().read(buffer);
            if (bytesRead != -1) {
                totalBytesRead += bytesRead;
            }
        } while (bytesRead != -1);

        ServletOutputStream out = resp.getOutputStream();
        out.println("TOMCAT - Size of body is " + totalBytesRead);
        out.flush();
        out.close();
    }

}