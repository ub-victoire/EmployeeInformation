package org.emp;

import org.client.Client;
import org.company.Company;
import org.project.Project

public class Employee (){

    public void empName(){
        System.out.println("Employee Name: Chris Gloria");
    }

    public static void main(String[] argd){
        var employee = new Employee();
        var client = new Client();
        var company = new Company();
        var project = new Project();


        employee.empName();
        company.companyName();
        client.clientName();
        project.projectName();
    }
}
