package com.sys.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sys.Interface.SwithchViewStatus;
import com.sys.R;

/**
 * Created by LY on 2018/3/29.
 */

public class SwitchView extends View {

    private Context mContext ;
    private int mWdiht  , mHeight ;
    private Paint mPaint ;

    private RectF mRectF ;
    private boolean actionMove ;
    private int tag = 0 ; // 1 开 0 关 2 中间
    private float mCX , mCY ; // 园心
    private float mR , mF ; // 园的半径
    private float minX ,maxX  , one_Third , two_Third;

    private SwithchViewStatus mSwithchViewStatus ;

    public void setmSwithchViewStatus(SwithchViewStatus mSwithchViewStatus) {
        this.mSwithchViewStatus = mSwithchViewStatus;
    }

    public SwitchView(Context context) {
        super(context);
        mContext = context ;
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context ;
        initPaint() ;
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context ;
        initPaint() ;
    }


    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(5.0f);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mContext.getColor(R.color.color_cccccc));
        this.setTag(tag);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widhtMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widhtMode == MeasureSpec.EXACTLY){
            mWdiht = widthSize ;
        }else{

        }
        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize ;
        }else{

        }
        mRectF = new RectF(mPaint.getStrokeWidth(),mPaint.getStrokeWidth() ,
                mWdiht - mPaint.getStrokeWidth() ,mHeight - mPaint.getStrokeWidth());
        minX = mCX = mHeight/2 + mPaint.getStrokeWidth();
        maxX = mWdiht - mHeight/2 - mPaint.getStrokeWidth() ;
        mF = mCY = mHeight/2 ;
        mR = (mHeight -mPaint.getStrokeWidth()*4)/2;
        one_Third = mWdiht/7 * 3;
        two_Third = one_Third/7 * 4 ;
        setMeasuredDimension(mWdiht ,mHeight );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mContext.getColor(R.color.color_ffffff));
        drawRoundRect(canvas);
        drawCircle(canvas);
    }

    /**
     * 换圆角矩形
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas){
        if((int)this.getTag() == 1){ // 开 1 ，关 0
            mPaint.setColor(mContext.getColor(R.color.color_ee157cf8));
        }else if((int)this.getTag() == 0){
            mPaint.setColor(mContext.getColor(R.color.color_cccccc));
        }else{
            mPaint.setColor(mContext.getColor(R.color.color_220000ff));
        }
        canvas.drawRoundRect(mRectF, mF, mF, mPaint);
    }

    /**
     * 画圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        mPaint.setColor(mContext.getColor(R.color.color_ffffff));
        if((int)this.getTag() == 1){
            canvas.drawCircle( maxX ,mCY , mR ,mPaint);
        }else if((int)this.getTag() == 0){
            canvas.drawCircle( minX ,mCY , mR ,mPaint);
        }else{
            canvas.drawCircle( mCX ,mCY , mR ,mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_MOVE :
                float mx = event.getX();
                if(mx<=minX){
                    mCX = minX;
                    actionMove = false ;
                    this.setTag(0);
                }else if(mx >= maxX){
                    mCX = maxX ;
                    actionMove = false ;
                    this.setTag(1);
                }else {
                    mCX = mx ;
                    if(mCX < one_Third){
                        this.setTag(0);
                    }else if(mCX>two_Third){
                        this.setTag(1);
                    }else {
                        this.setTag(2);
                    }
                    actionMove = true ;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                float ux = event.getX();
                if(ux<=minX){
                    mCX = minX;
                    this.setTag(0);
                }else if(ux >= maxX){
                    mCX = maxX ;
                    this.setTag(1);
                }else if(actionMove){
                    if(mCX>=getMeasuredWidth()/2){
                        mCX = maxX;
                        this.setTag(1);
                    }else{
                        mCX = minX ;
                        this.setTag(0);
                    }
                }else{
                    if(mCX == minX){
                        mCX = maxX ;
                        this.setTag(1);
                    }else if(mCX == maxX){
                        mCX = minX ;
                        this.setTag(0);
                    }
                }
                invalidate();
                if(mSwithchViewStatus != null ){
                    mSwithchViewStatus.swithchViewStatus(this, (Integer) this.getTag());
                }
                break;
        }
        return true ;
    }

    /**
     *
     * @param tag
     */
    public void setIsOpen(boolean tag){
        if(tag){
            mCX = getMeasuredWidth() - getMeasuredHeight()/2 - mPaint.getStrokeWidth() ;
            this.setTag(1);
        }else{
            mCX = getMeasuredHeight()/2 + mPaint.getStrokeWidth();
            this.setTag(0);
        }
        invalidate();
    }

    /**
     * 获取状态
     * @return
     */
    public boolean isOpen(){
        if((int)this.getTag() ==0 ){
            return true ;
        }else{
            return false ;
        }
    }
}
