package ru.ncedu.java.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * un1acker
 * 11.03.2015
 */
public class BusinessCardImpl implements BusinessCard {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String department;
    private int salary;
    private String phoneNumber;

    public BusinessCardImpl() {
        this.firstName = null;
        this.lastName = null;
        this.age = 0;
        this.gender = null;
        this.department = null;
        this.salary = 0;
        this.phoneNumber = null;
    }

    @Override
    public BusinessCard getBusinessCard(Scanner scanner) {
        scanner.useDelimiter(";");
        BusinessCardImpl bussinCard = null;
        try {
            //Chuck;Norris;DSI;10-04-1940;M;1000;1234567890
            bussinCard = new BusinessCardImpl();
            bussinCard.setFirstName(scanner.next("[A-Za-z-]+"));
            bussinCard.setLastName(scanner.next("[A-Za-z-]+"));
            bussinCard.setDepartment(scanner.next("[A-Za-z]+"));
            bussinCard.setAge(this.currentAge(scanner.next("\\d{2}-\\d{2}-\\d{4}")));
            bussinCard.setGender(this.getFormatGender(scanner.next("M|F")));
            bussinCard.setSalary(scanner.nextInt());
            bussinCard.setPhoneNumber(this.getFormatPhoneNumber(scanner.next("\\d{10}")));
        } catch (InputMismatchException ex1) {
            throw new InputMismatchException("Input data does not correspond to the data format : " + ex1);
        } catch (NoSuchElementException ex2) {
            throw new NoSuchElementException("Input stream has not enough values to construct BusinessCard : " + ex2);
        } catch (ParseException ex3) {
            ex3.printStackTrace();
        } finally {
            scanner.close();
        }
        return bussinCard;
    }

    @Override
    public String getEmployee() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String getDepartment() {
        return this.department;
    }

    @Override
    public int getSalary() {
        return this.salary;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        StringBuilder businessCard = new StringBuilder()
                .append("Employee: " + this.getEmployee() + "\n")
                .append("Department: " + this.getDepartment() + "\n")
                .append("Salary: " + this.getSalary() + "\n")
                .append("Age: " + this.getAge() + "\n")
                .append("Gender: " + this.getGender() + "\n")
                .append("Phone: " + this.getPhoneNumber() + "\n");
        return businessCard.toString();
    }

    private int currentAge(String birthDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(birthDate);
        Calendar employeeDate = Calendar.getInstance();
        employeeDate.setTime(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - employeeDate.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < employeeDate.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == employeeDate.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < employeeDate.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    private String getFormatPhoneNumber(String phoneNumber) {
        //+7 123-456-78-90
        String formatedNumber = String.format("+7 %s-%s-%s-%s",
                phoneNumber.substring(0, 3),
                phoneNumber.substring(3, 6),
                phoneNumber.substring(6, 8),
                phoneNumber.substring(8, 10));
        return formatedNumber;
    }

    private String getFormatGender(String gender) {
        return ("M".equals(gender)) ? "Male" : "Female";
    }

}
