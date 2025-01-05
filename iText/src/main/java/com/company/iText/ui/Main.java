package com.company.iText.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.iText.model.CompanyInfo;
import com.company.iText.model.InvoiceInfo;
import com.company.iText.model.Product;

public class Main {
	public static void main(String[]args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to your Nerv invoice generator.");
		CompanyInfo nervCompanyInfo = new CompanyInfo("Nerv", "Neo Tokyo-3", "892892", "TokyoBank", "892", "GP524515151516856161");
		
		InvoiceInfo invoice = new InvoiceInfo();
		System.out.print("Insert invoice number: ");
		int invoiceNumber = Integer.parseInt(scan.nextLine());
		invoice.setInvoiceNumber(invoiceNumber);
		
		CompanyInfo clientCompanyInfo = new CompanyInfo();
		System.out.print("Enter the client's company name: ");
		clientCompanyInfo.setName(scan.nextLine());	
		System.out.print("Enter the client's adress: ");
		clientCompanyInfo.setAddress(scan.nextLine());
		System.out.print("Enter the client's VAT number or tax code: ");
		clientCompanyInfo.setVatNumberOrTaxCode(scan.nextLine());

		System.out.println("Client information: ");
		System.out.println(clientCompanyInfo);
		invoice.setCompanyInfo(clientCompanyInfo);
		
		LocalDate invoiceDate = getInvoiceDate(scan, "Enter the invoice date (dd/mm/yyyy): ");
		invoice.setInvoiceDay(invoiceDate);
		
		LocalDate invoiceDueDate = getInvoiceDate(scan, "Enter the invoice due date (dd/mm/yyyy): ");
		invoice.setInvoiceDueDay(invoiceDueDate);
		
		ArrayList<Product> products = new ArrayList<Product>();
		boolean insertProductLoop;
		do {
			Product product = new Product();
			insertProductLoop = false;	

			System.out.print("Enter product description: ");
			String descriptionProduct = scan.nextLine();
			product.setDescription(descriptionProduct);

			System.out.print("Enter product quantity: ");
			float quantityProduct = Float.parseFloat(scan.nextLine());
			product.setQuantity(quantityProduct);
			
			System.out.print("Enter product price: ");
			float priceProduct = Float.parseFloat(scan.nextLine());
			product.setPrice(priceProduct);

			System.out.print("Enter product VAT%: ");
			float vatProduct = Float.parseFloat(scan.nextLine());
			product.setVat(vatProduct);
			products.add(product);
			System.out.println("Product added.");

			while(true) {
				System.out.print("Add another product? Y/N: ");
				String choice = scan.nextLine().toLowerCase();
				switch (choice.toLowerCase()) {
				case "y":
					insertProductLoop = true;
					break;
				case "n":
					System.out.println(products);
					break;
				default:
					System.out.println("Invalid response.");
					continue;
				}
				break;
			}
		}while(insertProductLoop);
		
		HelloWorldPDF helloWorldPdf = new HelloWorldPDF();
		helloWorldPdf.makePdf(nervCompanyInfo, clientCompanyInfo, invoice, products);
		scan.close();

	}
	
	//metodo controllo data
	public static LocalDate getInvoiceDate(Scanner scan, String message) {
        LocalDate invoiceDate = null;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(message);
                String dateStr = scan.nextLine();
                invoiceDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                valid = true;  // Data valida, esci dal ciclo
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in the format dd/mm/yyyy.");
            }
        }

        return invoiceDate;
    }
}
