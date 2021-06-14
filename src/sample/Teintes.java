package sample;

import java.util.ArrayList;

public class Teintes {
    ArrayList<Teinte> liste;
    Theme theme;

    Teintes(Theme theme, double startHue, double endHue) {
        this.theme = theme;
        this.liste = new ArrayList<Teinte>();
        double relativeStartHue = 0;
        double relativeEndHue = endHue - startHue;
        if (relativeEndHue < 0) relativeEndHue += 360;
        for (int i = 0; i < theme.jchs.length; i++) {
            Teinte teinte = new Teinte();
            teinte.themeIndex = i;
            teinte.oldHue = theme.jchs[i][2];
            teinte.oldRelativeHue = teinte.oldHue - startHue;
            if (teinte.oldRelativeHue < 0) teinte.oldRelativeHue += 360;
            if (teinte.oldRelativeHue > relativeStartHue && teinte.oldRelativeHue < relativeEndHue)
                this.liste.add(teinte);
        }//maintenant on a les teintes intermédiaires dans la liste
        //on la trie sur oldrelativehue
        int n = this.liste.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (this.liste.get(j).oldRelativeHue > this.liste.get(j + 1).oldRelativeHue) {
                    Teinte temp = liste.get(j);
                    this.liste.set(j, liste.get(j + 1));
                    this.liste.set(j + 1, temp);
                }
            }
        }
        double step = (relativeEndHue - relativeStartHue) / (this.liste.size() + 1);
        System.out.println("step : " + step);
        //on crée les new hues
        for (int i = 0; i < n; i++) {
            this.liste.get(i).newRelativeHue = 0 + step * (i + 1);
            this.liste.get(i).newHue = (this.liste.get(i).newRelativeHue + startHue) % 360.0;
        }
    }
}

class Teinte {
    int themeIndex;
    double oldHue;
    double newHue;
    double oldRelativeHue;
    double newRelativeHue;
    int place;
}

