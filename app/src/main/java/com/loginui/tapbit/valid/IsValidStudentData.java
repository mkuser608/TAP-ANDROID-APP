package com.loginui.tapbit.valid;

public class IsValidStudentData {

    public boolean isValidRegistrationNumber(String reg){
        return reg.length()>9;
    }

    public boolean isValidMobileNumber(String number){
        return  number.length()==10;
    }


}
