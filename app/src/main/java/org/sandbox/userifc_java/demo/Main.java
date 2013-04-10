package org.sandbox.userifc_java.demo;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gnome.gtk.Gtk;
import org.gnome.gtk.TextView;
import org.freedesktop.bindings.Version;

import org.sandbox.wrapper_java.curses.CursesC;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_WINDOW;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_PANEL;
import org.sandbox.wrapper_java.tcltk.TcltkC;
import org.sandbox.wrapper_java.tcltk.SWIGTYPE_p_Tcl_Interp;

import org.sandbox.intro_java.util.Library;
//import org.sandbox.userifc_java.gtk.HelloController;
//import org.sandbox.userifc_java.curses.HelloController;

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
	
	private static interface IRunMethod{ public void run_method(
		String progname, String rsrc_path, String name);
	}
    
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
    
    private static void run_demo_gtk(String progname, String rsrc_path,
            String name) {
        Gtk.init(null);
    
        long timeIn_mSecs = System.currentTimeMillis();
        String greetFile = "greet.txt";
        java.util.Date dt1 = new java.util.Date(timeIn_mSecs);
        
        java.util.regex.Pattern re = java.util.regex.Pattern.compile(
        //  "quit", java.util.regex.Pattern.CASE_INSENSITIVE);
            "(?i)quit");
        java.util.regex.Matcher m = re.matcher(name);
        String pretext = String.format("%s match: %s to %s\n(Java %s) Java-Gnome version %s\n%s\n",
            m.matches() ? "Good" : "Does not", name, re.pattern(),
            System.getProperty("java.version"), Version.getVersion(),
            dt1.toString());
        org.sandbox.userifc_java.gtk.HelloController uicontroller = 
            new org.sandbox.userifc_java.gtk.HelloController(greetFile,
            rsrc_path);
        ((TextView)uicontroller.getView1().widgets.get("textview1"
            )).getBuffer().setText(pretext);
        
        Gtk.main();
    }
    
    private static void run_demo_curses(String progname, String rsrc_path,
            String name) {
        long timeIn_mSecs = System.currentTimeMillis();
        String greetFile = "greet.txt";
        java.util.Date dt1 = new java.util.Date(timeIn_mSecs);
        
        java.util.regex.Pattern re = java.util.regex.Pattern.compile(
        //  "quit", java.util.regex.Pattern.CASE_INSENSITIVE);
            "(?i)quit");
        java.util.regex.Matcher m = re.matcher(name);
        String pretext = String.format("%s match: %s to %s\n(Java %s) Curses %s TUI\n%s\n",
            m.matches() ? "Good" : "Does not", name, re.pattern(),
            System.getProperty("java.version"), "???",
            dt1.toString());
        org.sandbox.userifc_java.curses.HelloController uicontroller = 
            new org.sandbox.userifc_java.curses.HelloController(greetFile,
            rsrc_path);
        CursesC.wattron(uicontroller.getView1().stdscr, 
            org.sandbox.userifc_java.curses.HelloView.Keys.A_REVERSE.value);
        CursesC.mvwaddstr(uicontroller.getView1().stdscr, 1, 1, pretext);
        CursesC.wattroff(uicontroller.getView1().stdscr, 
            org.sandbox.userifc_java.curses.HelloView.Keys.A_REVERSE.value);
        uicontroller.run();
    }
    
    private static int tk_AppInit(SWIGTYPE_p_Tcl_Interp interp) {
		if (TcltkC.Tcl_Init(interp) != 0) {
		  	System.err.printf("Error: Initializing Tcl!\n");
		  	return 1;
		}
		if (TcltkC.Tk_Init(interp) != 0) {
		  	System.err.printf("Error: Initializing Tk!\n");
		  	return 1;
		}
		return 0;
	}
    
    private static void run_demo_tcltk(String progname, String rsrc_path,
            String name, String[] extrav) {
        long timeIn_mSecs = System.currentTimeMillis();
        String greetFile = "greet.txt";
        java.util.Date dt1 = new java.util.Date(timeIn_mSecs);
        
        java.util.regex.Pattern re = java.util.regex.Pattern.compile(
        //  "quit", java.util.regex.Pattern.CASE_INSENSITIVE);
            "(?i)quit");
        java.util.regex.Matcher m = re.matcher(name);
        String pretext = String.format("%s match: %s to %s\n(Java %s) Tcl/Tk %s\n%s\n",
            m.matches() ? "Good" : "Does not", name, re.pattern(),
            System.getProperty("java.version"), "???",
            dt1.toString());
        
        String uiform = (null == extrav || 1 > extrav.length) ?
		  	rsrc_path + "/" + "tcltk/helloForm-tk.tcl" : 
		  	rsrc_path + "/" + extrav[0];
		String[] defaultArgs = {"--", uiform};
		String[] tkArgs = new String[(null == extrav || 1 > extrav.length) ?
		    3 : extrav.length+1];
		System.arraycopy(defaultArgs, 0, tkArgs, 0, 2);
	
		if (null == extrav || 1 > extrav.length)
		  	tkArgs[2] = "-name=helloForm-tk";
		else
		  	System.arraycopy(extrav, 1, tkArgs, 2, extrav.length-1);
	
		SWIGTYPE_p_Tcl_Interp interp = TcltkC.Tcl_CreateInterp();
		tk_AppInit(interp);
		TcltkC.Tcl_Eval(interp, String.format("set argv {%s}",
			String.join(" ", Arrays.copyOfRange(tkArgs, 2, tkArgs.length))));
		//TcltkC.Tcl_EvalFile(interp, tkArgs[1]);
		TcltkC.Tcl_Eval(interp, String.format("source %s", tkArgs[1]));
		TcltkC.Tcl_Eval(interp, "if {![winfo exists .frame1]} {lib_main}");
		TcltkC.Tk_MainLoop();
		//TcltkC.Tk_Main(tkArgs.length, tkArgs, Main.&tk_AppInit); // ???
    }
    
    private static void printUsage(String str, int status) {
        System.err.format("Usage: java %s [-h][-u name][-i ifc][extrafile [extraopts ..]]\n",
            Main.class.getName());
        System.err.println(str);
        System.exit(status);
    }

    private static String[] parse_cmdopts(Map<String, String> optsMap, 
            String[] args) {
        String option = null;
        java.util.List<String> extra = new java.util.LinkedList<String>();
        rootLogger.info("parse_cmdopts()");
        for (int i = 0, size = args.length; size > i; ++i) {
            option = args[i];
              
            if ('-' != option.charAt(0) || 1 == option.length()) {
                //printUsage("Not an option: " + option, 1);
                extra.add(option);
                continue;
            }
            switch (option.charAt(1)) {
                case 'h': printUsage("", 0);
                    break;
                case 'u': 
                    if ((size <= i + 1) || ('-' == args[i + 1].charAt(0)))
                        printUsage("Missing argument for " + option, 1);
                    optsMap.put("name", args[++i]);
                    break;
                case 'i': 
                    if ((size <= i + 1) || ('-' == args[i + 1].charAt(0)))
                        printUsage("Missing argument for " + option, 1);
                    optsMap.put("ifc", args[++i]);
                    break;
                default: //printUsage("Unknown option: " + option, 1);
                    extra.add(option);
            }
        }
        return extra.toArray(new String[0]);
    }

    /** Brief description.
     * @param args - array of command-line arguments */
    public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		Map<String, String> optsMap = new HashMap<String, String>() {
		    static final long serialVersionUID = 660L;
		    { put("name", "World"); put("ifc", "term"); }
		};
        String[] extrav = parse_cmdopts(optsMap, args);
	    
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
        
        /*switch (optsMap.get("ifc")) {
            case "gtk":
                run_demo_gtk(Main.class.getName(), rsrc_path,
                    optsMap.get("name"));
            default:
                run_demo(Main.class.getName(), rsrc_path,
                    optsMap.get("name"));
        }*/
        Map<String, IRunMethod> switcher = new HashMap<String, IRunMethod>() {
            //static final long serialVersionUID = 1060L;
            //{ put("term", run_demo); put("gtk", run_demo_gtk); }
            {put("term", new IRunMethod(){public void run_method(
					String progname, String rsrc_path, String name) {
				run_demo(progname, rsrc_path, name); }});
			put("gtk", new IRunMethod(){public void run_method(
					String progname, String rsrc_path, String name) {
				run_demo_gtk(progname, rsrc_path, name); }});
			put("curses", new IRunMethod(){public void run_method(
					String progname, String rsrc_path, String name) {
				run_demo_curses(progname, rsrc_path, name); }});
			put("tcltk", new IRunMethod(){public void run_method(
					String progname, String rsrc_path, String name) {
				run_demo_tcltk(progname, rsrc_path, name, extrav); }});
			}
        };
        IRunMethod func = switcher.getOrDefault(optsMap.get("ifc"),
            new IRunMethod(){public void run_method(String progname,
					String rsrc_path, String name) {
				run_demo(progname, rsrc_path, name); }});
        func.run_method(Main.class.getName(), rsrc_path, optsMap.get("name"));
        //run_demo(Main.class.getName(), rsrc_path, optsMap.get("name"));
	    
	    rootLogger.debug("exiting main()");
    }
}
