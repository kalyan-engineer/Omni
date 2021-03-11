package com.bank.account.DTO;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDTO {

	    private String accountid;
	 
	    private String accounttype;

	    private String minor;

	    private String branch;
	    
	    private String dateofbirth;
	    
	    private UserDTO userDTO;
	    
	    private String saveStatus;
	    
	    private String userid;
	    
	    private String openedDate;

		public String getAccountid() {
			return accountid;
		}

		public void setAccountid(String accountid) {
			this.accountid = accountid;
		}

		public String getAccounttype() {
			return accounttype;
		}

		public void setAccounttype(String accounttype) {
			this.accounttype = accounttype;
		}

		public String getMinor() {
			return minor;
		}

		public void setMinor(String minor) {
			this.minor = minor;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public String getDateofbirth() {
			return dateofbirth;
		}

		public void setDateofbirth(String dateofbirth) {
			this.dateofbirth = dateofbirth;
		}

		public UserDTO getUserDTO() {
			return userDTO;
		}

		public void setUserDTO(UserDTO userDTO) {
			this.userDTO = userDTO;
		}

		public String getSaveStatus() {
			return saveStatus;
		}

		public void setSaveStatus(String saveStatus) {
			this.saveStatus = saveStatus;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getOpenedDate() {
			return openedDate;
		}

		public void setOpenedDate(String openedDate) {
			this.openedDate = openedDate;
		}
		
		
}
