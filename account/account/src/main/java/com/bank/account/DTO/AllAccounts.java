package com.bank.account.DTO;

import java.util.List;


public class AllAccounts {

	private Pagination page;
	private long totalRecords;
	private List<AccountDTO> AccountRecords;
	public Pagination getPage() {
		return page;
	}
	public void setPage(Pagination page) {
		this.page = page;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<AccountDTO> getAccountRecords() {
		return AccountRecords;
	}
	public void setAccountRecords(List<AccountDTO> accountRecords) {
		AccountRecords = accountRecords;
	}
	
}
