package com.sys.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sys.R;

/**
 * Created by LY on 2018/3/29.
 */

public class SwitchView extends View {

    private int bachColor = R.color.color_ee157cf8 ;
    private Context mContext ;
    private int mWdiht  , mHeight ;
    private Paint mPaint ;

    private RectF mRectF ;

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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mContext.getColor(R.color.color_ee157cf8));
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
        setMeasuredDimension(mWdiht ,mHeight );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mContext.getColor(R.color.color_ffffff));
        canvas.drawRoundRect( mRectF , getMeasuredHeight()/2  , getMeasuredHeight()/2 ,  mPaint);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mContext.getColor(R.color.color_66000000));
        canvas.drawCircle(getMeasuredHeight()/2 , getMeasuredHeight()/2 ,
                (getMeasuredHeight()-mPaint.getStrokeWidth()*4)/2 ,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
