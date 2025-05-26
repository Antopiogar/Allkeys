package model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.SolidBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fattura {
    
    private Acquisto ac;
    private String path;
    
    public Fattura(Acquisto ac, String path) {
        this.ac = ac;
        this.path = path;
    }
    
    
    
    public boolean genera() {
        try {
            // Validazione degli input
            if (!validaInput()) {
                return false;
            }
            
            // Creazione del PDF
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            try {
                // Creare i font
                PdfFont fontNormale = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                PdfFont fontGrassetto = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                PdfFont fontTitolo = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                
                // Aggiungere le sezioni del documento
                aggiungiInfoUtente(document, fontTitolo, fontGrassetto, fontNormale);
                document.add(new Paragraph("\n"));
                aggiungiTabellaDati(document, fontTitolo, fontGrassetto, fontNormale);
                
                return true;
                
            } finally {
                document.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        	return false;
        }
    }
    
       /**
     * Valida gli input forniti al costruttore
     */
    private boolean validaInput() {
        if (path == null || path.trim().isEmpty()) {
        	System.out.println("PATH MORTO");
        	return false;
        }
        
        if (ac == null) {
        	System.out.println("acquisto smonco");
            return false;
        }
        
        return true;
    }
    
    /**
     * Aggiunge la sezione con le informazioni dell'utente
     */
    private void aggiungiInfoUtente(Document document, PdfFont fontTitolo, PdfFont fontGrassetto, PdfFont fontNormale) {
        
        // Titolo del documento
        Paragraph titolo = new Paragraph("ALLKEYS")
            .setFont(fontTitolo)
            .setFontSize(20)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontColor(ColorConstants.BLUE)
            .setMarginBottom(20);
        document.add(titolo);
        
        
        
        // Tabella per le informazioni utente
        Table tabellaUtente = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
            .useAllAvailableWidth()
            .setBorder(new SolidBorder(ColorConstants.GRAY, 1));
        
        // Header della sezione utente
        Cell headerUtente = new Cell(1, 2)
            .add(new Paragraph("INFORMAZIONI CLIENTE").setFont(fontGrassetto).setFontSize(14))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setTextAlignment(TextAlignment.CENTER)
            .setPadding(8);
        tabellaUtente.addHeaderCell(headerUtente);
        
        // Aggiungere informazioni utente
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Definisci il formato desiderato
        aggiungiRigaInfoUtente(tabellaUtente, "Nome:", ac.getUtente().getNome(), fontGrassetto, fontNormale);
        aggiungiRigaInfoUtente(tabellaUtente, "Cognome:", ac.getUtente().getCognome(), fontGrassetto, fontNormale);
        aggiungiRigaInfoUtente(tabellaUtente, "Email:", ac.getUtente().getEmail(), fontGrassetto, fontNormale);
        aggiungiRigaInfoUtente(tabellaUtente, "Data acquisto",dateFormatter.format(ac.getOrdine().getDataAcquisto()), fontGrassetto, fontNormale);
   
        document.add(tabellaUtente);
    }
    
    /**
     * Aggiunge una riga di informazioni utente alla tabella
     */
    private void aggiungiRigaInfoUtente(Table tabella, String etichetta, String valore, 
                                       PdfFont fontGrassetto, PdfFont fontNormale) {
        Cell cellaEtichetta = new Cell()
            .add(new Paragraph(etichetta).setFont(fontGrassetto))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setPadding(5);
        
        Cell cellaValore = new Cell()
            .add(new Paragraph(valore != null ? valore : "N/A").setFont(fontNormale))
            .setPadding(5);
        
        tabella.addCell(cellaEtichetta);
        tabella.addCell(cellaValore);
    }
    
    /**
     * Aggiunge la tabella con i dati degli articoli
     */
    private void aggiungiTabellaDati(Document document, PdfFont fontTitolo, PdfFont fontGrassetto, PdfFont fontNormale) {
        
        // Titolo della tabella
        Paragraph titoloTabella = new Paragraph("DETTAGLIO ARTICOLI")
            .setFont(fontTitolo)
            .setFontSize(16)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontColor(ColorConstants.DARK_GRAY)
            .setMarginTop(20)
            .setMarginBottom(15);
        document.add(titoloTabella);
        
        // Creare tabella con 5 colonne
        float[] larghezzeColonne = {15, 15, 40, 15};
        Table tabella = new Table(UnitValue.createPercentArray(larghezzeColonne))
            .useAllAvailableWidth()
            .setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        
        // Header della tabella
        String[] headers = {"Nome", "Piattaforma", "Key", "Prezzo €"};
        for (String header : headers) {
            Cell cellHeader = new Cell()
                .add(new Paragraph(header).setFont(fontGrassetto).setFontSize(10))
                .setBackgroundColor(ColorConstants.DARK_GRAY)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8);
            tabella.addHeaderCell(cellHeader);
        }
        double soldi = 0;
        // Aggiungere righe di dati
        for (int i = 0; i < ac.getArticoli().size(); i++) {
            tabella.addCell(createCell(ac.getArticoli().get(i).getNome(), fontNormale, TextAlignment.CENTER));
            tabella.addCell(createCell(ac.getArticoli().get(i).getPiattaforma(), fontNormale, TextAlignment.CENTER));
            tabella.addCell(createCell(ac.getChiavi().get(i).getCodice(), fontNormale, TextAlignment.CENTER));
            tabella.addCell(createCell("%.2f".formatted(ac.getArticoli().get(i).getPrezzo()), fontNormale, TextAlignment.CENTER));
            soldi += ac.getArticoli().get(i).getPrezzo();
        }

        // Riga del totale
        tabella.addCell(new Cell(1, 3)
            .add(new Paragraph("PREZZO TOTALE").setFont(fontGrassetto).setFontSize(10))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setTextAlignment(TextAlignment.RIGHT)
            .setPadding(8));
        
        tabella.addCell(new Cell()
            .add(new Paragraph(String.format("%.2f €", soldi)).setFont(fontGrassetto).setFontSize(10))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontColor(ColorConstants.RED)
            .setPadding(8));
        
        document.add(tabella);
        // Data del documento
        String dataCorrente = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Paragraph data = new Paragraph("Data: " + dataCorrente)
                .setFont(fontNormale)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(15);
            document.add(data);
        // Note finali
        Paragraph note = new Paragraph("\nNota: Tutti i prezzi sono espressi in Euro e includono IVA.")
            .setFont(fontNormale)
            .setFontSize(8)
            .setFontColor(ColorConstants.GRAY)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginTop(15);
        document.add(note);
    }
    
    /**
     * Metodo di utilità per creare celle della tabella
     */
    private Cell createCell(String contenuto, PdfFont font, TextAlignment allineamento) {
        return new Cell()
            .add(new Paragraph(contenuto).setFont(font).setFontSize(9))
            .setTextAlignment(allineamento)
            .setPadding(5);
    }
    
    
}
