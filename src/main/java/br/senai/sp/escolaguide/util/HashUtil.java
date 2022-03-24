package br.senai.sp.escolaguide.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

import br.senai.sp.escolaguide.model.Adimistrador;

public class HashUtil{
	public static String hash256(String palavra) {
		//"tempero" para o hash
		String salt = "b@n@n@";
		//acrescentando o tempero
		palavra = palavra + salt;
		//criar o hashe armazenar na String
		String sha256 = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString(); 
		//retorna o hash
		return sha256;
	}
}
