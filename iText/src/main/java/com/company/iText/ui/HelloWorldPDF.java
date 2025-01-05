package com.company.iText;

import java.io.FileOutputStream;

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
	public static void main(String[] args) {
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
			Paragraph header = new Paragraph("Fattura n.xxx", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.RED));

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
			cellLeft1.addElement(new Phrase("[Nome dell'azienda]", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED)));
			cellLeft1.addElement(new Phrase("[Indirizzo]", new Font(Font.FontFamily.HELVETICA, 10)));
			cellLeft1.addElement(new Phrase("P.IVA/C.F: [Numero di partita IVA/Codice Fiscale]", new Font(Font.FontFamily.HELVETICA, 10)));
			cellLeft1.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cellLeft1);

			PdfPCell cellCentral1= new PdfPCell();
			cellCentral1.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cellCentral1);

			// cella di destra
			PdfPCell cellRight1 = new PdfPCell();
			cellRight1.addElement(new Phrase("FATTURA A", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
			cellRight1.addElement(new Phrase("[Nome dell'azienda del cliente]", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.RED)));
			cellRight1.addElement(new Phrase("[Indirzzo dell'azienda del cliente]", new Font(Font.FontFamily.HELVETICA,10)));
			cellRight1.addElement(new Phrase("P.IVA/C.F: [Numero di partita IVA del cliente/Codice Fiscale]", new Font(Font.FontFamily.HELVETICA,10)));
			cellRight1.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cellRight1);

			//RIGA2 colspan 3
			PdfPCell cell2 = new PdfPCell(new Phrase(""));
			cell2.setColspan(3); //occupa lo spazio di tre colonne
			cell2.setMinimumHeight(50f);
			cell2.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cell2);

			//RIGA3
			// cella di sinistra
			PdfPCell cellLeft3 = new PdfPCell();
			cellLeft3.addElement(new Phrase("[Nome della banca]", new Font(Font.FontFamily.HELVETICA, 10)));
			cellLeft3.addElement(new Phrase("SWIFT/BIC: [SWIFT/BIC]", new Font(Font.FontFamily.HELVETICA, 10)));
			cellLeft3.addElement(new Phrase("Numero del conto corrente [Conto Corrente(IBAN)]", new Font(Font.FontFamily.HELVETICA, 10)));
			cellLeft3.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cellLeft3);

			//cella centrale
			PdfPCell cellCentral3= new PdfPCell();
			cellCentral3.setBorder(PdfPCell.NO_BORDER);
			tableInfo.addCell(cellCentral3);

			// cella di destra
			PdfPCell cellRight3 = new PdfPCell();
			cellRight3.addElement(new Phrase("Data Fattura: 16/11/2021", new Font(Font.FontFamily.HELVETICA,10)));
			cellRight3.addElement(new Phrase("Data di Scadenza: 30/11/2021", new Font(Font.FontFamily.HELVETICA,10, Font.BOLD)));
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
			tablePrice.addCell(createRedCell("DESCRIZIONE"));
			tablePrice.addCell(createRedCell("QUANTITÀ"));
			tablePrice.addCell(createRedCell("PREZZO"));
			tablePrice.addCell(createRedCell("SUBTOTALE"));
			tablePrice.addCell(createRedCell("IVA"));

			//DESCRIZIONE
			tablePrice.addCell(createDescriptionCell("Il mio prodotto"));
			//quantità
			tablePrice.addCell(createQuantityString("6"));    
			//prezzo
			tablePrice.addCell(createSubtotalVatString("310,00"));       
			//subtotale
			tablePrice.addCell(createSubtotalVatString("1.860,00"));      
			//iva
			tablePrice.addCell(createSubtotalVatString("409,20 (22)%"));
			
			//DESCRIZIONE AI FINI ESEMPIO
			tablePrice.addCell(createDescriptionCell("Il mil servizio"));
			//quantità
			tablePrice.addCell(createQuantityString("1"));    
			//prezzo
			tablePrice.addCell(createSubtotalVatString("422,00"));       
			//subtotale
			tablePrice.addCell(createSubtotalVatString("422,00"));      
			//iva
			tablePrice.addCell(createSubtotalVatString("92,84 (22)%"));
			
			//RIGA2 finalcol
			PdfPCell finalcol1 = new PdfPCell(new Phrase(""));
			PdfPCell finalcol2 = new PdfPCell(new Phrase(""));
			PdfPCell finalcol3 = new PdfPCell(new Phrase(""));
			PdfPCell finalcol4 = new PdfPCell(new Phrase(""));
			PdfPCell finalcol5 = new PdfPCell(new Phrase(""));
			finalcol1.setMinimumHeight(50f);
			//cell2.setBorder(PdfPCell.NO_BORDER);
			finalcol1.setBorderWidthTop(0);
			tablePrice.addCell(finalcol1);
			finalcol2.setBorderWidthTop(0);
			tablePrice.addCell(finalcol2);
			finalcol3.setBorderWidthTop(0);
			tablePrice.addCell(finalcol3);
			finalcol4.setBorderWidthTop(0);
			tablePrice.addCell(finalcol4);
			finalcol5.setBorderWidthTop(0);
			tablePrice.addCell(finalcol5);


			document.add(tablePrice);

			//TAB3 FINALE TOTALE
			PdfPTable tableTotal = new PdfPTable(5); //cinque colonne
			tableTotal.setWidthPercentage(100);
			//impostiamo la larghezza delle colonne (in float ma lo trattiamo come fossero %)
			tableTotal.setWidths(new float[]{35f, 15f, 15f, 15f, 20f});

			//RIGA colspan 
			PdfPCell colspanPrice1 = new PdfPCell(new Phrase(""));
			colspanPrice1.setColspan(3); //occupa lo spazio di tre colonne
			colspanPrice1.setBorderWidth(0);
			tableTotal.addCell(colspanPrice1);

			tableTotal.addCell(createTotalSubIvaString("SUBTOTALE"));
			tableTotal.addCell(createTotalSubIvaString("2.282,00"));

			PdfPCell colspanPrice2 = new PdfPCell(new Phrase(""));
			colspanPrice2.setColspan(3); //occupa lo spazio di tre colonne
			colspanPrice2.setBorderWidth(0);
			tableTotal.addCell(colspanPrice2);

			tableTotal.addCell(createTotalSubIvaString("IVA"));
			tableTotal.addCell(createTotalSubIvaString("502,04"));

			PdfPCell colspanPrice3 = new PdfPCell(new Phrase(""));
			colspanPrice3.setColspan(3); //occupa lo spazio di tre colonne
			colspanPrice3.setBorderWidth(0);
			tableTotal.addCell(colspanPrice3);

			tableTotal.addCell(createTotalString("TOTALE"));
			tableTotal.addCell(createTotalString("2.784,04"));

			document.add(tableTotal);

			document.close();
			System.out.println("PDF created!");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		//
		return cell;
	}
	
	//metodo allineamento e grandezza Stringa subtotale e iva
		private static PdfPCell createQuantityString(String quantity) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(quantity, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidthBottom(0);
			cell.setBorderWidthTop(0);
			return cell;
		}


	//metodo allineamento e grandezza Stringa subtotale e iva
	private static PdfPCell createSubtotalVatString(String subVatTotal) {
		PdfPCell cell = new PdfPCell();
		cell.setPhrase(new Phrase(subVatTotal, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		return cell;
	}
	
	//metodo allineamento e grandezza Stringa subtotale e iva
		private static PdfPCell createTotalSubIvaString(String text) {
			PdfPCell cell = new PdfPCell();
			cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL)));
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

}