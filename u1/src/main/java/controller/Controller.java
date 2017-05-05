/**
 * Created by Sefa on 05.05.2017.
 */
package controller;

import model.Model;
import view.View;

public class Controller{

    public Model model;
    public View view;

    public Controller(){

    }
    public void link(Model model, View view) {
        this.model = model;
        this.view = view;
    }
}
