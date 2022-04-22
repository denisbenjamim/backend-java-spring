package br.com.alura.challenger.backendjava.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeradorSenha {
    private final static List<String> TODAS_LETRAS_MINUSCULAS = getTodasLetrasMinisculas();
    private final static List<String> TODAS_LETRAS_MAIUSCULAS = getTodasLetrasMaiusculas();
    private final static List<String> NUMEROS_ZERO_ATE_NOVE = getNumerosZeroAteNove();
    
    private final static List<String> MIX_CARACTERES_VALIDOS = new ArrayList<String>(){{
        addAll(TODAS_LETRAS_MINUSCULAS);
        addAll(TODAS_LETRAS_MAIUSCULAS);
        addAll(NUMEROS_ZERO_ATE_NOVE);
    }};

    private final static Random random = new Random();
    /**
     * Gera uma senha com 6 caracteres alfanumericos aleatorios
     * @return
     */
    public static String getSenhaAleatoria(){
        return getSenhaAleatoria(6);
    }

    /**
     * Gera uma senha de uma quantidade especifica de caracteres alfanumericos aleatorios
     * @param quantidadeCaracteres
     * @return
     */
    public static String getSenhaAleatoria(int quantidadeCaracteres){
        Collections.shuffle(MIX_CARACTERES_VALIDOS);
        StringBuilder senhaBuilder = new StringBuilder();
        
        int indiceRandomico;
        int sizeMaxCaracteresValidos = MIX_CARACTERES_VALIDOS.size();
        for(int i=0; i < quantidadeCaracteres; i++){
            indiceRandomico = random.ints(0, sizeMaxCaracteresValidos).findFirst().getAsInt();
            senhaBuilder.append(MIX_CARACTERES_VALIDOS.get(indiceRandomico));
        }

        return senhaBuilder.toString();
    }

    private static List<String> getCaracteresEntre(char caractereInicial, char caracterFinal){
        final List<String> caracteres =  new ArrayList<>();
        final int codigoPrimeiraLetraMinuscula = caractereInicial;
        final int codigoUltimaLetraMiniuscula = caracterFinal;
        
        for(int i = codigoPrimeiraLetraMinuscula; i<= codigoUltimaLetraMiniuscula ;i++){
            caracteres.add(String.valueOf((char)i ));
        }
        
        return Collections.unmodifiableList(caracteres);
    }

    private static List<String> getTodasLetrasMinisculas(){
        return getCaracteresEntre('a', 'z');
    }

    private static List<String> getTodasLetrasMaiusculas(){
        return getCaracteresEntre('A', 'Z');
    }

    private static List<String> getNumerosZeroAteNove(){
        return getCaracteresEntre('0', '9');
    }
}
