package com.netcracker.edu.java.tasks;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumberImpl implements ComplexNumber {
    private double realNum;
    private double imagNum;

    public ComplexNumberImpl() {
        this.realNum = 0;
        this.imagNum = 0;
    }

    public ComplexNumberImpl(double realNum, double imagNum) {
        this.realNum = realNum;
        this.imagNum = imagNum;
    }

    @Override
    public double getRe() {
        return this.realNum;
    }

    @Override
    public double getIm() {
        return this.imagNum;
    }

    @Override
    public boolean isReal() {
        return this.imagNum == 0;
    }

    @Override
    public void set(double re, double im) {
        this.realNum = re;
        this.imagNum = im;
    }

    @Override
    public void set(String value) throws NumberFormatException {
        String complexNumber = this.toComplexForm(value);
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)([+-]?\\d*\\.?\\d*)i");
        Matcher matcher = pattern.matcher(complexNumber);
        if(matcher.matches()) {
            this.set(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)));
        } else {
            throw new NumberFormatException();
        }

    }

    @Override
    public ComplexNumber copy() {
        ComplexNumber num = null;
        try {
            num = this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public ComplexNumber clone() throws CloneNotSupportedException {
        Object cloneNum = null;
        try {
            cloneNum = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (ComplexNumber) cloneNum;
    }

    @Override
    public int compareTo(ComplexNumber other) {
        return (int)((Math.pow(this.getRe(), 2.0) + Math.pow(this.getIm(), 2.0)) -
                (Math.pow(other.getRe(), 2.0) + Math.pow(other.getIm(), 2.0)));
    }

    @Override
    public void sort(ComplexNumber[] array) {
        Arrays.sort(array);
    }

    @Override
    public ComplexNumber negate() {
        this.set((-1.0) * this.getRe(),(-1.0) * this.getIm());
        return this;
    }

    @Override
    public ComplexNumber add(ComplexNumber arg2) {
        this.set(this.getRe() + arg2.getRe(), this.getIm() + arg2.getIm());
        return this;
    }

    @Override
    public ComplexNumber multiply(ComplexNumber arg2) {
        //(a*c-b*d)+(b*c+a*d)i
        //a+bi and arg2 is c+di
        double real = this.getRe() * arg2.getRe() - this.getIm() * arg2.getIm();
        double imag = this.getIm() * arg2.getRe() + this.getRe() * arg2.getIm();
        this.set(real, imag);
        return this;
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof ComplexNumber)) {
            return false;
        }
        if(o == this) {
            return true;
        }
        ComplexNumber complexNum = (ComplexNumber) o;
        return (this.getRe() == complexNum.getRe() && this.getIm() == complexNum.getIm());
    }

    public String toString() {
        if(this.getIm() == 0) {
            return this.getRe() + "";
        } else if(this.getRe() == 0) {
            return this.getIm() + "i";
        } else if(this.getIm() > 0) {
            return this.getRe() + "+" + this.getIm() + "i";
        } else {
            return this.getRe() + "" + this.getIm() + "i";
        }
    }

    public String toComplexForm(String value) {
        String complexNum = "";
        int amountOfPlus = 0;
        int amountOfMinus = 0;

        if(value.length() == 0) {
            complexNum = "0+0i";
        } else if(!value.contains("i")) {
            complexNum = value + "+0i";
        } else if(value.contains("-i")) {
            complexNum = value.replace("-i", "-1i");
        } else if(value.contains("+i")) {
            complexNum = value.replace("+i", "+1i");
        } else if(value.contains("i") && value.length() == 1) {
            complexNum = value.replace("i", "1i");
        }

        if(complexNum.isEmpty()) {
            complexNum = value;
        }
        for(int i = 0; i < value.length(); i++) {
            if(value.charAt(i) == '+') {
                ++amountOfPlus;
            }
            if(value.charAt(i) == '-') {
                ++amountOfMinus;
            }
        }
        if(amountOfPlus == 0 && amountOfMinus == 1 && value.contains("i")) {
            complexNum = "0" + complexNum;
        } else if(amountOfMinus == 0 && amountOfPlus == 0 && value.contains("i")) {
            complexNum = "0+" + complexNum;
        }

        return complexNum;
    }
}
