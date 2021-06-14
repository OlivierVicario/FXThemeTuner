package sample;

import colorconverters.JzAzBz;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public ArrayList<int[]> randomrgbs;
    Theme inputTheme;
    ArrayList<int[]> currentRgbs;
    Theme currentTheme;
    Thumb[] thumbs;

    public BorderPane BORDERPANE;
    public VBox vbCENTERPANE;
    public VBox vbLEFTPANE;
    public Pane paneCircles;
    public Button btnRdmTheme;
    public CheckBox cbC_V;
    public CheckBox cbV_C;
    public CheckBox cbV_H;
    public CheckBox cbC_H;
    public HBox hboxTheme;
    public HBox hboxCuTheme;
    public HBox hboxCL;
    public HBox hboxLC;
    public HBox hboxLH;
    public HBox hboxCH;


    //ajouter différents contrastes saturation et valeurs
    //ajouter delta pour chaque variations par rapport au thème initial
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        randomrgbs = new ArrayList<int[]>();
    }

    public void btnRdmThemeHandler(ActionEvent actionEvent) {
        randomrgbs.clear();
        for (int i = 0; i < 5; i++) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            int[] rgb = new int[]{r, g, b};
            randomrgbs.add(rgb);
        }
        inputTheme = new Theme(randomrgbs);
        afficheTheme(hboxTheme, inputTheme.rgbs);

        afficheThumbs(inputTheme);
    }

    private void afficheTheme(HBox hboxTheme, int[][] rgbs) {
        hboxTheme.getChildren().clear();
        for (int i = 0; i < 5; i++) {
            Rectangle r = new Rectangle(80, 40);
            r.setFill(Color.rgb(rgbs[i][0], rgbs[i][1], rgbs[i][2]));
            r.setStrokeWidth(10);
            r.setStroke(Color.TRANSPARENT);
            r.setArcHeight(8);
            r.setArcWidth(8);
            hboxTheme.getChildren().add(r);
        }
    }

    private void afficheThumbs(Theme _theme) {
        paneCircles.getChildren().clear();
        Circle cercle = new Circle(175, 175, 150);
        cercle.setFill(null);
        cercle.setStroke(Color.GREY);
        cercle.setStrokeWidth(1);
        paneCircles.getChildren().add(cercle);

        thumbs = new Thumb[_theme.rgbs.length];
        currentRgbs = new ArrayList<int[]>();

        for (int i = 0; i < _theme.rgbs.length; i++) {
            double[] jch = _theme.jchs[i];
            currentRgbs.add(_theme.rgbs[i]);

            double[] pivot = {175, 175};
            double[] centreThumb = pol2recPivot(150, _theme.jchs[i][2], pivot[0], pivot[1]);
            Thumb thumb = new Thumb(centreThumb[0], centreThumb[1], 10, jch, i);

            int finalI = i;
            thumb.fillProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    currentRgbs.set(finalI, JzAzBz.jchToRgb(jch));
                    miseAJourCurrentTheme();
                    //System.out.println("mise à jour current theme");
                }
            });
            thumb.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    if (me.isPrimaryButtonDown()) {
                        double startHue = jch[2];
                        System.out.println("startHue = " + startHue);
                    } else if (me.isSecondaryButtonDown()) {
                        double endtHue = jch[2];
                        System.out.println("endHue = " + endtHue);
                    }
                }
            });
            this.thumbs[i] = thumb;
            paneCircles.getChildren().add(thumb);
            //((Rectangle)hboxCuTheme.getChildren().get(i)).fillProperty().bind(thumb.fillProperty());
        }
        miseAJourCurrentTheme();
    }

    void miseAJourCurrentTheme() {
        currentTheme = new Theme(currentRgbs);
        afficheTheme(hboxCuTheme, currentTheme.rgbs);

        int[][] rgbsCL = currentTheme.generate_C_L();
        afficheTheme(hboxCL, rgbsCL);

        int[][] rgbsLC = currentTheme.generate_L_C();
        afficheTheme(hboxLC, rgbsLC);

        int[][] rgbsLH = currentTheme.generate_L_H();
        afficheTheme(hboxLH, rgbsLH);

        int[][] rgbsCH = currentTheme.generate_C_H();
        afficheTheme(hboxCH, rgbsCH);
    }

    double[] pol2recPivot(double d, double theta, double px, double py) {
        double x0 = Math.cos((theta * Math.PI / 180.0)) * d;
        double y0 = Math.sin((theta * Math.PI / 180.0)) * d;
        double x = x0 + px;
        double y = y0 + py;
        double[] rec = {x, y};
        return rec;
    }

}
