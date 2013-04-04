package org.sandbox.userifc_java.demo;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sandbox.intro_java.util.Library;

/** DocComment:
 * <p>Introduction, basic syntax/features.</p> */
public class Main {
	static {
		// from cmdln: java -Dlog4j.configurationFile=path/log4j2.xml ...
		//System.setProperty("log4j.configurationFile", "log4j2.xml");
		// from cmdln: java -Dlogback.configurationFile=path/logback.xml ...
		System.setProperty("logback.configurationFile", "logback.xml");
	}
	private static final Logger rootLogger = LoggerFactory.getLogger(
		Main.class.getName());
    
    private static void run_demo(String progname, String rsrc_path,
            String name) {
        long timeIn_mSecs = System.currentTimeMillis();
        String greetStr, greetFile = "greet.txt";
        java.util.Date dt1 = new java.util.Date(timeIn_mSecs);
        
        java.util.regex.Pattern re = java.util.regex.Pattern.compile(
        //  "quit", java.util.regex.Pattern.CASE_INSENSITIVE);
            "(?i)quit");
        java.util.regex.Matcher m = re.matcher(name);
        System.out.format("%s match: %s to %s\n",
            m.matches() ? "Good" : "Does not", name, re.pattern());
        
        greetStr = Demo.greeting(rsrc_path, greetFile, name);
        System.out.format("%s\n%s!\n", dt1.toString(), greetStr);
    }
	
    private static void printUsage(String str, int status) {
        System.err.format("Usage: java %s [-h][-u name]\n",
			Main.class.getName());
        System.err.println(str);
        System.exit(status);
    }

	private static void parse_cmdopts(Map<String, String> optsMap, 
            String[] args) {
        String option = null;
		rootLogger.info("parse_cmdopts()");
        for (int i = 0, size = args.length; size > i; ++i) {
			option = args[i];
              
            if ('-' != option.charAt(0) || 1 == option.length())
                printUsage("Not an option: " + option, 1);
            switch (option.charAt(1)) {
                case 'h': printUsage("", 0);
                    break;
                case 'u': 
                    if ((size <= i + 1) || ('-' == args[i + 1].charAt(0)))
                        printUsage("Missing argument for " + option, 1);
                    optsMap.put("name", args[++i]);
                    break;
                default: printUsage("Unknown option: " + option, 1);
          	}
		}
    }

    /** Brief description.
     * @param args - array of command-line arguments */
    public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		Map<String, String> optsMap = new HashMap<String, String>() {
		    static final long serialVersionUID = 660L;
		    { put("name", "World"); }
		};
        parse_cmdopts(optsMap, args);
	    
	    String rsrc_path = null != System.getenv("RSRC_PATH") ? 
			System.getenv("RSRC_PATH") :
			System.getProperty("rsrcPath", "src/main/resources");
        
        java.io.InputStream iniStrm, jsonStrm, yamlStrm;
        try {
			iniStrm = new java.io.FileInputStream(rsrc_path + "/prac.conf");
			jsonStrm = new java.io.FileInputStream(rsrc_path + "/prac.json");
			yamlStrm = new java.io.FileInputStream(rsrc_path + "/prac.yaml");
		} catch (java.io.IOException exc0) {
			System.out.format("(exc: %s) Bad env var RSRC_PATH: %s\n",
				exc0, rsrc_path);
			
			iniStrm = Main.class.getResourceAsStream("/prac.conf");
			jsonStrm = Main.class.getResourceAsStream("/prac.json");
			yamlStrm = Main.class.getResourceAsStream("/prac.yaml");
		}
        
        org.ini4j.Ini ini_cfg = new org.ini4j.Ini();
        
    	javax.json.JsonObject jsonobj = null;
    	javax.json.JsonReader rdr = null;
        try {
        	ini_cfg.load(iniStrm);
        	rdr = javax.json.Json.createReader(jsonStrm);
			jsonobj = rdr.readObject();
        } catch (java.io.IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        } finally {
			rdr.close();
		}
		
		org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
		Map<String, Object> yamlmap = yaml.loadAs(yamlStrm,
			java.util.<String, Object>HashMap.class);
		Map<String, Object> user1map = (Map<String, Object>)yamlmap.get("user1");
        
    	String[][] tup_arr = {
			{ini_cfg.toString(), ini_cfg.get("default").get("domain"),
				ini_cfg.get("user1").get("name")},
			{jsonobj.toString(), jsonobj.getString("domain"),
				jsonobj.getJsonObject("user1").getString("name")},
			{yamlmap.toString(), (String)yamlmap.get("domain"),
				(String)user1map.get("name")}
		};
		
        //System.out.format("ini4j config start section: %s\n",
    	//	ini_cfg.keySet().iterator().next());
    	for (int i = 0; tup_arr.length > i; ++i) {
			System.out.format("config: %s\n", tup_arr[i][0]);
			System.out.format("domain: %s\n", tup_arr[i][1]);
			System.out.format("user1Name: %s\n\n", tup_arr[i][2]);
		}
    	
    	run_demo(Main.class.getName(), rsrc_path, optsMap.get("name"));
	    
	    rootLogger.debug("exiting main()");
    }
}
