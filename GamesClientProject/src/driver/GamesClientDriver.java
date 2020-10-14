package driver;

import controller.Controller;
import controller.GamesController;
import model.GamesModel;
import model.Model;
import view.GamesView;
import view.View;

public class GamesClientDriver {
	public GamesClientDriver() {
		
	}
	
	public static void main(String[] args){
		Model model = new GamesModel();
		 View view = new GamesView();
		 Controller controller = new GamesController(model, view);
		 ((GamesModel)model).addPropertyChangeListener(controller);
		 ((GamesView)view).addPropertyChangeListener(controller);
		  view.start();
		}
}
