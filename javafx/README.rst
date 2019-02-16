Userifc_java-Javafx
===========================================
.. .rst to .html: rst2html5 foo.rst > foo.html
..                pandoc -s -f rst -t html5 -o foo.html foo.rst

JavaFX sub-package for Java User interface example project.

Installation
------------
source code tarball download:
    
        # [aria2c --check-certificate=false | wget --no-check-certificate | curl -kOL]
        
        FETCHCMD='aria2c --check-certificate=false'
        
        $FETCHCMD https://bitbucket.org/thebridge0491/userifc_java/[get | archive]/master.zip

version control repository clone:
        
        git clone https://bitbucket.org/thebridge0491/userifc_java.git

MOD_OPTS="--module-path=$JAVAFX_HOME/lib --add-modules=javafx.controls,javafx.fxml"

build example with sbt:
cd <path> ; JAVA_OPTS="$JAVA_OPTS $MOD_OPTS" sbt [-Djava.library.path=$PREFIX/lib] compile [test:run]

sbt publishLocal

build example with rake:
cd <path> ; [sh] ./configure.sh [--prefix=$PREFIX] [--help]

rake main [check]

rake publish

build example with make:
cd <path> ; [sh] ./configure.sh [--prefix=$PREFIX] [--help]

make all [check]

make publish

build example with ant:
cd <path> ; ant [-Djava.library.path=$PREFIX/lib] -Djava.args="$MOD_OPTS" compile [test]

ant publish

build example with maven:
cd <path> ; mvn [-Djava.library.path=$PREFIX/lib] -DargLine="$MOD_OPTS" compile [test]

mvn install

build example with gradle:
cd <path> ; JAVA_OPTS="$JAVA_OPTS $MOD_OPTS" gradle [-Djava.library.path=$PREFIX/lib] assemble [check]

gradle install

Usage
-----
        // PKG_CONFIG='pkg-config --with-path=$PREFIX/lib/pkgconfig'
        
        // $PKG_CONFIG --cflags --libs <ffi-lib>
        
        // java [-Djava.library.path=$PREFIX/lib] ...
        
        import org.sandbox.userifc_java.javafx.Library;
        
        ...
        
		import javafx.scene.Scene;
		
		import javafx.application.Application;
		
		import javafx.stage.Stage;
		
        import org.sandbox.userifc_java.javafx.*;
        
        ...
        
        public static class JfxApp extends Application {
			
			@Override
			
			public void start(Stage stagePrime) {
			
				Application.Parameters params = this.getParameters();
			
				String param0 = params.getRaw().size() > 0 ?
			
					params.getRaw().get(0) : "";
			
				HelloModel model = new HelloModel("greet.txt");
			
				HelloFormView view1 = new HelloFormView();
			
				model.addObserver(view1);
			
				model.notifyObservers(params.getNamed().get("data"));
			
				System.out.format("view1.getData(): %s\n", view1.getData());
			
				stagePrime.setScene(new Scene(view1.parent, 200, 160));
			
				stagePrime.show();
			
			}
		
		}
        
        String[] params = {"--data=" + "To be set -- HELP."};
        
        //JfxApp.launch(JfxApp.class, params);
        
        Application.launch(JfxApp.class, params);

Author/Copyright
----------------
Copyright (c) 2013 by thebridge0491 <thebridge0491-codelab@yahoo.com>

License
-------
Licensed under the Apache-2.0 License. See LICENSE for details.
