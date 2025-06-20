package control;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Acquisto;
import model.Fattura;
import model.OrdineDAO;

/**
 * Servlet implementation class FatturaPDFServlet
 */
@WebServlet("/FatturaPDFServlet")
public class FatturaPDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Fattura fatt = null;

        String realPath = getServletContext().getRealPath("/fatture");
        Path directoryPath = Paths.get(realPath);

        String idOrdineParametro = request.getParameter("idOrdine");
        int idOrdine = Integer.parseInt(idOrdineParametro);
        

        Acquisto ordine = OrdineDAO.loadOrderByIdOrder(idOrdine);
        
        System.out.println("acquisto = " + ordine);
        System.out.println("path = " + ordine.getOrdine().getFattura());

        Path filePath = directoryPath.resolve(ordine.getOrdine().getFattura());

        boolean genera = false;
        
        try {
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            System.out.println("Percorso file: " + filePath);

            if (Files.notExists(filePath)) {
                String fatturaCompletaPath = filePath.toString();
                fatt = new Fattura(ordine, fatturaCompletaPath);
                genera = fatt.genera();
                if (genera) {
                    System.out.println("File creato con successo.");
                } else {
                    System.out.println("ERRORE NELLA GENERAZIONE");
                }
            } else {
                System.out.println("Il file esiste già.");
            }

            String mimeType = getServletContext().getMimeType(filePath.toString());
            if (mimeType == null) {
                mimeType = "application/octet-stream"; 
            }

            response.setContentType(mimeType);
            response.setContentLengthLong(Files.size(filePath));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");

            try (OutputStream out = response.getOutputStream()) {
                Files.copy(filePath, out);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERRORE NEL TRASFERIMENTO PDF");
            }

        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file o della cartella:");
            e.printStackTrace();
        }
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
