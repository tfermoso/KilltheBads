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
    private int height;
    private int ySpeed;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        Random rnd=new Random();
        this.width=bmp.getWidth()/BMP_COLUMNS;
        this.height=bmp.getHeight()/BMP_ROWS;
        xSpeed=rnd.nextInt(10)-5;
        ySpeed=rnd.nextInt(10)-5;

        x=rnd.nextInt(gameView.getWidth()-width);
        y=rnd.nextInt(gameView.getHeight()-height);


    }
    private void update(){
        if(x>gameView.getWidth()-width-xSpeed || x +xSpeed<0){
            xSpeed=-xSpeed;
        }
        x=x+xSpeed;
        if(y>gameView.getHeight()-height-ySpeed || y +ySpeed<0){
            ySpeed=-ySpeed;
        }
        y=y+ySpeed;
        currentFrame=++currentFrame%BMP_COLUMNS;
    }
    public void onDraw(Canvas canvas){
        update();
        int srcX=currentFrame*width;
        int srcY=getAnimationRow()*height;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
        Rect dst=new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bmp,src,dst,null);
    }
    //dirección= 0 up, 1 left, 2 down, 3 rigth

    private int getAnimationRow() {
        double dirDouble=(Math.atan2(xSpeed,ySpeed)/(Math.PI/2)+2);
        int direccion= (int) Math.round(dirDouble)%BMP_ROWS;
        return DIRECCION_TO_ANIMATION_MAP[direccion];
    }

    public boolean isCollition(float x2, float y2) {
        return x2>x && x2<x+width && y2 > y && y2<y+height;
    }
}
