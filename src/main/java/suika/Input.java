package suika;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseMotionListener, MouseListener {

    private boolean leftClick;
    private int mouseX;
    private int mouseY;

    @Override
    public void mouseDragged(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1){
            leftClick = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

    public boolean isLeftClick(){
        return leftClick;
    }

    public void resetLeftClick(){
        leftClick = false;
    }

    public int getMouseX(){
        return mouseX;
    }
}