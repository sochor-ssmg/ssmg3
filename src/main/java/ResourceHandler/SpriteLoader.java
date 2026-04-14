package ResourceHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class SpriteLoader {


    public static Image load(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(SpriteLoader.class.getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException("Nelze načíst obrázek: " + path, e);
        }
    }


    public static Image getFrame(String path, int frameWidth, int frameHeight, int frameCount, int index) {
        try {
            BufferedImage sheet = ImageIO.read(
                    Objects.requireNonNull(SpriteLoader.class.getResource(path))
            );

            if (index < 0 || index >= frameCount)
                throw new IllegalArgumentException("Index snímku mimo rozsah: " + index);

            int x = index * frameWidth;
            int y = 0;

            return sheet.getSubimage(x, y, frameWidth, frameHeight);

        } catch (IOException e) {
            throw new RuntimeException("Nelze načíst spritesheet: " + path, e);
        }
    }
}
