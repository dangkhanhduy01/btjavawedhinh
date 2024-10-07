import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DoiTienServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sotienStr = request.getParameter("sotien");
        String loaitiente = request.getParameter("loaitiente");
        double tienvnd = 0;

        // Error handling for invalid input
        try {
            double sotien = Double.parseDouble(sotienStr);

            // Currency conversion logic
            switch (loaitiente) {
                case "USD":
                    tienvnd = sotien * 21380.00; // Example rate for USD
                    break;
                case "GBP":
                    tienvnd = sotien * 32622.80; // Example rate for GBP
                    break;
                case "EUR":
                    tienvnd = sotien * 24000.00; // Example rate for EUR
                    break;
                case "JPY":
                    tienvnd = sotien * 150.00; // Example rate for JPY
                    break;
                case "AUD":
                    tienvnd = sotien * 15000.00; // Example rate for AUD
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported currency type.");
            }

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Currency Conversion Result</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Currency Conversion Result</h2>");
                out.println("<p>Amount: " + sotien + " " + loaitiente + "</p>");
                out.println("<p>Converted Amount: " + tienvnd + " VND</p>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for currency conversion";
    }
}
