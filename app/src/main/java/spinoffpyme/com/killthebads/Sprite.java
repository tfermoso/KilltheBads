package spinoffpyme.com.killthebads;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by tomas on 10/01/2018.
 */

public class Sprite {
    int[] DIRECCION_TO_ANIMATION_MAP={3,1,0,2};

    private static final int BMP_ROWS=4;
    private static final int BMP_COLUMNS=3;
    private int x=0;
    private int y=0;
    private int xSpeed=5;
    private  GameView gameView;
    private Bitmap bmp;
    private int currentFrame=0;
    private int width;
    private int heigth;
    private int ySpeed;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        Random rnd=new Random();
        this.width=bmp.getWidth()/BMP_COLUMNS;
        this.heigth=bmp.getHeight()/BMP_ROWS;
        xSpeed=rnd.nextInt(100)-5;
        ySpeed=rnd.nextInt(100)-5;


    }
    private void update(){
        if(x>gameView.getWidth()-width-xSpeed || x +xSpeed<0){
            xSpeed=-xSpeed;
        }
        x=x+xSpeed;
        if(y>gameView.getHeight()-heigth-ySpeed || y +ySpeed<0){
            ySpeed=-ySpeed;
        }
        y=y+ySpeed;
        currentFrame=++currentFrame%BMP_COLUMNS;
    }
    public void onDraw(Canvas canvas){
        update();
        int srcX=currentFrame*width;
        int srcY=getAnimationRow()*heigth;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+heigth);
        Rect dst=new Rect(x,y,x+width,y+heigth);
        canvas.drawBitmap(bmp,src,dst,null);
    }
    //dirección= 0 up, 1 left, 2 down, 3 rigth

    private int getAnimationRow() {
        double dirDouble=(Math.atan2(xSpeed,ySpeed)/(Math.PI/2)+2);
        int direccion= (int) Math.round(dirDouble)%BMP_ROWS;
        return DIRECCION_TO_ANIMATION_MAP[direccion];
    }
}
