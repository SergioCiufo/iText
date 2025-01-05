package com.company.iText.ui;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.company.iText.model.CompanyInfo;
import com.company.iText.model.InvoiceInfo;
import com.company.iText.model.Product;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class HelloWorldPDF {
    public void makePdf(CompanyInfo company, CompanyInfo clientCompany, InvoiceInfo invoice, ArrayList<Product> products) {
        try {
            Document document = new Document(PageSize.A4);
            //TODO nel caso il percorso non esiste, costruiscilo
            PdfWriter.getInstance(document, new FileOutputStream("src/resources/pdf/PdfTest.pdf"));
            //apri il documento vuoto (pronto per le aggiunte)
            document.open();
            
            //logo azienda
            Image logo = Image.getInstance("src/resources/media/nervLogo.png");
            //riduciamo la grandezza dell'immagine in percentuale
            logo.scalePercent(25);
            //aggiungi l'elemento al documento
            document.add(logo);
            
            //Intestazione
            Paragraph header = new Paragraph("Fattura n."+invoice.getInvoiceNumber(), new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.RED));
            
            //mettiamo del margine sopra all'intestazione
            header.setSpacingBefore(20);
            //mettiamo del margine sotto all'intestazione
            header.setSpacingAfter(20);
            //allineamo l'intestazione al centro
            header.setAlignment(Element.ALIGN_CENTER);

            document.add(header);
            
            //tabella trasparente per scrivere a sx e dx
            PdfPTable tableInfo = new PdfPTable(3); //due colonne
            tableInfo.setWidthPercentage(100);
            tableInfo.setSpacingAfter(50);
            
            //RIGA1
            // cella di sinistra
            PdfPCell cellLeft1 = new PdfPCell();
            cellLeft1.addElement(new Phrase(company.getName(), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED)));
            cellLeft1.addElement(new Phrase(company.getAddress(), new Font(Font.FontFamily.HELVETICA, 10)));
            cellLeft1.addElement(new Phrase("VAT/TAX CODE: "+company.getVatNumberOrTaxCode(), new Font(Font.FontFamily.HELVETICA, 10)));
            cellLeft1.setBorder(PdfPCell.NO_BORDER);
            //cellLeft1.setPadding(10);  // Padding interno per la cella (non serve se il bordo è invisibile)
            tableInfo.addCell(cellLeft1);
            
            PdfPCell cellCentral1= new PdfPCell();
            //TODO da rimuovere il commento (aggiustare la larghezza della colonna)
            //cellCentral1.setBorder(PdfPCell.NO_BORDER);
            tableInfo.addCell(cellCentral1);

            // cella di destra
            PdfPCell cellRight1 = new PdfPCell();
            cellRight1.addElement(new Phrase("INVOICE TO", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cellRight1.addElement(new Phrase(clientCompany.getName(), new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.RED)));
            cellRight1.addElement(new Phrase(clientCompany.getAddress(), new Font(Font.FontFamily.HELVETICA,10)));
            cellRight1.addElement(new Phrase("VAT/TAX CODE: "+clientCompany.getVatNumberOrTaxCode(), new Font(Font.FontFamily.HELVETICA,10)));
            cellRight1.setBorder(PdfPCell.NO_BORDER);
            //cellRight1.setPadding(10);  // Padding interno per la cella (non serve se il bordo è invisibile)
            tableInfo.addCell(cellRight1);
            
            //RIGA2 colspan 3
            PdfPCell cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(3); //occupa lo spazio di tre colonne
            cell2.setMinimumHeight(50f);
            tableInfo.addCell(cell2);
            
            //RIGA3
            // cella di sinistra
            PdfPCell cellLeft3 = new PdfPCell();
            cellLeft3.addElement(new Phrase(company.getBankName(), new Font(Font.FontFamily.HELVETICA, 10)));
            cellLeft3.addElement(new Phrase("SWIFT/BIC: "+company.getSwiftOrBic(), new Font(Font.FontFamily.HELVETICA, 10)));
            cellLeft3.addElement(new Phrase("IBAN: "+company.getIban(), new Font(Font.FontFamily.HELVETICA, 10)));
            cellLeft3.setBorder(PdfPCell.NO_BORDER);
            tableInfo.addCell(cellLeft3);
            
            //cella centrale
            PdfPCell cellCentral3= new PdfPCell();
            //TODO da rimuovere il commento (aggiustare la larghezza della colonna)
            //cellCentral1.setBorder(PdfPCell.NO_BORDER);
            tableInfo.addCell(cellCentral3);
            
            // cella di destra
            PdfPCell cellRight3 = new PdfPCell();
            cellRight3.addElement(new Phrase("Invoice date: "+invoice.getInvoiceDay(), new Font(Font.FontFamily.HELVETICA,10)));
            cellRight3.addElement(new Phrase("Invoice due day: "+invoice.getInvoiceDueDay(), new Font(Font.FontFamily.HELVETICA,10, Font.BOLD)));
            cellRight3.setBorder(PdfPCell.NO_BORDER);
            tableInfo.addCell(cellRight3);
            
            // Aggiungi la tabella al documento
            document.add(tableInfo);
            
            //TAB2
            PdfPTable tablePrice = new PdfPTable(5); //cinque colonne
            tablePrice.setWidthPercentage(100);
            //impostiamo la larghezza delle colonne (in float ma lo trattiamo come fossero %)
            tablePrice.setWidths(new float[]{35f, 15f, 15f, 15f, 20f});
            
            //intestazione
            // chiama il metodo per evitare lunghure
            tablePrice.addCell(createRedCell("DESCRIPTION"));
            tablePrice.addCell(createRedCell("QUANTITY"));
            tablePrice.addCell(createRedCell("PRICE"));
            tablePrice.addCell(createRedCell("SUBTOTAL"));
            tablePrice.addCell(createRedCell("VAT"));
            
            for(int i = 0; i< products.size(); i++) {
            	//descrizione
                tablePrice.addCell(createDescriptionCell(products.get(i).getDescription()));
                //quantità
                tablePrice.addCell(createQuantityCell(products.get(i).getQuantity()));    
                //prezzo
                tablePrice.addCell(createPriceSubVat(products.get(i).getPrice()));       
                //subtotale
                float singleSubtotal = calculate(products.get(i).getPrice(), products.get(i).getQuantity());
                tablePrice.addCell(createPriceSubVat(singleSubtotal));  //calcolo subtotal      
                //iva
                float singleVat= calculateVat(singleSubtotal, products.get(i).getVat());
                tablePrice.addCell(createPriceSubVat(singleVat)+"("+products.get(i).getVat()+")"+"%");
            }  
            
            document.add(tablePrice);
            
            //TAB3 FINALE TOTALE
            PdfPTable tableTotal = new PdfPTable(5); //cinque colonne
            tableTotal.setWidthPercentage(100);
            //impostiamo la larghezza delle colonne (in float ma lo trattiamo come fossero %)
            tableTotal.setWidths(new float[]{35f, 15f, 15f, 15f, 20f});
            
            //calcoli totale
            float totalSubtotal = calculateTotalSub(products);
            float totalVat= calculateTotalVat(products);
            float totalCalculated= totalSubtotal+totalVat;
            
            //RIGA colspan 
            PdfPCell colspanPrice1 = new PdfPCell(new Phrase(""));
            colspanPrice1.setColspan(3); //occupa lo spazio di tre colonne
            colspanPrice1.setBorderWidth(0);
            tableTotal.addCell(colspanPrice1);
            
            tableTotal.addCell(createSubtotalVatString("SUBTOTAL"));
            tableTotal.addCell(createSubtotalVatString(totalSubtotal+"€"));
            
            PdfPCell colspanPrice2 = new PdfPCell(new Phrase(""));
            colspanPrice2.setColspan(3); //occupa lo spazio di tre colonne
            colspanPrice2.setBorderWidth(0);
            tableTotal.addCell(colspanPrice2);
            
            tableTotal.addCell(createSubtotalVatString("VAT"));
            tableTotal.addCell(createSubtotalVatString(totalVat+"€"));
            
            PdfPCell colspanPrice3 = new PdfPCell(new Phrase(""));
            colspanPrice3.setColspan(3); //occupa lo spazio di tre colonne
            colspanPrice3.setBorderWidth(0);
            tableTotal.addCell(colspanPrice3);
            
            tableTotal.addCell(createTotalString("TOTAL"));
            tableTotal.addCell(createTotalString(totalCalculated+"€"));
            
            document.add(tableTotal);
            
            document.close();
            System.out.println("PDF created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static float calculate(float quantity, float price) {
    	float subtotal = quantity * price;
    	return subtotal;
    }
    
    private static float calculateVat(float subtotal, float vatPercentual) {
    	float vat = (subtotal*vatPercentual)/100;
    	return vat;
    }
    
    private static float calculateTotalSub(ArrayList<Product> products) {
		float totalSub =0;
    	for(Product prod : products) {
			float calc = calculate(prod.getQuantity(), prod.getPrice());
			totalSub=+calc;
		}
    	return totalSub;
    }
    
    private static float calculateTotalVat(ArrayList<Product> products) {
    	float totalVat = 0;
    	for(Product prod : products) {
    		float calcSingleSubtotal = calculate(prod.getQuantity(), prod.getPrice());
    		System.out.println("subtotal no iva" +calcSingleSubtotal);
    		float singleVat = calculateVat(calcSingleSubtotal, prod.getVat());
    		System.out.println("singleVat+ "+singleVat);
    		totalVat+=singleVat;
    		System.out.println("totalVat " + totalVat);
    	}
    	return totalVat;
    }
    
    
    
    //metodo per creare le celle
    private static PdfPCell createRedCell(String text) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.RED);
        //cell.setPhrase ci permette di allineare il contenuto della cella (addElement non lo supporta)
        cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.WHITE)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }
    
    //metodo per creare descrizioni
    private static PdfPCell createDescriptionCell(String text) {
    	PdfPCell cell = new PdfPCell();
    	cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
    	//if(ultima i==counter){
    	//metti il bordo sotto;
    	//}else{
    	//
    	cell.setBorderWidthBottom(0);
    	cell.setBorderWidthTop(0);
    	//
    	return cell;
    }
    
    //metodo per creare quantità
    private static PdfPCell createQuantityCell(float quantity) {
    	PdfPCell cell = new PdfPCell();
    	String quantityStr = Float.toString(quantity);
    	cell.setPhrase(new Phrase(quantityStr, new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	//if(ultima i==counter){
    	//metti il bordo sotto;
    	//}else{
    	//
    	cell.setBorderWidthBottom(0);
    	cell.setBorderWidthTop(0);
    	//
    	return cell;
    }
    
    //TODO DA AGGIUSTARE LA VAT
  //metodo per creare quantità
    private static PdfPCell createPriceSubVat(float priceSubVat) {
    	PdfPCell cell = new PdfPCell();
    	String priceSubVatStr = Float.toString(priceSubVat);
    	cell.setPhrase(new Phrase(priceSubVatStr, new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorderWidthBottom(0);
    	cell.setBorderWidthTop(0);
    	//
    	return cell;
    }
    
  //metodo allineamento e grandezza Stringa subtotale e iva
    private static PdfPCell createSubtotalVatString(String subVatTotal) {
    	PdfPCell cell = new PdfPCell();
    	cell.setPhrase(new Phrase(subVatTotal, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorderWidth(0);
    	return cell;
    }
    
    //metodo allineamento e grandezza Stringa subtotale e iva
    private static PdfPCell createSubtotalVatString(float subVatTotal) {
    	PdfPCell cell = new PdfPCell();
    	String subTotalVatString = Float.toString(subVatTotal);
    	cell.setPhrase(new Phrase(subTotalVatString, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorderWidth(0);
    	return cell;
    }
    
  //metodo allineamento e grandezza Stringa subtotale e iva
    private static PdfPCell createTotalString(String text) {
    	PdfPCell cell = new PdfPCell();
    	cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorderWidth(0);
    	return cell;
    }
    
  //metodo allineamento e grandezza Stringa subtotale e iva
    private static PdfPCell createTotalString(float total) {
    	PdfPCell cell = new PdfPCell();
    	String totalVatString = Float.toString(total);
    	cell.setPhrase(new Phrase(totalVatString, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    	cell.setBorderWidth(0);
    	return cell;
    }
}