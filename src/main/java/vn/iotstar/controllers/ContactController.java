package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class ContactController
 * Xử lý form liên hệ
 */
@WebServlet("/ContactController")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Redirect to contact section
		response.sendRedirect(request.getContextPath() + "/#contact");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set content type
		response.setContentType("application/json;charset=UTF-8");
		
		// Get form parameters
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		
		// Validate input
		boolean isValid = validateInput(name, email, message);
		
		// Create JSON response
		PrintWriter out = response.getWriter();
		
		if (isValid) {
			// Simulate saving to database
			saveContactMessage(name, email, message);
			
			// Return success response
			out.println("{");
			out.println("  \"success\": true,");
			out.println("  \"message\": \"Tin nhắn đã được gửi thành công! Chúng tôi sẽ liên hệ lại sớm nhất có thể.\",");
			out.println("  \"data\": {");
			out.println("    \"name\": \"" + escapeJson(name) + "\",");
			out.println("    \"email\": \"" + escapeJson(email) + "\",");
			out.println("    \"timestamp\": \"" + java.time.LocalDateTime.now() + "\"");
			out.println("  }");
			out.println("}");
		} else {
			// Return error response
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.println("{");
			out.println("  \"success\": false,");
			out.println("  \"message\": \"Dữ liệu không hợp lệ. Vui lòng kiểm tra lại thông tin.\",");
			out.println("  \"errors\": {");
			out.println("    \"name\": \"" + (name == null || name.trim().isEmpty() ? "Tên không được để trống" : "") + "\",");
			out.println("    \"email\": \"" + (email == null || !isValidEmail(email) ? "Email không hợp lệ" : "") + "\",");
			out.println("    \"message\": \"" + (message == null || message.trim().isEmpty() ? "Tin nhắn không được để trống" : "") + "\"");
			out.println("  }");
			out.println("}");
		}
	}
	
	/**
	 * Validate form input
	 */
	private boolean validateInput(String name, String email, String message) {
		if (name == null || name.trim().isEmpty()) {
			return false;
		}
		if (email == null || !isValidEmail(email)) {
			return false;
		}
		if (message == null || message.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validate email format
	 */
	private boolean isValidEmail(String email) {
		if (email == null) return false;
		return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	}
	
	/**
	 * Escape JSON string
	 */
	private String escapeJson(String str) {
		if (str == null) return "";
		return str.replace("\"", "\\\"")
				 .replace("\n", "\\n")
				 .replace("\r", "\\r")
				 .replace("\t", "\\t");
	}
	
	/**
	 * Simulate saving contact message
	 */
	private void saveContactMessage(String name, String email, String message) {
		// In a real application, this would save to database
		System.out.println("=== NEW CONTACT MESSAGE ===");
		System.out.println("Name: " + name);
		System.out.println("Email: " + email);
		System.out.println("Message: " + message);
		System.out.println("Timestamp: " + java.time.LocalDateTime.now());
		System.out.println("==========================");
	}
}

