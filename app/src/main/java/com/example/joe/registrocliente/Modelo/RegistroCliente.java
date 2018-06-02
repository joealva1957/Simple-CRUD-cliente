package com.example.joe.registrocliente.Modelo;

import java.util.ArrayList;

public class RegistroCliente {

    ArrayList <Cliente> registro;


    // Constructor
    public RegistroCliente() {
        registro = new ArrayList<Cliente>();
    }// End

    // Insert a new curstomer in register

    public String insertCus( Cliente cliente ) {
        if( cliente != null ) {
            registro.add(cliente);
            return "Inserted";
        }
        return "Error";
    }// End

    // Select an existing customer in register

    public int selectCus( String id ) {
        String tmp;
        tmp = id;
        if( tmp != null ){
            for ( int i = 0; i < registro.size(); i++ ) {
                if( registro.get(i).getId().equalsIgnoreCase(tmp) ) {
                    return i;
                }
            }
        }
        return -1;
    }// End

    // Update an existing customer in register

    public String updateCus ( Cliente cliente, int pos ) {
        if( cliente != null && pos != -1 ){
            registro.get(pos).setNombre(cliente.getNombre());
            registro.get(pos).setApellido(cliente.getApellido());
            registro.get(pos).setCorreo(cliente.getCorreo());
            registro.get(pos).setId(cliente.getId());
            return "Updated";
        }
        return "Error";
    }

    // Delete an existing customer in register

    public String deleteCus (int pos) {
        if(pos != -1) {
            registro.remove(pos);
            return "Deleted";
        }
        return "Error";
    }

    public String getInfoCus(int pos){
        return registro.get(pos).toString();
    }

    public Cliente returnCli (int pos){
        return registro.get(pos);
    }

    public ArrayList<Cliente> getRegistro() {
        return registro;
    }
}
