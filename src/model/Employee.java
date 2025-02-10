package model;

import main.Logable;

public class Employee extends Person implements Logable {
    private int employeeId;
    private String password;
    private static final int EMPLOYEE_ID = 123;
    private static final String PASSWORD = "test";

    public Employee(String name, String surname, int DNI, int employeeId, String password) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }

    @Override
    public boolean login(int user, String password) {
        return user == EMPLOYEE_ID && password.equals(PASSWORD);
    }
}