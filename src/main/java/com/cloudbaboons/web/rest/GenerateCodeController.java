package com.cloudbaboons.web.rest;

import static org.springframework.http.ResponseEntity.ok;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for generating code
 */
@RestController
@RequestMapping("/api")
public class GenerateCodeController {

    private final Logger log = LoggerFactory.getLogger(GenerateCodeController.class);

    @GetMapping("/generate")
    public ResponseEntity<String> getCurrentSessions() {
    	String response = new String("Generated");
    	
    	 try {
             String target = new String("/apps/kapil/baboons/generate.sh test-app2  com.test  com/test   8189   postgresql  cb");

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

}
