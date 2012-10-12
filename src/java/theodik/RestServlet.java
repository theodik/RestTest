/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package theodik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author theodik
 */
@WebServlet(name = "RestServlet", urlPatterns = {"/items/*"})
public class RestServlet extends HttpServlet {

    private final Pattern regPattern = Pattern.compile("(/((?<new>new)|(?<id>[0-9]+))?)(?<edit>/edit)?");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;encoding=UTF-8");

        PrintWriter writer = response.getWriter();
        Matcher matcher = regPattern.matcher(request.getPathInfo());
        if (matcher.matches()) {
            if (matcher.group("new") != null) {
                writer.println("action: new");
            } else {
                String id = matcher.group("id");
                if(id == null){
                    writer.println("action: index");
                } else if (matcher.group("edit") != null) {
                    writer.println("action: edit - " + id);
                } else {
                    writer.println("action: show - " + id);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;encoding=UTF-8");
        PrintWriter writer = response.getWriter();
        if (request.getPathInfo().equals("/")) {
            writer.println("action: create");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;encoding=UTF-8");
        PrintWriter writer = response.getWriter();
        Matcher matcher = regPattern.matcher(request.getPathInfo());
        if (matcher.matches()) {
            String id = matcher.group("id");
            if (id != null) {
                writer.println("action: update - " + id);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;encoding=UTF-8");
        PrintWriter writer = response.getWriter();
        Matcher matcher = regPattern.matcher(request.getPathInfo());
        if (matcher.matches()) {
            String id = matcher.group("id");
            if (id != null) {
                writer.println("action: delete - " + id);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
