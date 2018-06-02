package com.example.joe.registrocliente;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joe.registrocliente.Modelo.Cliente;
import com.example.joe.registrocliente.Modelo.RegistroCliente;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class cliente_act_registro extends AppCompatActivity {

    // Declare components
    EditText campoNombre, campoApellido, campoCorreo, campoIdentificador;
    Button btnAgregar, btnBuscar, btnActualizar, btnEliminar;

    // Declare object
    Cliente cli;

    // Create instance of object
    RegistroCliente rg = new RegistroCliente();

    // Declare components
    Uri srcImg;
    ImageView imgCustomer;

    // Importante
    private final int GALERIA_IMAGENES=1;
    private final int CAMARA=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_lyt_registro);

        // Mapping SimpleText
        campoNombre = (EditText) findViewById(R.id.editTextNombre);
        campoApellido = (EditText) findViewById(R.id.editTextApellido);
        campoCorreo = (EditText) findViewById(R.id.editTextCorreo);
        campoIdentificador = (EditText) findViewById(R.id.editTextIdentificador);

        // Mapping buttons
        btnAgregar = (Button) findViewById(R.id.btnInsert);
        btnBuscar = (Button) findViewById(R.id.btnSelect) ;
        btnActualizar = (Button)findViewById(R.id.btnUpdate);
        btnEliminar = (Button)findViewById(R.id.btnDelete);

        //Mapping ImageView
        imgCustomer = (ImageView) findViewById(R.id.imageView2);

        // Listener Insert
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // Check fields
                if (campoNombre.getText().toString().isEmpty() || campoApellido.getText().toString().isEmpty() || campoCorreo.getText().toString().isEmpty() || campoIdentificador.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields required", Toast.LENGTH_SHORT).show();
                } else {
                    if (rg.selectCus(campoIdentificador.getText().toString()) == -1) {
                        cli = new Cliente(campoNombre.getText().toString(), campoApellido.getText().toString(), campoCorreo.getText().toString(), campoIdentificador.getText().toString(), srcImg);
                        String mensaje = rg.insertCus(cli);
                        cleanFields();
                        Toast.makeText(cliente_act_registro.this, mensaje, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Customer already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Listener Select
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( campoIdentificador.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(),"Fill ID is required",Toast.LENGTH_SHORT).show();
                } else {
                    String id = campoIdentificador.getText().toString();
                    if( rg.selectCus(id) != -1 ) {
                        cli = rg.returnCli(rg.selectCus(id));
                        campoNombre.setText(cli.getNombre());
                        campoApellido.setText(cli.getApellido());
                        campoCorreo.setText(cli.getCorreo());
                    }else {
                        Toast.makeText(getApplicationContext(), "Customer doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Listener Update
        btnActualizar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                if( campoNombre.getText().toString().isEmpty() ||
                    campoApellido.getText().toString().isEmpty() ||
                    campoCorreo.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields required",Toast.LENGTH_SHORT).show();
                }else {
                    int pos = rg.selectCus(campoIdentificador.getText().toString());
                    if( pos != -1) {
                        cli = new Cliente(campoNombre.getText().toString(), campoApellido.getText().toString(), campoCorreo.getText().toString(), campoIdentificador.getText().toString(), srcImg);
                        String mensaje = rg.updateCus(cli, pos);
                        Toast.makeText( cliente_act_registro.this, mensaje,Toast.LENGTH_SHORT).show();
                        cleanFields();
                    }else {
                        Toast.makeText(getApplicationContext(), "Customer not found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Listener Delete
        btnEliminar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v){
                if( campoIdentificador.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Field ID is required",Toast.LENGTH_SHORT).show();
                }else {
                    String id = campoIdentificador.getText().toString();
                    if(rg.selectCus(id) != -1) {
                        String mensaje = rg.deleteCus(rg.selectCus(id));
                        cleanFields();
                        Toast.makeText(cliente_act_registro.this, mensaje,Toast.LENGTH_SHORT);
                    }else {
                        Toast.makeText(getApplicationContext(), "Customer not found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Listener image customer
        imgCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Mensaje en ventana
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(cliente_act_registro.this);
                alertDialog.setMessage("Seleccione una opción");
                alertDialog.setTitle("Imagen de cliente");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);

                // Al boton positivo con la opción camara y si lo selecciona se realiza un Intent para obtener los datos
                alertDialog.setPositiveButton("Cámara", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(intent, CAMARA);
                        startActivityForResult(intent, CAMARA);
                    }
                });
                //Al boton negativo con la opcion de galeria
                alertDialog.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        //Para recibir un resultado cuando se inicia un activity
                        // se llama al startActivityForResult
                        startActivityForResult(Intent.createChooser(intent,"Seleccionar foto"),
                                GALERIA_IMAGENES);
                    }
                });
            }
        });
        //imgCustomer

    }// oncreate


    // important
    /*Este metodo recibe 3 argumentos
    El código de la solicitud que se envio por medio startActivityForResult
    Un codigo de resultado(Este puede ser Result_OK si la accion se ejcuto correctamente
    o Result_Canceled si el usuario cancelo la operacion)
    Un intent con la informacion
    */
    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode==RESULT_OK){
            switch(reqCode){
                // Al fotoTemp se le asigna los datos que se encuentran el intent data
                //Al imageView de user se le carga la direccion de la imagen que acabo
                // de guardar en fotoTemp para que se haga visible en la aplicacion

                case GALERIA_IMAGENES:
                    srcImg = data.getData();
                    imgCustomer.setImageURI(srcImg);
                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente",
                            Toast.LENGTH_SHORT).show();
                    break;
                case CAMARA:
                    if (data!=null){
                        Bitmap thumbail = (Bitmap)data.getExtras().get("data");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        thumbail.compress(Bitmap.CompressFormat.JPEG,90,bytes);
                        File destination = new File(Environment.getExternalStorageDirectory(),
                                System.currentTimeMillis()+".jpg");
                        FileOutputStream fo;
                        try{
                            destination.createNewFile();
                            fo = new FileOutputStream(destination);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        imgCustomer.setImageBitmap(thumbail);
                        srcImg = data.getData();
                        Toast.makeText(getApplicationContext(),"Exito Camara",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }else{
            Toast.makeText(getApplicationContext(),"Ha ocurrido un error" +
                    " en la accion",Toast.LENGTH_SHORT).show();
        }
    }//Fin del metodo

    // Clean fields

    public void cleanEditTxtNombre() {
        campoNombre.setText("");
    }

    public void cleanEditTxtApellido() {
        campoApellido.setText("");
    }

    public void cleadEditTxtCorreo() {
        campoCorreo.setText("");
    }

    public void cleanEditTxtIdentificador() {
        campoIdentificador.setText("");
    }

    public void cleanFields() {
        campoNombre.setText("");
        campoApellido.setText("");
        campoCorreo.setText("");
        campoIdentificador.setText("");
    }

    public void closeFieldId() {
        //campoNombre.setText("");
        //campoApellido.setText("");
        //campoCorreo.setText("");
        campoIdentificador.setEnabled(false);
    }

}// End
