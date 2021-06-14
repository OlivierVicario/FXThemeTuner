package sample;

import colorconverters.JzAzBz;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Thumb extends Circle {
    double[] jch;
    int indice;

    Thumb(double centerX, double centerY, double radius, double[] _jch, int indice) {
        super(centerX, centerY, radius);
        setStroke(Color.GREY);
        setStrokeWidth(2);
        setOpacity(1);
        jch = _jch;
        int[] rgb = JzAzBz.jchToRgb(jch);
        Color color = Color.rgb(rgb[0], rgb[1], rgb[2]);
        setFill(color);
        this.indice = indice;

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setStrokeWidth(4);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                setStrokeWidth(2);
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double distance = Math.sqrt((me.getX() - 175) * (me.getX() - 175) + (me.getY() - 175) * (me.getY() - 175));
                double t = 150.0 / distance;
                setCenterX(175 + t * (me.getX() - 175));
                setCenterY(175 + t * (me.getY() - 175));
                double[] pol = rec2polPivot(getCenterX(), getCenterY(), 175, 175);
                jch[2] = pol[1];
                int[] rgb = JzAzBz.jchToRgb(jch);
                setFill(Color.rgb(rgb[0], rgb[1], rgb[2]));
            }
        });
    }

    double[] rec2polPivot(double x, double y, double px, double py) {//px,py:point pivot à ramener à la base 0
        double x0 = x - px;
        double y0 = y - py;
        double theta = Math.atan2(y0, x0); //Quadrant by signs
        if (theta > 0) {
            theta = (theta / Math.PI) * 180;
        } else {
            theta = 360 - (Math.abs(theta) / Math.PI) * 180;
        }
        double d = Math.sqrt(Math.pow(x0, 2) + Math.pow(y0, 2));
        double[] pol = {d, theta};
        return pol;
    }

    double[] pol2recPivot(double d, double theta, double px, double py) {
        double x0 = Math.cos((theta * Math.PI / 180.0)) * d;
        double y0 = Math.sin((theta * Math.PI / 180.0)) * d;
        double x = x0 + px;
        double y = y0 + py;
        double[] rec = {x, y};
        return rec;
    }

    double[] rec2pol(double x, double y) {
        double theta = Math.atan2(y, x); //Quadrant by signs
        if (theta > 0) {
            theta = (theta / Math.PI) * 180;
        } else {
            theta = 360 - (Math.abs(theta) / Math.PI) * 180;
        }
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double[] pol = {d, theta};
        return pol;
    }

    double[] pol2rec(double d, double theta) {
        double x = Math.cos((theta * Math.PI / 180.0)) * d;
        double y = Math.sin((theta * Math.PI / 180.0)) * d;
        double[] rec = {x, y};
        return rec;
    }

    int[] colorToRgb(Color c) {
        int[] rgb = new int[3];
        rgb[0] = (int) Math.round(c.getRed() * 255);
        rgb[1] = (int) Math.round(c.getGreen() * 255);
        rgb[2] = (int) Math.round(c.getBlue() * 255);
        return rgb;
    }
}
