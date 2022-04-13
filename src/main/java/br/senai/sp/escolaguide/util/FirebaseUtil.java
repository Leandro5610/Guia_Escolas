package br.senai.sp.escolaguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
@Service
public class FirebaseUtil {
	// variavel para guardar as credencias do firebase
	private Credentials cred;
	// variavel para acessar o storage
	private Storage storage;
	// constante para o nome do buckte
	private final String BUCKET_NAME = "escolaguide.appspot.com";
	// constate para o prefixo da URL
	private final String PREFFIX = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/";
	// constante para sufixo da URL
	private final String SUFFIX = "?alt=media";
	// constante para a URL
	private final String DOWNLOAD_URL = PREFFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		//buscar as credenciais ( aquivo JSON)
		Resource resource = new ClassPathResource("chavefirebase.json");
		
		try {
			// ler o arquivo ára obter as credenciais
			cred = GoogleCredentials.fromStream(resource.getInputStream());
			
			//acessao oderviço de storage
			storage = StorageOptions.newBuilder().setCredentials(cred).build().getService();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}
	public String uploadFile(MultipartFile arquivo) throws IOException {
		//gera uma string aleatoria para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString()+getExtensao(arquivo.getOriginalFilename());
		//criar um BlobId
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		
		//blobinfo a partir do bloid
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		//manda o info para o storage passando os bytes do arquivo para ele
		storage.create(blobInfo, arquivo.getBytes());
		//retornar a url para acessar o arquivo
		
		return String.format(DOWNLOAD_URL,nomeArquivo);
	}
	
	//retorna a extensao do arquivo atraves do nome
	private String getExtensao(String nomeArquivo) {
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	public void removeFile(String nomeArquivo) {
		//retira o prefixo e sufixo do arquivo
		nomeArquivo = nomeArquivo.replace(PREFFIX, "").replace(SUFFIX, "");
		// pego o blob atraves do nome do arquivo
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		//deleta o arquivo
		storage.delete(blob.getBlobId());
	}
}
