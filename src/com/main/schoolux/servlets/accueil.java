package com.main.schoolux.servlets;

import org.eclipse.persistence.exceptions.DescriptorException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "acceuil")
public class accueil extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //permet de rediriger sur la pag acceuil définie dans la jsp et surtout dans web.xml
        this.getServletContext().getRequestDispatcher("/WEB-INF/acceuil.jsp").forward(request,response);
    }
}
