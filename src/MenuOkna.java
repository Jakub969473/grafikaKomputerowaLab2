import javax.swing.*;

public class MenuOkna extends JMenuBar{
    JMenu plik = new JMenu("Plik");
    JMenuItem wczytajLewy = new JMenuItem("Wczytaj lewy");
    JMenuItem wczytajPrawy = new JMenuItem("Wczytaj prawy");
    JMenuItem zapiszPlik = new JMenuItem("Zapisz plik");
    JMenuItem  zakoncz = new JMenuItem("Zakończ");
    JMenuItem lewWECzysc = new JMenuItem("Wyczyść lewe wejscie");
    JMenuItem praWeCzysc = new JMenuItem("Wyczyść prawe wejscie");
    JMenuItem wejscieCzysc = new JMenuItem("Wyczyść wyjscie");
    JMenu kopiowanie = new JMenu("kopiuj z");
    JMenuItem doWejscia1 = new JMenuItem("wyjscia do wejscia 1");
    JMenuItem doWejscia2 = new JMenuItem("wyjscia do wejscia 2");
    JMenu operacje = new JMenu("operacje arytmetyczne");
    JMenuItem dodawanie = new JMenuItem("dodawanie");
    JMenuItem odejmowanie = new JMenuItem("odejmowanie");
    JMenuItem mnożenie = new JMenuItem("mnożenie");
    JMenuItem dzielenie = new JMenuItem("dzielenie");



    public MenuOkna()
    {
        //menu Plik
        plik.add(wczytajLewy);
        plik.add(wczytajPrawy);
        plik.add(zapiszPlik);
        plik.add(lewWECzysc);
        plik.add(praWeCzysc);
        plik.add(wejscieCzysc);

        //linia oddzielająca JMenuItem
        plik.add(new JSeparator());
        plik.add(zakoncz);
        add(plik);

        //menu kopiowania
        kopiowanie.add(doWejscia1);
        kopiowanie.add(doWejscia2);
        add(kopiowanie);

        //menu operacyji
        operacje.add(dodawanie);
        operacje.add(odejmowanie);
        operacje.add(mnożenie);
        operacje.add(dzielenie);
        add(operacje);


    }
}
