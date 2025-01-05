package com.company.iText.model;

import java.util.Objects;

public class CompanyInfo {
	private String name;
	private String address;
	private String VatNumberOrTaxCode;
	private String bankName;
	private String swiftOrBic;
	private String iban;
	
	public CompanyInfo() {
		super();
	}

	public CompanyInfo(String name, String address, String vatNumberOrTaxCode) {
		super();
		this.name = name;
		this.address = address;
		VatNumberOrTaxCode = vatNumberOrTaxCode;
	}

	public CompanyInfo(String name, String address, String vatNumberOrTaxCode, String bankName, String swiftOrBic,
			String iban) {
		super();
		this.name = name;
		this.address = address;
		VatNumberOrTaxCode = vatNumberOrTaxCode;
		this.bankName = bankName;
		this.swiftOrBic = swiftOrBic;
		this.iban = iban;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVatNumberOrTaxCode() {
		return VatNumberOrTaxCode;
	}

	public void setVatNumberOrTaxCode(String vatNumberOrTaxCode) {
		VatNumberOrTaxCode = vatNumberOrTaxCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSwiftOrBic() {
		return swiftOrBic;
	}

	public void setSwiftOrBic(String swiftOrBic) {
		this.swiftOrBic = swiftOrBic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(VatNumberOrTaxCode, address, bankName, iban, name, swiftOrBic);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyInfo other = (CompanyInfo) obj;
		return Objects.equals(VatNumberOrTaxCode, other.VatNumberOrTaxCode) && Objects.equals(address, other.address)
				&& Objects.equals(bankName, other.bankName) && Objects.equals(iban, other.iban)
				&& Objects.equals(name, other.name) && Objects.equals(swiftOrBic, other.swiftOrBic);
	}

	@Override
	public String toString() {
		return "CompanyInfo [name=" + name + ", address=" + address + ", VatNumberOrTaxCode=" + VatNumberOrTaxCode + "]";
	}
	
}
