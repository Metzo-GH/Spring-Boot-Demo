package com.tpagenda.tpagenda;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static ResponseEntity<byte[]> generatePdf(Evenement evenement) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Détails de l'événement:"));
            document.add(new Paragraph("Nom de l'événement: " + evenement.getLabel()));
            document.add(new Paragraph("Date: " + evenement.getDate()));
            document.add(new Paragraph("Heure de début: " + evenement.getStartTime()));
            document.add(new Paragraph("Heure de fin: " + evenement.getEndTime()));
            document.add(new Paragraph("Label: " + evenement.getLabel()));
            document.close();

            byte[] pdfBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "evenement_details.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
