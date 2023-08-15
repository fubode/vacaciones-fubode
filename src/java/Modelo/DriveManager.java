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
        /*
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://localhost:5433/vacaciones-fubode");
        this.setUsername("postgres");
        this.setPassword("root");        
        */
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://localhost:5432/vacaciones");
        this.setUsername("postgres");
        this.setPassword("/+Lfba49TXtomic");        
    }   
}