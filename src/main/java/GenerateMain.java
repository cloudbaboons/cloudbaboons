import java.io.*;
public class GenerateMain {

        public static void main(String[] args) throws Exception {
                try {
                        String target = new String("/apps/kapil/baboons/generate.sh");

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
                } catch (Throwable t) {
                        t.printStackTrace();
                }
        }
}