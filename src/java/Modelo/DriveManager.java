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
    private final String DRIVEMANAGER = "org.postgresql.Drive";
    private final String URL = "jdbc:postgresql://localhost:5432/vacaciones";
    private final String USER = "postgres";
    private final String PAS = "root";
    public DriveManager(){
        
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://ec2-52-21-136-176.compute-1.amazonaws.com");
        this.setUsername("jczbvgyrxyuqov");
        this.setPassword("5807815e51a4f1f66283b225903e210522ddd2352790caba014f5cdae2c31ee0");
        
        
    }
    /*
    public DriveManager(){
        this.setDriverClassName("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://localhost:5432/vacaciones");
        this.setUsername("postgres");
        this.setPassword("root");
    }*/
}
