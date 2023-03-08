package com.artmart.services;

import com.artmart.models.Event;
import com.itextpdf.text.Document;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PdfService {

    public void generatePdf(String filename, Event event, int id) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document() {
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();
        //EventService es = new EventService();
        document.add(new Paragraph("Date created  : " + LocalDateTime.now()));
        document.add(new Paragraph("Event name : " + event.getName()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("Type : " + event.getType()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Location : " + event.getLocation()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Start date : " + event.getStartDate()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("End date : " + event.getEndDate()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Capacity : " + event.getCapacity()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Entry fee : " + event.getEntryFee()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("Description : " + event.getDescription()));
        document.add(new Paragraph("                      "));
        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph("ArtMart"));
        document.close();
        Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }

}