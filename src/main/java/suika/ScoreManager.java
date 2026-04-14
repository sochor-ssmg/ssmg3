package suika;

import java.io.*;

public class ScoreManager {

    public static int loadHighScore(){
        try{
            File file = new File("highscore.txt");
            if(file.exists()){
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int score = Integer.parseInt(reader.readLine());
                reader.close();
                return score;
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static void saveHighScore(int score){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));
            writer.write(String.valueOf(score));
            writer.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}