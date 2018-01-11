package spinoffpyme.com.killthebads;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by tomas on 10/01/2018.
 */

public class Sprite {
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

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width=bmp.getWidth()/BMP_COLUMNS;
        this.heigth=bmp.getHeight()/BMP_ROWS;
    }
    private void update(){
        if(x>gameView.getWidth()-width-xSpeed){
            xSpeed=-5;
        }
        if(x+xSpeed<0){
            xSpeed=5;
        }
        x=x+xSpeed;
        currentFrame=++currentFrame%BMP_COLUMNS;
    }
    public void onDraw(Canvas canvas){
        update();
        int srcX=currentFrame*width;
        int srcY=1*heigth;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+heigth);
        Rect dst=new Rect(x,y,x+width,y+heigth);
        canvas.drawBitmap(bmp,src,dst,null);
    }
}
