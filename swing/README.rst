Userifc_java-Swing
===========================================
.. .rst to .html: rst2html5 foo.rst > foo.html
..                pandoc -s -f rst -t html5 -o foo.html foo.rst

Swing sub-package for Java User interface example project.

Installation
------------
source code tarball download:
    
        # [aria2c --check-certificate=false | wget --no-check-certificate | curl -kOL]
        
        FETCHCMD='aria2c --check-certificate=false'
        
        $FETCHCMD https://bitbucket.org/thebridge0491/userifc_java/[get | archive]/master.zip

version control repository clone:
        
        git clone https://bitbucket.org/thebridge0491/userifc_java.git

build example with sbt:
cd <path> ; sbt [-Djava.library.path=$PREFIX/lib] compile [test:run]

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
cd <path> ; ant [-Djava.library.path=$PREFIX/lib] compile [test]

ant publish

build example with maven:
cd <path> ; mvn [-Djava.library.path=$PREFIX/lib] compile [test]

mvn install

build example with gradle:
cd <path> ; gradle [-Djava.library.path=$PREFIX/lib] assemble [check]

gradle install

Usage
-----
        // PKG_CONFIG='pkg-config --with-path=$PREFIX/lib/pkgconfig'
        
        // $PKG_CONFIG --cflags --libs <ffi-lib>
        
        // java [-Djava.library.path=$PREFIX/lib] ...
        
        import org.sandbox.userifc_java.swing.Library;
        
        ...
        
        import org.sandbox.userifc_java.swing.*;
        
        ...
        
        HelloModel model1 = new HelloModel("greet.txt");
        
        HelloFormView view1 = new HelloFormView();
        
        model1.addObserver(view1);
        
        model1.notifyObservers("To be set -- HELP.");
        
        System.out.format("view1.getData(): %s\n", view1.getData());

Author/Copyright
----------------
Copyright (c) 2013 by thebridge0491 <thebridge0491-codelab@yahoo.com>

License
-------
Licensed under the Apache-2.0 License. See LICENSE for details.
