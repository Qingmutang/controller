package com.bjpowernode.p2p.admin.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CreditorRights {
	
    private Integer id;

    private String applyNo;

    private Integer intoSource;

    private Integer applyProduct;

    private Double applyMoney;

    private Integer applyTerm;

    private String applyPurpose;

    private String borrowerRealname;

    private String borrowerIdcard;

    private Integer borrowerSex;

    private Integer borrowerAge;

    private Integer borrowerEducation;

    private Integer borrowerMarital;

    private Integer borrowerCity;

    private String borrowerPresentAddress;

    private String borrowerBankAccount;

    private String borrowerOpenBank;

    private Integer borrowerHouseCount;

    private Integer borrowerCarCount;

    private String borrowerWork;

    private String borrowerCompanyName;

    private String borrowerCompanyAddress;

    private Integer borrowerWorkYear;

    private Double borrowerMonthSalary;

    private Integer borrowerCreditReportCertification;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerCreditReportCertificationTime;

    private Integer borrowerIdcardCertification;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerIdcardCertificationTime;

    private Integer borrowerWorkCertification;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerWorkCertificationTime;

    private Integer borrowerIncomeCertification;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerIncomeCertificationTime;

    private Integer borrowerHouseCertification;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerHouseCertificationTime;

    private Integer borrowerFieldCertification;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date borrowerFieldCertificationTime;

    private Integer auditStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    private Integer auditLoanTerm;

    private Double auditLoanMoney;

    private Double contractLoanMoney;

    private Double consultingFee;

    private Double auditFee;

    private Double serviceFee;

    private Double accessFee;

    private Double yearRate;

    private Double putMoney;

    private Integer creditorRightsType;

    private Integer matchStatus;

    private String collectStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date collectFinishTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date matchFinishTime;

    private String loanContractPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Integer getIntoSource() {
        return intoSource;
    }

    public void setIntoSource(Integer intoSource) {
        this.intoSource = intoSource;
    }

    public Integer getApplyProduct() {
        return applyProduct;
    }

    public void setApplyProduct(Integer applyProduct) {
        this.applyProduct = applyProduct;
    }

    public Double getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
    }

    public Integer getApplyTerm() {
        return applyTerm;
    }

    public void setApplyTerm(Integer applyTerm) {
        this.applyTerm = applyTerm;
    }

    public String getApplyPurpose() {
        return applyPurpose;
    }

    public void setApplyPurpose(String applyPurpose) {
        this.applyPurpose = applyPurpose;
    }

    public String getBorrowerRealname() {
        return borrowerRealname;
    }

    public void setBorrowerRealname(String borrowerRealname) {
        this.borrowerRealname = borrowerRealname;
    }

    public String getBorrowerIdcard() {
        return borrowerIdcard;
    }

    public void setBorrowerIdcard(String borrowerIdcard) {
        this.borrowerIdcard = borrowerIdcard;
    }

    public Integer getBorrowerSex() {
        return borrowerSex;
    }

    public void setBorrowerSex(Integer borrowerSex) {
        this.borrowerSex = borrowerSex;
    }

    public Integer getBorrowerAge() {
        return borrowerAge;
    }

    public void setBorrowerAge(Integer borrowerAge) {
        this.borrowerAge = borrowerAge;
    }

    public Integer getBorrowerEducation() {
        return borrowerEducation;
    }

    public void setBorrowerEducation(Integer borrowerEducation) {
        this.borrowerEducation = borrowerEducation;
    }

    public Integer getBorrowerMarital() {
        return borrowerMarital;
    }

    public void setBorrowerMarital(Integer borrowerMarital) {
        this.borrowerMarital = borrowerMarital;
    }

    public Integer getBorrowerCity() {
        return borrowerCity;
    }

    public void setBorrowerCity(Integer borrowerCity) {
        this.borrowerCity = borrowerCity;
    }

    public String getBorrowerPresentAddress() {
        return borrowerPresentAddress;
    }

    public void setBorrowerPresentAddress(String borrowerPresentAddress) {
        this.borrowerPresentAddress = borrowerPresentAddress;
    }

    public String getBorrowerBankAccount() {
        return borrowerBankAccount;
    }

    public void setBorrowerBankAccount(String borrowerBankAccount) {
        this.borrowerBankAccount = borrowerBankAccount;
    }

    public String getBorrowerOpenBank() {
        return borrowerOpenBank;
    }

    public void setBorrowerOpenBank(String borrowerOpenBank) {
        this.borrowerOpenBank = borrowerOpenBank;
    }

    public Integer getBorrowerHouseCount() {
        return borrowerHouseCount;
    }

    public void setBorrowerHouseCount(Integer borrowerHouseCount) {
        this.borrowerHouseCount = borrowerHouseCount;
    }

    public Integer getBorrowerCarCount() {
        return borrowerCarCount;
    }

    public void setBorrowerCarCount(Integer borrowerCarCount) {
        this.borrowerCarCount = borrowerCarCount;
    }

    public String getBorrowerWork() {
        return borrowerWork;
    }

    public void setBorrowerWork(String borrowerWork) {
        this.borrowerWork = borrowerWork;
    }

    public String getBorrowerCompanyName() {
        return borrowerCompanyName;
    }

    public void setBorrowerCompanyName(String borrowerCompanyName) {
        this.borrowerCompanyName = borrowerCompanyName;
    }

    public String getBorrowerCompanyAddress() {
        return borrowerCompanyAddress;
    }

    public void setBorrowerCompanyAddress(String borrowerCompanyAddress) {
        this.borrowerCompanyAddress = borrowerCompanyAddress;
    }

    public Integer getBorrowerWorkYear() {
        return borrowerWorkYear;
    }

    public void setBorrowerWorkYear(Integer borrowerWorkYear) {
        this.borrowerWorkYear = borrowerWorkYear;
    }

    public Double getBorrowerMonthSalary() {
        return borrowerMonthSalary;
    }

    public void setBorrowerMonthSalary(Double borrowerMonthSalary) {
        this.borrowerMonthSalary = borrowerMonthSalary;
    }

    public Integer getBorrowerCreditReportCertification() {
        return borrowerCreditReportCertification;
    }

    public void setBorrowerCreditReportCertification(Integer borrowerCreditReportCertification) {
        this.borrowerCreditReportCertification = borrowerCreditReportCertification;
    }

    public Date getBorrowerCreditReportCertificationTime() {
        return borrowerCreditReportCertificationTime;
    }

    public void setBorrowerCreditReportCertificationTime(Date borrowerCreditReportCertificationTime) {
        this.borrowerCreditReportCertificationTime = borrowerCreditReportCertificationTime;
    }

    public Integer getBorrowerIdcardCertification() {
        return borrowerIdcardCertification;
    }

    public void setBorrowerIdcardCertification(Integer borrowerIdcardCertification) {
        this.borrowerIdcardCertification = borrowerIdcardCertification;
    }

    public Date getBorrowerIdcardCertificationTime() {
        return borrowerIdcardCertificationTime;
    }

    public void setBorrowerIdcardCertificationTime(Date borrowerIdcardCertificationTime) {
        this.borrowerIdcardCertificationTime = borrowerIdcardCertificationTime;
    }

    public Integer getBorrowerWorkCertification() {
        return borrowerWorkCertification;
    }

    public void setBorrowerWorkCertification(Integer borrowerWorkCertification) {
        this.borrowerWorkCertification = borrowerWorkCertification;
    }

    public Date getBorrowerWorkCertificationTime() {
        return borrowerWorkCertificationTime;
    }

    public void setBorrowerWorkCertificationTime(Date borrowerWorkCertificationTime) {
        this.borrowerWorkCertificationTime = borrowerWorkCertificationTime;
    }

    public Integer getBorrowerIncomeCertification() {
        return borrowerIncomeCertification;
    }

    public void setBorrowerIncomeCertification(Integer borrowerIncomeCertification) {
        this.borrowerIncomeCertification = borrowerIncomeCertification;
    }

    public Date getBorrowerIncomeCertificationTime() {
        return borrowerIncomeCertificationTime;
    }

    public void setBorrowerIncomeCertificationTime(Date borrowerIncomeCertificationTime) {
        this.borrowerIncomeCertificationTime = borrowerIncomeCertificationTime;
    }

    public Integer getBorrowerHouseCertification() {
        return borrowerHouseCertification;
    }

    public void setBorrowerHouseCertification(Integer borrowerHouseCertification) {
        this.borrowerHouseCertification = borrowerHouseCertification;
    }

    public Date getBorrowerHouseCertificationTime() {
        return borrowerHouseCertificationTime;
    }

    public void setBorrowerHouseCertificationTime(Date borrowerHouseCertificationTime) {
        this.borrowerHouseCertificationTime = borrowerHouseCertificationTime;
    }

    public Integer getBorrowerFieldCertification() {
        return borrowerFieldCertification;
    }

    public void setBorrowerFieldCertification(Integer borrowerFieldCertification) {
        this.borrowerFieldCertification = borrowerFieldCertification;
    }

    public Date getBorrowerFieldCertificationTime() {
        return borrowerFieldCertificationTime;
    }

    public void setBorrowerFieldCertificationTime(Date borrowerFieldCertificationTime) {
        this.borrowerFieldCertificationTime = borrowerFieldCertificationTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditLoanTerm() {
        return auditLoanTerm;
    }

    public void setAuditLoanTerm(Integer auditLoanTerm) {
        this.auditLoanTerm = auditLoanTerm;
    }

    public Double getAuditLoanMoney() {
        return auditLoanMoney;
    }

    public void setAuditLoanMoney(Double auditLoanMoney) {
        this.auditLoanMoney = auditLoanMoney;
    }

    public Double getContractLoanMoney() {
        return contractLoanMoney;
    }

    public void setContractLoanMoney(Double contractLoanMoney) {
        this.contractLoanMoney = contractLoanMoney;
    }

    public Double getConsultingFee() {
        return consultingFee;
    }

    public void setConsultingFee(Double consultingFee) {
        this.consultingFee = consultingFee;
    }

    public Double getAuditFee() {
        return auditFee;
    }

    public void setAuditFee(Double auditFee) {
        this.auditFee = auditFee;
    }

    public Double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getAccessFee() {
        return accessFee;
    }

    public void setAccessFee(Double accessFee) {
        this.accessFee = accessFee;
    }

    public Double getYearRate() {
        return yearRate;
    }

    public void setYearRate(Double yearRate) {
        this.yearRate = yearRate;
    }

    public Double getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public Integer getCreditorRightsType() {
        return creditorRightsType;
    }

    public void setCreditorRightsType(Integer creditorRightsType) {
        this.creditorRightsType = creditorRightsType;
    }

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public Date getCollectFinishTime() {
        return collectFinishTime;
    }

    public void setCollectFinishTime(Date collectFinishTime) {
        this.collectFinishTime = collectFinishTime;
    }

    public Date getMatchFinishTime() {
        return matchFinishTime;
    }

    public void setMatchFinishTime(Date matchFinishTime) {
        this.matchFinishTime = matchFinishTime;
    }

    public String getLoanContractPath() {
        return loanContractPath;
    }

    public void setLoanContractPath(String loanContractPath) {
        this.loanContractPath = loanContractPath;
    }
}