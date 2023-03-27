import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelGraficzny extends JPanel {

    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    //konstruktor
    public PanelGraficzny(){
        super();
        setLayout(new GridLayout(2,1));
        ustawRozmiar(new Dimension(400,400));
        wyczysc();

    }
    //wczytanie obrazka z pliku
    public void wczytajPlikGraficzny(String sciezka){
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka);
        //próba odczytania pliku graficznego do bufora
        try {
            plotno = ImageIO.read(plikGraficzny);
            //odczytanie rozmiaru obrazka
            Dimension rozmiar = new Dimension(plotno.getWidth(), plotno.getHeight());
            //ustalenie rozmiaru panelu zgodnego z rozmiarem obrazka
            setPreferredSize(rozmiar);
            setMaximumSize(rozmiar);
            //ustalenie obramowania
            setBorder(BorderFactory.createLineBorder(Color.black));
            repaint();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezka);
            e.printStackTrace();
        }
    }

    public void zapiszPlikGraficzny(String sciezka){
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka);
        //próba zapisania pliku graficznego z bufora
        try {
            if(plotno != null)
            {
                if(!ImageIO.write(plotno, sciezka.substring(sciezka.lastIndexOf('.') + 1), new File(sciezka)))
                {
                    JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
        }
    }

    public void wyczysc(){
        //wyrysowanie białego tła
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        //ustalenie obramowania
        setBorder(BorderFactory.createLineBorder(Color.black));
        repaint();
    }

    public void ustawRozmiar(Dimension r){
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
        setMaximumSize(r);
    }

    public void kopiuj(BufferedImage wejscie){
        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                czerw = ci.getRed();
                ziel = ci.getGreen();
                nieb = ci.getBlue();

                //jak ustawić kolor piksala w obiekcie BufferedImage
                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }
    //przesłonięta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu
        g2d.drawImage(plotno, 0, 0, this);
    }
    //metoda zmieniająca janość obrazka
    public void jasność(BufferedImage wejscie,double jas) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(wejscie.getRGB(i,j));
                czerw = (int)(ci.getRed() + jas);
                ziel = (int)(ci.getGreen() + jas);
                nieb = (int)(ci.getBlue() + jas);

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie(czerw);
                ziel=obcięcie(ziel);
                nieb=obcięcie(nieb);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }
    private int obcięcie(int kolor) {

        if(kolor>255){
            return 255;
        }else if(kolor<0){
            return 0;
        }else{
            return kolor;
        }
    }

    public void zmianaZakresuJasności(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));

        Color ci;
        int czerw, ziel, nieb;

        int maxNieb=0, minNieb=255, maxCzerw=0, minCzerw=255, maxZiel=0, minZiel=255;

        //znalazienie max i min kolorów
        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =ci.getRed();
                ziel =ci.getGreen();
                nieb = ci.getBlue();

                //znalezienie maks wartosci każdego koloru
                maxNieb = czyMax(maxNieb,nieb);
                maxZiel = czyMax(maxZiel,ziel);
                maxCzerw = czyMax(maxCzerw,czerw);

                //znalezienie min wartości każdego koloru
                minNieb = czyMin(minNieb,nieb);
                minZiel = czyMin(minZiel,ziel);
                minCzerw = czyMin(minCzerw,czerw);

            }

        //przypisanie kolorów
        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =ci.getRed()-minCzerw;
                ziel =ci.getGreen()-minZiel;
                nieb = ci.getBlue()-minNieb;

                czerw = (255*czerw)/(maxCzerw-minCzerw);
                nieb = (255*nieb)/(maxNieb-minNieb);
                ziel = (255*ziel)/(maxZiel-minZiel);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());

            }

        repaint();

    }

    private int czyMin(int a, int b) {

        if(a>b){
            return b;
        }else{
            return a;
        }
    }

    private int czyMax(int a, int b) {

        if(a<b){
            return b;
        }else{
            return a;
        }
    }

    public void dodawanie(BufferedImage lewy, BufferedImage prawy,double alfa) {

        ustawRozmiar(new Dimension(lewy.getWidth(),lewy.getHeight()));
        Color ci,ci2;
        int czerw, ziel, nieb;

        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));
                czerw = (int)(alfa*ci.getRed() + (1-alfa)*ci2.getRed());
                ziel = (int)(alfa*ci.getGreen() + (1-alfa)*ci2.getGreen());
                nieb = (int)(alfa*ci.getBlue() + (1-alfa)*ci2.getBlue());

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie(czerw);
                ziel=obcięcie(ziel);
                nieb=obcięcie(nieb);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }

    public void odejmowanie(BufferedImage lewy, BufferedImage prawy) {

        ustawRozmiar(new Dimension(lewy.getWidth(),lewy.getHeight()));
        Color ci,ci2;
        int czerw, ziel, nieb;

        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));
                czerw = Math.abs(ci.getRed() - ci2.getRed());
                ziel = Math.abs(ci.getGreen() - ci2.getGreen());
                nieb = Math.abs(ci.getBlue() - ci2.getBlue());

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie(czerw);
                ziel=obcięcie(ziel);
                nieb=obcięcie(nieb);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }

    public void mnożenie(BufferedImage lewy, BufferedImage prawy) {

        ustawRozmiar(new Dimension(lewy.getWidth(),lewy.getHeight()));
        Color ci,ci2;
        double maxCzerw1=0, maxZiel1=0, maxNieb1=0,maxCzerw2=0,maxZiel2=0,maxNieb2=0, maxCzerw,
                maxZiel,maxNieb,minCzerw1=255, minZiel1=255, minNieb1=255,minCzerw2=255,
                minZiel2=255,minNieb2=255, minCzerw, minZiel,minNieb;
        double czerw,ziel,nieb,czerw2,ziel2,nieb2;



        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));

                maxCzerw1=czyMax((int)maxCzerw1,ci.getRed());
                maxZiel1=czyMax((int)maxZiel1,ci.getGreen());
                maxNieb1=czyMax((int)maxNieb1,ci.getBlue());
                maxCzerw2=czyMax((int)maxCzerw2,ci2.getRed());
                maxZiel2=czyMax((int)maxZiel2,ci2.getGreen());
                maxNieb2=czyMax((int)maxNieb2,ci2.getBlue());

                minCzerw1=czyMin((int)minCzerw1,ci.getRed());
                minZiel1=czyMin((int)minZiel1,ci.getGreen());
                minNieb1=czyMin((int)minNieb1,ci.getBlue());
                minCzerw2=czyMin((int)minCzerw2,ci.getRed());
                minZiel2=czyMin((int)minZiel2,ci.getGreen());
                minNieb2=czyMin((int)minNieb2,ci.getBlue());
            }

        maxCzerw=czyMax((int)maxCzerw1,(int)maxCzerw2);
        maxNieb=czyMax((int)maxNieb1,(int)maxNieb2);
        maxZiel=czyMax((int)maxZiel1,(int)maxZiel2);

        minCzerw=czyMin((int)minCzerw1,(int)minCzerw2);
        minNieb=czyMin((int)minNieb1,(int)minNieb2);
        minZiel=czyMin((int)minZiel1,(int)minZiel2);

        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));

                czerw=ci.getRed()-minCzerw;
                nieb=ci.getBlue()-minNieb;
                ziel=ci.getGreen()-minZiel;

                czerw2=ci2.getRed()-minCzerw;
                nieb2=ci2.getBlue()-minNieb;
                ziel2=ci2.getGreen()-minZiel;

                czerw/=(maxCzerw-minCzerw);
                nieb/=(maxNieb-minNieb);
                ziel/=(maxZiel-minZiel);

                czerw2/=(maxCzerw-minCzerw);
                nieb2/=(maxNieb-minNieb);
                ziel2/=(maxZiel-minZiel);

                czerw*=czerw2;
                nieb*=nieb2;
                ziel*=ziel2;

                czerw*=255;
                nieb*=255;
                ziel*=255;

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie((int)czerw);
                ziel=obcięcie((int)ziel);
                nieb=obcięcie((int)nieb);

                plotno.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        repaint();
    }

    public void dzielenie(BufferedImage lewy, BufferedImage prawy) {

        ustawRozmiar(new Dimension(lewy.getWidth(),lewy.getHeight()));
        Color ci,ci2;
        double maxCzerw1=0, maxZiel1=0, maxNieb1=0,maxCzerw2=0,maxZiel2=0,maxNieb2=0, maxCzerw,
                maxZiel,maxNieb,minCzerw1=255, minZiel1=255, minNieb1=255,minCzerw2=255,
                minZiel2=255,minNieb2=255, minCzerw, minZiel,minNieb;
        double czerw,ziel,nieb,czerw2,ziel2,nieb2;



        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));

                maxCzerw1=czyMax((int)maxCzerw1,ci.getRed());
                maxZiel1=czyMax((int)maxZiel1,ci.getGreen());
                maxNieb1=czyMax((int)maxNieb1,ci.getBlue());
                maxCzerw2=czyMax((int)maxCzerw2,ci2.getRed());
                maxZiel2=czyMax((int)maxZiel2,ci2.getGreen());
                maxNieb2=czyMax((int)maxNieb2,ci2.getBlue());

                minCzerw1=czyMin((int)minCzerw1,ci.getRed());
                minZiel1=czyMin((int)minZiel1,ci.getGreen());
                minNieb1=czyMin((int)minNieb1,ci.getBlue());
                minCzerw2=czyMin((int)minCzerw2,ci.getRed());
                minZiel2=czyMin((int)minZiel2,ci.getGreen());
                minNieb2=czyMin((int)minNieb2,ci.getBlue());
            }

        maxCzerw=czyMax((int)maxCzerw1,(int)maxCzerw2);
        maxNieb=czyMax((int)maxNieb1,(int)maxNieb2);
        maxZiel=czyMax((int)maxZiel1,(int)maxZiel2);

        minCzerw=czyMin((int)minCzerw1,(int)minCzerw2);
        minNieb=czyMin((int)minNieb1,(int)minNieb2);
        minZiel=czyMin((int)minZiel1,(int)minZiel2);

        for(int i=0; i<lewy.getWidth(); i++)
            for(int j=0; j<lewy.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(lewy.getRGB(i,j));
                ci2 = new Color(prawy.getRGB(i,j));

                /*czerw=ci.getRed()/255.0;
                nieb=ci.getBlue()/255.0;
                ziel=ci.getGreen()/255.0;

                System.out.println(czerw+ " "+nieb+" "+ziel);

                czerw2=ci2.getRed()/255.0;
                nieb2=ci2.getBlue()/255.0;
                ziel2=ci2.getGreen()/255.0;

                czerw/=czerw2;
                nieb/=nieb2;
                ziel/=ziel2;

                System.out.println(czerw+ " "+nieb+" "+ziel);

                czerw*=255.0;
                nieb*=255.0;
                ziel*=255.0;

                System.out.println(czerw+ " "+nieb+" "+ziel);*/

                czerw=ci.getRed()-minCzerw;
                nieb=ci.getBlue()-minNieb;
                ziel=ci.getGreen()-minZiel;

                czerw2=ci2.getRed()-minCzerw;
                nieb2=ci2.getBlue()-minNieb;
                ziel2=ci2.getGreen()-minZiel;

                czerw/=(maxCzerw-minCzerw);
                nieb/=(maxNieb-minNieb);
                ziel/=(maxZiel-minZiel);

                czerw2/=(maxCzerw-minCzerw);
                nieb2/=(maxNieb-minNieb);
                ziel2/=(maxZiel-minZiel);

                czerw=1-czerw;
                nieb=1-nieb;
                ziel=1-ziel;

                czerw/=czerw2;
                ziel/=ziel2;
                nieb/=nieb2;

                czerw=1-czerw;
                nieb=1-nieb;
                ziel=1-ziel;

                czerw*=maxCzerw;
                nieb*=maxNieb;
                ziel*=maxZiel;

                /*czerw=(double)ci.getRed()/ci2.getRed();
                czerw*=maxCzerw;
                ziel=(double)ci.getGreen()/ci2.getGreen();
                ziel*=maxZiel;
                nieb=(double)ci.getBlue()/ci2.getBlue();
                nieb*=maxNieb;*/

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć

                czerw=obcięcie((int)czerw);
                ziel=obcięcie((int)ziel);
                nieb=obcięcie((int)nieb);

                System.out.println(czerw+ " "+nieb+" "+ziel);

                plotno.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        repaint();
    }
}
