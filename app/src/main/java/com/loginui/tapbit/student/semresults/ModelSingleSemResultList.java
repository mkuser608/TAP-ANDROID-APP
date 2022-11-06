package com.loginui.tapbit.student.semresults;

import java.math.BigDecimal;

public class ModelSingleSemResultList {

    private long id;
    private  String registrationNo;
    private int semNo;
    private BigDecimal sGPA;
    private BigDecimal cGPA;
    private String remarks;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public int getSemNo() {
        return semNo;
    }

    public void setSemNo(int semNo) {
        this.semNo = semNo;
    }

    public BigDecimal getsGPA() {
        return sGPA;
    }

    public void setsGPA(BigDecimal sGPA) {
        this.sGPA = sGPA;
    }

    public BigDecimal getcGPA() {
        return cGPA;
    }

    public void setcGPA(BigDecimal cGPA) {
        this.cGPA = cGPA;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
