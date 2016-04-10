/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

/**
 *
 * @author Käyttäjä
 */
public class FileUserDao implements UserDao{
    
    String kayttajatiednimi, salasanatiednimi;
    File kayttajat;
    File salasanat;
    Scanner lukija, salasanalukija;
    
    public FileUserDao(String kayttajatiedosto, String salasanatiedosto){
        salasanatiednimi = salasanatiedosto;
        kayttajatiednimi = kayttajatiedosto;
        kayttajat = new File(kayttajatiedosto);
        salasanat = new File(salasanatiedosto);
        lukija = null;
        salasanalukija = null;
    }
    
    private void avaalukijat(){
        try{
            lukija = new Scanner(kayttajat);
            salasanalukija = new Scanner(salasanat);
        }catch(Exception e){
            System.out.println("Tiedoston lukeminen epäonnistui " + e.getMessage());
        }
    }
    
    private void suljelukijat(){
        lukija.close();
        salasanalukija.close();
    }
    
    @Override
    public List<User> listAll(){
        List<User> users = new ArrayList<User>();
        avaalukijat();
        while(lukija.hasNextLine()){
            String nimi = lukija.nextLine();
            String salasana = salasanalukija.nextLine();
            users.add(new User(nimi, salasana));
        }
        suljelukijat();
        return users;
    }
    
    @Override
    public User findByName(String name){
        avaalukijat();
        while(lukija.hasNext()){
            String nimi = lukija.nextLine();
            String salasana = salasanalukija.nextLine();
            if(nimi.contains(name)) return new User(nimi, salasana);
        }
        suljelukijat();
        return null;
    }
    
    @Override
    public void add(User user){
        try{
        FileWriter kirjoittaja = new FileWriter(kayttajatiednimi, true);
        FileWriter salasanakirjoittaja = new FileWriter(salasanatiednimi, true);
        kirjoittaja.write(user.getUsername());
        salasanakirjoittaja.write(user.getPassword());
        kirjoittaja.close();
        salasanakirjoittaja.close();
        }catch(Exception e){
            System.out.println("Virhe, ei voida kirjoittaa tiedostoon " + e.getMessage());
        }
    } 
    
}
