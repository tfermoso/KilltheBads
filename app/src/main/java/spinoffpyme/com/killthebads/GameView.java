package spinoffpyme.com.killthebads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 10/01/2018.
 */

public class GameView extends SurfaceView {
    private final Bitmap bmpBlood;
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    int x=0;
    private int xSpeed=1;
    private Sprite sprite;
    private List<Sprite> sprites=new ArrayList<Sprite>();
    private List<TempSprite> temps=new ArrayList<TempSprite>();
    private long lastClick;
    private MediaPlayer mp;

    public GameView(Context context) {
        super(context);
        gameLoopThread=new GameLoopThread(this);
        mp=MediaPlayer.create(context,R.raw.aplastar);
        holder=getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry=true;
                gameLoopThread.setRunning(false);
                while(retry){
                    try {
                        gameLoopThread.join();
                        retry=false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    bmpBlood=BitmapFactory.decodeResource(getResources(),R.drawable.blood1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Sprite sprite: sprites){
            sprite.onDraw(canvas);
        }

        for (int i = temps.size()-1; i >=0; i--) {
            temps.get(i).onDraw(canvas);
        }

        /*
        for (TempSprite tempSprite: temps){
            tempSprite.onDraw(canvas);
        }
        */
    }

    private void createSprites(){
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad2));
        sprites.add(createSprite(R.drawable.bad3));
        sprites.add(createSprite(R.drawable.bad4));
        sprites.add(createSprite(R.drawable.bad5));
        sprites.add(createSprite(R.drawable.bad6));
        sprites.add(createSprite(R.drawable.good1));
        sprites.add(createSprite(R.drawable.good2));
        sprites.add(createSprite(R.drawable.good3));
        sprites.add(createSprite(R.drawable.good4));
        sprites.add(createSprite(R.drawable.good5));
        sprites.add(createSprite(R.drawable.good6));
    }

    private Sprite createSprite(int resource){
        bmp= BitmapFactory.decodeResource(getResources(),resource);
        return new Sprite(this,bmp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(System.currentTimeMillis()-lastClick>500) {
            lastClick=System.currentTimeMillis();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    if (sprite.isCollition(event.getX(), event.getY())) {
                        temps.add(new TempSprite(event.getX(),event.getY(),bmpBlood,temps,this));
                        mp.start();
                        sprites.remove(sprite);
                        break;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
