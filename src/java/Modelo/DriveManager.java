/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author GEEK STORE
 */
public class DriveManager extends DriverManagerDataSource{
    public DriveManager(){
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://ec2-3-217-113-25.compute-1.amazonaws.com/dfq426bctojb4q");
        this.setUsername("kkpyqhrsqaehba");
        this.setPassword("e5698668b1d3334b6a6f5a8af8db54740bb49b5e69bda7bb4175191253441910");
    }
    /*
    public DriveManager(){
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://localhost:5432/vacaciones");
        this.setUsername("postgres");
        this.setPassword("root");
    }*/
}
