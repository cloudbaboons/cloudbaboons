package com.cloudbaboons.web.rest;

import static org.springframework.http.ResponseEntity.ok;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
/**
 * REST controller for generating code
 */
@RestController
@RequestMapping("/api")
public class GenerateCodeController {

    private static final String SPACE = " ";
	private final Logger log = LoggerFactory.getLogger(GenerateCodeController.class);
	
	
	@Value("${config.root}")
	private static String root;
	
	

    @GetMapping("/generate")
    public ResponseEntity<String> generate(String appName,String packageName,String packageFolder,String portNum,
    		String databaseType, String appPrefix) {
    	String response = new String("Generated");
    	
    	String scriptPath = root + "generate.sh";
   
    
    StringBuilder builder = new StringBuilder();
    	
    	 try {
			builder.append(scriptPath).append(SPACE).append(appName).append(SPACE).append(packageName).append(SPACE)
					.append(packageFolder).append(SPACE).append(portNum).append(SPACE).append(databaseType)
					.append(SPACE).append(appPrefix);
			
		    String target = builder.toString();//new String("/apps/kapil/baboons/generate.sh test-app2  com.test  com/test   8189   postgresql  cb");

             Runtime rt = Runtime.getRuntime();
             Process proc = rt.exec(target);
             proc.waitFor();
             StringBuffer output = new StringBuffer();
             BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
             String line = "";                       
             while ((line = reader.readLine())!= null) {
                     output.append(line + "\n");
             }
             System.out.println("### " + output);
             
             response = output.toString();
     } catch (Throwable t) {
             t.printStackTrace();
     }
    	 
    	 return ok()
	                .cacheControl(CacheControl.noStore())
	                .body(response);
    }
    
	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param) throws IOException {

		  String 	FILE_PATH = "/apps/kapil/baboons" + "/data/app.zip";
	   
     File file = new File(FILE_PATH);
     InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

     return ResponseEntity.ok()
           .header(HttpHeaders.CONTENT_DISPOSITION,
                 "attachment;filename=" + file.getName())
           .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length())
           .body(resource);
     
	}
	
	
	   // Using ResponseEntity<InputStreamResource>
	   @GetMapping("/download1")
	   public ResponseEntity<InputStreamResource> downloadFile1() throws IOException {

		     String 	FILE_PATH = "/apps/kapil/baboons" + "/data/app.pdf";
		   
	      File file = new File(FILE_PATH);
	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	      return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                  "attachment;filename=" + file.getName())
	            .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
	            .body(resource);
	   }

	   // Using ResponseEntity<ByteArrayResource>
	   @GetMapping("/download2")
	   public ResponseEntity<ByteArrayResource> downloadFile2() throws IOException {
		   String 	FILE_PATH = "/apps/kapil/baboons" + "/data/app.pdf";
	      Path path = Paths.get(FILE_PATH);
	      byte[] data = Files.readAllBytes(path);
	      ByteArrayResource resource = new ByteArrayResource(data);

	      return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                  "attachment;filename=" + path.getFileName().toString())
	            .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
	            .body(resource);
	   }

	   // Using HttpServletResponse
	   @GetMapping("/download3")
	   public void downloadFile3(HttpServletResponse resonse) throws IOException {
		   String 	FILE_PATH = "/apps/kapil/baboons" + "/data/app.pdf";
		   
		   File file = new File(FILE_PATH);

	      resonse.setContentType("application/pdf");
	      resonse.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
	      BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
	      BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
	      
	      byte[] buffer = new byte[1024];
	      int bytesRead = 0;
	      while ((bytesRead = inStrem.read(buffer)) != -1) {
	        outStream.write(buffer, 0, bytesRead);
	      }
	      outStream.flush();
	      inStrem.close();
	   }

}
