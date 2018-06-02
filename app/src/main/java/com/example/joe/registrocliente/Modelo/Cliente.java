package com.example.joe.registrocliente.Modelo;

import android.net.Uri;

public class Cliente {

    //tributos
    String nombre;
    String apellido;
    String correo;
    String id;
    private Uri imgCliente;

    public Cliente(String nombre, String apellido, String correo, String id, Uri imgCliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.id = id;
        this.imgCliente = imgCliente;
    }

    public Cliente() {
        nombre = "";
        apellido = "";
        correo = "";
        id = "";
        imgCliente = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getImgCliente() {
        return imgCliente;
    }

    public void setImgCliente(Uri imgCliente) {
        this.imgCliente = imgCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", id='" + id + '\'' +
                ", imgCliente=" + imgCliente +
                '}';
    }
}//fin cliente
