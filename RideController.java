package com.talentsprint.controller;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;
import com.talentsprint.bean.RideHistory;
import com.talentsprint.chariot.DataBaseUtility;
//import com.talentsprint.chariot.SearchRides;

import java.sql.*;
/**
 * Servlet implementation class RideController
 */
@WebServlet("/RideController")
public class RideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RideController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 RideHistory h = new RideHistory();
		Connection connection = null;
		java.sql.Statement statement;
		ResultSet resultSet = null;
        HttpSession session = request.getSession();
		connection = DataBaseUtility.getConnection();
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from Ride where driverId = '9440095503'");
			while(resultSet.next()) {
				h.setDriverId(resultSet.getString(1));
				h.setCustomerId(resultSet.getString(2));
				h.setSource(resultSet.getString(3));
				h.setDestination(resultSet.getString(4));
				h.setStatus(resultSet.getString(5));
				h.setAmount(resultSet.getDouble(6));
				h.setBookingTime(resultSet.getString(7));
			}
			session.setAttribute("driverId",h.getDriverId());
			session.setAttribute("source",h.getSource());
			session.setAttribute("destination",h.getDestination());
			PrintWriter out = response.getWriter();
			//out.println(h.getDriverId());
			//out.println(h.getSource());
			RequestDispatcher rd = request.getRequestDispatcher("RouteToUser.jsp");
			rd.forward(request, response);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} 
		
		// HttpSession session = request.getSession();
		
		String accept = request.getParameter("Accept");
		String reject = request.getParameter("Reject");
		
		if(accept != null) {
			try {
				System.out.println("accepted");
				statement = connection.createStatement();
				statement.executeUpdate("Update ride set status = 'Accepted' where driverId = '8106794042' order by  bookingTime desc limit 1");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("RouteToUser.jsp");
			rd.forward(request, response);
		}
		if(reject != null) {
			try {
				System.out.println("rejected");
				statement = connection.createStatement();
				statement.executeUpdate("Update ride set status = 'Rejected' where driverId = '8106794042' order by  bookingTime desc limit 1");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("CurrentLocation.jsp");
			rd.forward(request, response);
		}
		
	    
		
		
		
	}


}
