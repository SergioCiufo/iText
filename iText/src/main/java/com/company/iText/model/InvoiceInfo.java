package com.company.iText.model;

import java.time.LocalDate;
import java.util.Objects;

public class InvoiceInfo {
	private int invoiceNumber;
	private CompanyInfo companyInfo;
	private LocalDate invoiceDay;
	private LocalDate invoiceDueDay;
	
	public InvoiceInfo() {
		super();
	}

	public InvoiceInfo(int invoiceNumber, CompanyInfo companyInfo, LocalDate invoiceDay, LocalDate invoiceDueDay) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.companyInfo = companyInfo;
		this.invoiceDay = invoiceDay;
		this.invoiceDueDay = invoiceDueDay;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public LocalDate getInvoiceDay() {
		return invoiceDay;
	}

	public void setInvoiceDay(LocalDate invoiceDay) {
		this.invoiceDay = invoiceDay;
	}

	public LocalDate getInvoiceDueDay() {
		return invoiceDueDay;
	}

	public void setInvoiceDueDay(LocalDate invoiceDueDay) {
		this.invoiceDueDay = invoiceDueDay;
	}


	@Override
	public int hashCode() {
		return Objects.hash(companyInfo, invoiceDay, invoiceDueDay, invoiceNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceInfo other = (InvoiceInfo) obj;
		return Objects.equals(companyInfo, other.companyInfo) && Objects.equals(invoiceDay, other.invoiceDay)
				&& Objects.equals(invoiceDueDay, other.invoiceDueDay) && invoiceNumber == other.invoiceNumber;
	}

	@Override
	public String toString() {
		return "InvoiceInfo [invoiceNumber=" + invoiceNumber + ", companyInfo=" + companyInfo + ", invoiceDay="
				+ invoiceDay + ", invoiceDueDay=" + invoiceDueDay + "]";
	}
	
	
}
