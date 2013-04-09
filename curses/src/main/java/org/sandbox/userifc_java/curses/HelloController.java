package org.sandbox.userifc_java.curses;

import org.sandbox.wrapper_java.curses.CursesC;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_WINDOW;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_PANEL;

//import org.sandbox.intro_java.util.Library;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloController {
    /** Constructor.
     * @param greetFile - greet filepath
     * @param rsrc_path - path of resources */
    public HelloController(String greetFile, String rsrc_path) {
        this.model = new HelloModel(greetFile, rsrc_path);
        SWIGTYPE_p_WINDOW win = CursesC.initscr();
        this.view1 = new HelloView(win);
	    
	    this.model.addObserver(this.view1);
	    //CursesC.wrefresh(this.view1.stdscr);
    }
    public HelloController() { this("greet.txt", "src/main/resources");
    }
    public HelloController(String greetFile) {
        this(greetFile, "src/main/resources");
    }
    
    /** Accessor for model.
     * @return model of the controller */
    public HelloModel getModel() { return this.model;
    }
    public HelloView getView1() { return this.view1;
    }
    
    /** Mutator for model. */
    public void setModel(HelloModel modelX) { this.model = modelX;
    }
    public void setView1(HelloView viewX) { this.view1 = viewX;
    }
    
    private boolean equalObjects(HelloController other) {
        return (this.model.equals(other.model) || this.model == other.model) &&
            (this.view1.equals(other.view1) || this.view1 == other.view1);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (null == obj || getClass() != obj.getClass())
            return false;
        return equalObjects((HelloController)obj);
    }
    
    @Override
    public int hashCode() {
        final int primeMul = 31;
        int primeAdd = 17;
        //long d_Bits = Double.doubleToLongBits(this.dblNum);
        return primeMul * (
            primeAdd + ((null != this.model) ? this.model.hashCode() : 0) +
            primeAdd + ((null != this.view1) ? this.view1.hashCode() : 0));
    }
    
    @Override
    public String toString() {
        return String.format("%s{}", this.getClass().getSimpleName());
    }
    
    public boolean step_virtualscr() {
		boolean isRunning = true;

		CursesC.werase(CursesC.panel_window(
		    this.view1.panels.get("panel_input")));
		CursesC.box(CursesC.panel_window(
		    this.view1.panels.get("panel_input")), (int)'|', (int)'-');
		CursesC.hide_panel(this.view1.panels.get("panel_input"));
		//int ch = CursesC.getch();
		int ch = CursesC.wgetch(CursesC.panel_window(
		    this.view1.panels.get("panel_commands")));

		if (HelloView.Keys.KEY_ENTER.value == ch)
		    this.on_key_enter();
		else if (HelloView.Keys.KEY_ESC.value == ch)
		    isRunning = false;
		else if (HelloView.Keys.KEY_RUN.value != ch)
		    this.on_key_unmapped(ch);

		CursesC.wrefresh(CursesC.panel_window(
		    this.view1.panels.get("panel_output")));
		CursesC.wrefresh(CursesC.panel_window(
		    this.view1.panels.get("panel_input")));
		CursesC.wrefresh(CursesC.panel_window(
		    this.view1.panels.get("panel_commands")));

		return isRunning;
	}
	
	public void run() {
        CursesC.noecho();
        CursesC.wrefresh(this.view1.stdscr);
        //CursesC.refresh();
        
        while (this.step_virtualscr()) {
            //CursesC.update_panels();
            CursesC.doupdate();
        }
    }
    
    public void on_key_unmapped(int ch) {
        CursesC.mvwprintw(CursesC.panel_window(
            this.view1.panels.get("panel_input")),
            1, 1, String.format("Error! Un-mapped key: %s. Retrying.",
            CursesC.unctrl(ch)));
        CursesC.wrefresh(CursesC.panel_window(
            this.view1.panels.get("panel_input")));
        CursesC.flash();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		}
    }
    
    public void on_key_enter() {
        CursesC.top_panel(this.view1.panels.get("panel_input"));
        CursesC.echo();
        String data = new String();
        //CursesC.mvwgetstr(CursesC.panel_window(
        //    this.view1.panels.get("panel_input")), 1, 1, data);
        for (int i = (int)0, ch = (int)'\0'; '\n' != ch; ++i) {
            data = data + ('\0' == ch ? "" : Character.toString((char)ch));
            ch = CursesC.mvwgetch(CursesC.panel_window(
                this.view1.panels.get("panel_input")), 1, i+1);
        }
        CursesC.mvwprintw(CursesC.panel_window(
            this.view1.panels.get("panel_input")), 1, 1, data);
        CursesC.wrefresh(CursesC.panel_window(
            this.view1.panels.get("panel_input")));
        int cur_y = CursesC.getcury(CursesC.panel_window(
            this.view1.panels.get("panel_output")));
        int max_y = CursesC.getmaxy(CursesC.panel_window(
            this.view1.panels.get("panel_output")));
        if ((max_y - 3) < cur_y) {
            CursesC.werase(CursesC.panel_window(
                this.view1.panels.get("panel_output")));
            CursesC.box(CursesC.panel_window(
                this.view1.panels.get("panel_output")), '|', '-');
        }
        cur_y = CursesC.getcury(CursesC.panel_window(
            this.view1.panels.get("panel_output")));
        this.model.notifyObservers(data);
        CursesC.noecho();
    }
    
    private HelloModel model;
    private HelloView view1;
}
