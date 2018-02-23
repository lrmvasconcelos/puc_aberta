package br.pucminas.pucaberta.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by lucas on 28/04/17.
 */

public class IO_file {


    public static final String FILE_CONFIGURACAO = "config.txt";

    private static final String TAG = "IO_file";

    // application context
    public Context context;

    public IO_file(Context context){
        this.context = context;
    }

    public boolean salvar(String str, String nome_arquivo){

        boolean save = false;

        try {
            FileOutputStream fOut = context.openFileOutput(nome_arquivo, Context.MODE_PRIVATE);

            fOut.write(str.getBytes());
            fOut.close();

            save = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return save;
    }

    public String recuperar(String nome_arquivo){
        String temp="";

        try{
            FileInputStream fin = context.openFileInput(nome_arquivo);
            int c;
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }

            //string temp contains all the data of the file.
            fin.close();

        }catch (Exception e){

            return "";
        }

        return temp;
    }

    public void deletar(String nome_arquivo){
        context.deleteFile(nome_arquivo);
    }

    public boolean checkFile(String nome_arquivo){

        try {
            FileInputStream file = context.openFileInput(nome_arquivo);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();


            return false;
        }
    }

}

