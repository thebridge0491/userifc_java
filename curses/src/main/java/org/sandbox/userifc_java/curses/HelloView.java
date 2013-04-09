package org.sandbox.userifc_java.curses;

import org.sandbox.wrapper_java.curses.CursesC;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_WINDOW;
import org.sandbox.wrapper_java.curses.SWIGTYPE_p_PANEL;

import java.beans.PropertyChangeEvent;

//import org.sandbox.intro_java.util.Library;
import org.sandbox.userifc_java.aux.Observer;

/** DocComment:
 * <p>Brief description.</p> */
public class HelloView extends Observer {

	static int diffCtrl = (int)'\0' - (int)'@';  // -64
	static int diffUprLwr = (int)'A' - (int)'a';  // -32
	static int attr_shift = 8;          // #define NCURSES_ATTR_SHIFT 8

	public enum Keys {    // usage: Keys.KEY_ENTER.value
		KEY_ENTER((int)'E' + diffCtrl),  // Ctrl+E -- Enter (0527)
		KEY_ESC((int)'X' + diffCtrl),    // Ctrl+X -- Exit  (0551)
		KEY_RUN((int)'R' + diffCtrl),    // Ctrl+R -- Run   (???)
		A_REVERSE(1 << 10+attr_shift),   // #define A_REVERSE NCURSES_BITS(1U,10)
		A_STANDOUT(1 << 8+attr_shift);    // #define A_STANDOUT NCURSES_BITS(1U,8)

		public final int value;

		Keys(int value) { this.value = value; }

		//int getValue() { return this.value; }
		//
		//String toString(){ value.toString(); }
		//
		//String getKey() { name(); }
	}
    
    /** Constructor.
     * @param screen - A Ncurses WINDOW */
    public HelloView(SWIGTYPE_p_WINDOW screen) {
        this.panels = new java.util.HashMap<String, SWIGTYPE_p_PANEL>();
        this.stdscr = null == screen ? CursesC.initscr() : screen;
        this.stdscr = this.stdscrSetup();
        this.setData("");
        
		//int orig_hgt, orig_wid;
		//CursesC.getmaxyx(stdscr, orig_hgt, orig_wid);
		int orig_hgt = CursesC.getmaxy(this.stdscr),
		    orig_wid = CursesC.getmaxx(this.stdscr);

		SWIGTYPE_p_PANEL panel_output = CursesC.new_panel(CursesC.newwin(
		    orig_hgt - 5, orig_wid - 2, 1, 1));
		SWIGTYPE_p_PANEL panel_input = CursesC.new_panel(CursesC.newwin(
		    3, (int)(orig_wid / 2), 7, 20));
		SWIGTYPE_p_PANEL panel_commands = CursesC.new_panel(CursesC.newwin(
		    4, orig_wid - 2, orig_hgt - 5, 1));

		CursesC.wattron(this.stdscr, Keys.A_REVERSE.value);
		CursesC.waddstr(this.stdscr, String.format("'%-32s'", 
		    "curseshello"));
		CursesC.wattroff(this.stdscr, Keys.A_REVERSE.value);

		CursesC.werase(CursesC.panel_window(panel_output));
		CursesC.werase(CursesC.panel_window(panel_input));
		CursesC.werase(CursesC.panel_window(panel_commands));
		CursesC.box(CursesC.panel_window(panel_output), (int)'|', (int)'-');
		CursesC.box(CursesC.panel_window(panel_input), (int)'|', (int)'-');
		CursesC.box(CursesC.panel_window(panel_commands), (int)'|', (int)'-');
		CursesC.wattron(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.mvwaddch(CursesC.panel_window(panel_commands), 1, 1,
		    Keys.KEY_RUN.value);
		CursesC.wattroff(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.wprintw(CursesC.panel_window(panel_commands),
		    String.format("'%-11s'", " Run"));

		CursesC.wattron(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.waddch(CursesC.panel_window(panel_commands), 
		    Keys.KEY_ENTER.value);
		CursesC.wattroff(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.wprintw(CursesC.panel_window(panel_commands),
		    String.format("'%-11s'", " Enter Name"));

		CursesC.wattron(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.mvwaddch(CursesC.panel_window(panel_commands), 2, 1,
		    Keys.KEY_ESC.value);
		CursesC.wattroff(CursesC.panel_window(panel_commands),
		    Keys.A_STANDOUT.value);
		CursesC.wprintw(CursesC.panel_window(panel_commands),
		    String.format("'%-11s'", " Exit"));

		this.panels.put("panel_output", panel_output);
		this.panels.put("panel_input", panel_input);
		this.panels.put("panel_commands", panel_commands);
		CursesC.wrefresh(this.stdscr);
    }
    public HelloView() { this(CursesC.initscr());
    }
    
    private SWIGTYPE_p_WINDOW stdscrSetup() {
        CursesC.noecho();
        CursesC.cbreak();
        CursesC.keypad(this.stdscr, true);
        return this.stdscr;
    }
    
    @Override
	public void finalize() {
		CursesC.nocbreak();
		CursesC.keypad(this.stdscr, false);
		CursesC.echo();
		CursesC.endwin();
	}
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setData((String)evt.getNewValue());
        int cur_y = CursesC.getcury(CursesC.panel_window(
            this.panels.get("panel_output")));
        CursesC.mvwprintw(CursesC.panel_window(
            this.panels.get("panel_output")), cur_y+1, 1, this.getData());
    }
    
    @Override
    //public void update(java.util.Observable src, Object arg) {
    public void update(Object src, Object arg) {
        CursesC.echo();
        int cur_y = CursesC.getcury(CursesC.panel_window(
            this.panels.get("panel_output")));
        /*this.setData((String)arg);
        CursesC.mvwprintw(CursesC.panel_window(
            this.panels.get("panel_output"]), cur_y+1, 1, this.getData());*/
        this.propertyChange(new PropertyChangeEvent(src, "data", 
            this.getData(), (String)arg));
        CursesC.noecho();
    }
    
    public java.util.HashMap<String, SWIGTYPE_p_PANEL> panels;
    public SWIGTYPE_p_WINDOW stdscr = null;
}
