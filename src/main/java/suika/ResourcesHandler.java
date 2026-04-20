package suika;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class ResourcesHandler {

    public static final Font customFont = loadFont("/Font/Blue Winter.otf", 32f);

    public static final Image mainmenu = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Mainmenugame.png"))
    ).getImage();

    public static final Image Blueberry = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Blueberry.png"))
    ).getImage();
    public static final int ORDER_BLUEBERRY = 1;

    public static final Image Raspberry = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Raspberry.png"))
    ).getImage();
    public static final int ORDER_RASPBERRY = 2;

    public static final Image Dewberry = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Dewberry.png"))
    ).getImage();
    public static final int ORDER_DEWBERRY = 3;

    public static final Image Persimmon = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Persimmon.png"))
    ).getImage();
    public static final int ORDER_PERSIMMON = 4;

    public static final Image Starfruit = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Starfruit.png"))
    ).getImage();
    public static final int ORDER_STARFRUIT = 5;

    public static final Image Dragon_Fruit = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/D_Fruit.png"))
    ).getImage();
    public static final int ORDER_DRAGON_FRUIT = 6;

    public static final Image Mango = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Mango.png"))
    ).getImage();
    public static final int ORDER_MANGO = 7;

    public static final Image Cherimoya = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Cherimoya.png"))
    ).getImage();
    public static final int ORDER_CHERIMOYA = 8;

    public static final Image Coconut = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Coconut.png"))
    ).getImage();
    public static final int ORDER_COCONUT = 9;

    public static final Image Melon = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Fruit/Melon.png"))
    ).getImage();
    public static final int ORDER_MELON = 10;

    public static final Image s = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Text/s.png"))
    ).getImage();

    public static final Image e = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Text/e.png"))
    ).getImage();

    public static final Image a = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Text/a.png"))
    ).getImage();

    public static final Image m = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Text/m.png"))
    ).getImage();

    public static final Image g = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/Text/g.png"))
    ).getImage();

    public static final Image Play_Area = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/box.png"))
    ).getImage();

    public static final Image Pointer_Leg = new ImageIcon(
            Objects.requireNonNull(ResourcesHandler.class.getResource("/leg.png"))
    ).getImage();

    public static Font loadFont(String path, float size){
        try(InputStream is = ResourcesHandler.class.getResourceAsStream(path)){
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch(Exception e){
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int)size);
        }
    }

    public static Image getFruitImage(int order){
        switch(order){
            case ORDER_BLUEBERRY:
                return Blueberry;
            case ORDER_RASPBERRY:
                return Raspberry;
            case ORDER_DEWBERRY:
                return Dewberry;
            case ORDER_PERSIMMON:
                return Persimmon;
            case ORDER_STARFRUIT:
                return Starfruit;
            case ORDER_DRAGON_FRUIT:
                return Dragon_Fruit;
            case ORDER_MANGO:
                return Mango;
            case ORDER_CHERIMOYA:
                return Cherimoya;
            case ORDER_COCONUT:
                return Coconut;
            case ORDER_MELON:
                return Melon;
            default:
                return Blueberry;
        }
    }

    public static int mainmenuWidth(){
        return 1400;
    }

    public static int mainmenuHeight(){
        return 1000;
    }
}