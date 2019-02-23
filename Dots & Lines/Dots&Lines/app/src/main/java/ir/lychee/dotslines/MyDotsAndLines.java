package ir.lychee.dotslines;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MyDotsAndLines extends View
{
//    static int DOTS_INDEX = 0;

    boolean boolTouchMe = true;

    boolean boolIsTouched = false;

    Paint paintDots;
    Paint paintLines;
    Paint paintText;

    int intColorDots = Color.rgb(68, 233, 27);
    int intColorLines = Color.rgb(35, 135, 107);

    int intCircleBound = 360;
    final float floatDotsRadius = 4f;

    float floatX, floatY;

    ArrayList<Dots> DotsArrayList;
    ArrayList<Dots> ArrayListForRND;

    final float floatDotsMoveDistance = 1.1f;
    final long longDotsMoveDuration = 20;
//    Handler handler;

    public MyDotsAndLines(Context context)
    {
        super(context);
        init();
    }

    public MyDotsAndLines(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public MyDotsAndLines(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        paintDots = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDots.setColor(intColorDots);

        paintLines = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLines.setColor(intColorLines);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int intFontSize = (int) (metrics.density * 40);

        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(intColorLines);
        paintText.setTextAlign(Paint.Align.LEFT);
        paintText.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paintText.setTextSize(intFontSize);

        DotsArrayList = new ArrayList<>();
//        handler = new Handler();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolTouchMe = false;
        boolIsTouched = true;

        floatX = event.getX();
        floatY = event.getY();
        if (DotsArrayList.size() > 0)
        {
            Random RND_Dots = new Random();
            int intRNDDotsIndex;

            Dots[] dotsArrayConnectedIndex = {null, null, null};

            ArrayListForRND = new ArrayList<>();

            for (int i = 0; i < DotsArrayList.size(); i++)
            {
                Dots dotsTemp = DotsArrayList.get(i);
                //noinspection unchecked,UseBulkOperation
                ArrayListForRND.add(dotsTemp);
//                Log.i("tag ForRND", String.valueOf(IndexArrayListForRND.get(i)));
            }
            for (int i = 0; i < dotsArrayConnectedIndex.length; i++)
            {
                if (ArrayListForRND.size() > 0)
                {
//                    Toast.makeText(getContext(), String.valueOf(IndexArrayListForRND), Toast.LENGTH_LONG).show();

                    intRNDDotsIndex = RND_Dots.nextInt(ArrayListForRND.size());
                    dotsArrayConnectedIndex[i] = ArrayListForRND.get(intRNDDotsIndex);
                    ArrayListForRND.remove(intRNDDotsIndex);
                }
//                else
//                {
//                    intArrayConnectedIndex[i] = 0;
//                }
            }

//            int intDotsIndexTemp = 0;
//            for (int i = 0; i <= DOTS_INDEX; i++)
//            {
//                Log.i("tagIndex", "the i is => " + String.valueOf(i) + "\tDOTS_INDEX=> " + String.valueOf(DOTS_INDEX));
//                if (DotsArrayList.size() > i)
//                {
//                    Dots dotsTemp = DotsArrayList.get(i);
//                    Log.i("tag0", "the i is => " + String.valueOf(i) + "\tDotsArrayList=> " + String.valueOf(DotsArrayList.size()));
//
//                    Log.i("tag1", "the i is => " + String.valueOf(i) + "\tdotsTemp.getIntIndex()=> " + String.valueOf(dotsTemp.getIntIndex()));
//                    if (dotsTemp.getIntIndex() != i)
//                    {
//                        intDotsIndexTemp = i;
//                        Log.i("tag2", "the i is => " + String.valueOf(i) + "\tintDotsIndexTemp=> " + String.valueOf(intDotsIndexTemp));
//                        break;
//                    }
//                }
//            }
            Dots dots = new Dots(floatX, floatY, dotsArrayConnectedIndex);
//              Log.i("tagIndex", "\tDOTS_INDEX=> " + String.valueOf(DOTS_INDEX));
            DotsArrayList.add(dots);
//            DOTS_INDEX++;
        } else
        {
            Dots dots = new Dots(floatX, floatY);
            DotsArrayList.add(dots);
//            DOTS_INDEX++;
        }

        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (boolTouchMe)
        {
            FunDrawTouchMe(canvas);
        }
        if (boolIsTouched)
        {

            FunDrawDots(canvas);

            FunDrawLines(canvas);
        }
        try
        {
            Thread.sleep(longDotsMoveDuration);
            invalidate();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void FunDrawTouchMe(Canvas canvas)
    {
        String strText = "Touch Me";
        Rect rect = new Rect();
        paintText.getTextBounds(strText, 0, strText.length(), rect);

        float floatX = (float) ((getWidth() - getPaddingLeft() - getPaddingRight()) / 2 - (rect.width() / 2));
        float floatY = (float) ((getHeight() - getPaddingTop() - getPaddingBottom()) / 2 + (rect.height() / 2));
        canvas.drawText(strText, floatX, floatY, paintText);
    }

    private void FunDrawDots(Canvas canvas)
    {
        if (DotsArrayList.size() == 0)
        {
            boolTouchMe = true;
        }

        Iterator iterator = DotsArrayList.iterator();

        boolean boolRemoveResult = false;

        while (iterator.hasNext())
        {
            if (boolRemoveResult)
            {
                iterator = DotsArrayList.iterator();
            }
            Dots dots = (Dots) iterator.next();
//            Toast.makeText(getContext(), String.valueOf(dots.getFloatX()), Toast.LENGTH_SHORT).show();
//            Log.i("DOT", String.valueOf(dots.getFloatX()));
            float fX = dots.getFloatX();
            float fY = dots.getFloatY();
            float fX2 = dots.getFloatX2();
            float fY2 = dots.getFloatY2();

//            if (DotsArrayList.size() > 2)
            canvas.drawCircle(fX, fY, floatDotsRadius, paintDots);

//            Log.i("Dot", "dots:\t" + dots.getIntIndex() + "\tX2\t" + String.valueOf(dots.getFloatX2()));
            dots.setFloatX(fX2);
            dots.setFloatY(fY2);
            dots.setFloatX2(fX2 + dots.getFloatXAngel() * floatDotsMoveDistance);
            dots.setFloatY2(fY2 + dots.getFloatYAngel() * floatDotsMoveDistance);

            // call a method for removing the Dots who got out the screen
            // age index ha ro az DotsArrayList hazf konim
            // moshkeli ke pish miyad ine ke vaghti darim intArrayConnectedIndex ro toye ontouch minvisim
            // darim az ye seri addade 0 ta DotIndex in karo mikonim, vali hala ye seri hazf mishan
            // pas bayad on ghesmat ro dobare benvisam

            //**************************************************************************************
            // this function remove the Dot object from the view if that dot dos'nt exist in screen

            boolRemoveResult = Fun_RemoveDot(dots);
        }


//        for (double i = 0; i < 380; i++)
//        {
//            floatX2 = (float) (floatX + Math.cos(i) * 350);
//            floatY2 = (float) (floatY + Math.sin(i) * 350);
//
//            Log.i("LI", String.valueOf(i));
//            Log.i("LX2", String.valueOf(floatX2 - floatY) + "    " + String.valueOf(Math.cos(i)));
//            Log.i("LY2", String.valueOf(floatY2 - floatY) + "    " + String.valueOf(Math.sin(i)));

//            canvas.drawCircle(floatX2, floatY2, floatDotsRadius, paintDots);
//            canvas.drawLine(floatX, floatY, floatX2, floatY2, paintLines);

//        }

    }

    private boolean Fun_RemoveDot(Dots dots)
    {
        boolean boolResult = false;
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float width = getWidth() - getPaddingLeft() - getPaddingRight();

        if (dots.getFloatX() < 0 || dots.getFloatX() > width || dots.getFloatY() < 0 || dots.getFloatY() > height)
        {
            DotsArrayList.remove(dots);
//            DOTS_INDEX--;
            boolResult = true;
        }
        return boolResult;
    }

    private void FunDrawLines(Canvas canvas)
    {
        if (DotsArrayList.size() > 0)
        {
            for (int i = 0; i < DotsArrayList.size(); i++)
            {
                Dots dotsTemp = DotsArrayList.get(i);
                Dots[] dotsArrayTemp = dotsTemp.getDotsArrayConnectedIndex();
                for (Dots anDotsArrayTemp : dotsArrayTemp)
                {
                    float startX = dotsTemp.getFloatX();
                    float startY = dotsTemp.getFloatY();
                    try
                    {
                        if (DotsArrayList.contains(anDotsArrayTemp))
                        {
                            float stopX = anDotsArrayTemp.getFloatX();
                            float stopY = anDotsArrayTemp.getFloatY();
                            canvas.drawLine(startX, startY, stopX, stopY, paintLines);
                        }
                    } catch (Exception e)
                    {
                        //do noting
                    }
                }
            }
        }
    }

    class Dots
    {
        float floatX;
        float floatY;
        float floatX2;
        float floatY2;
        float floatXAngel;
        float floatYAngel;
//        int intIndex;

        Dots[] dotsArrayConnectedIndex = {null, null, null};

        public Dots(float floatX, float floatY)
        {
            this.floatX = floatX;
            this.floatY = floatY;
//            this.intIndex = intIndex;

            Random rnd = new Random();
            int i = rnd.nextInt(intCircleBound);
            this.floatXAngel = (float) (Math.cos(i));
            this.floatYAngel = (float) (Math.sin(i));

            this.floatX2 = floatX + floatXAngel * floatDotsMoveDistance;
            this.floatY2 = floatY + floatYAngel * floatDotsMoveDistance;

//            Log.i("cos", String.valueOf(this.floatX2));
//            Log.i("sin", String.valueOf(this.floatY2));
//            handler.postAtTime(new MyRunnable(), longDotsMoveDuration);
        }

        public Dots(float floatX, float floatY, Dots[] dotsArrayConnectedIndex)
        {
            this.floatX = floatX;
            this.floatY = floatY;
//            this.intIndex = intIndex;
            this.dotsArrayConnectedIndex = dotsArrayConnectedIndex;

            Random rnd = new Random();
            int i = rnd.nextInt(intCircleBound);
            this.floatXAngel = (float) (Math.cos(i));
            this.floatYAngel = (float) (Math.sin(i));

            this.floatX2 = floatX + floatXAngel * floatDotsMoveDistance;
            this.floatY2 = floatY + floatYAngel * floatDotsMoveDistance;

//            Log.i("cos", String.valueOf(this.floatX2));
//            Log.i("sin", String.valueOf(this.floatY2));
//            handler.postAtTime(new MyRunnable(), longDotsMoveDuration);
        }

        public float getFloatX()
        {
            return floatX;
        }

        public void setFloatX(float floatX)
        {
            this.floatX = floatX;
        }

        public float getFloatY()
        {
            return floatY;
        }

        public void setFloatY(float floatY)
        {
            this.floatY = floatY;
        }

        public float getFloatX2()
        {
            return floatX2;
        }

        public void setFloatX2(float floatX2)
        {
            this.floatX2 = floatX2;
        }

        public float getFloatY2()
        {
            return floatY2;
        }

        public void setFloatY2(float floatY2)
        {
            this.floatY2 = floatY2;
        }

//        public int getIntIndex()
//        {
//            return intIndex;
//        }

//        public void setIntIndex(int intIndex)
//        {
//            this.intIndex = intIndex;
//        }

        public Dots[] getDotsArrayConnectedIndex()
        {
            return dotsArrayConnectedIndex;
        }

        public void setIntArrayConnectedIndex(Dots[] dotsArrayConnectedIndex)
        {
            this.dotsArrayConnectedIndex = dotsArrayConnectedIndex;
        }

        public float getFloatXAngel()
        {
            return floatXAngel;
        }

        public void setFloatXAngel(float floatXAngel)
        {
            this.floatXAngel = floatXAngel;
        }

        public float getFloatYAngel()
        {
            return floatYAngel;
        }

        public void setFloatYAngel(float floatYAngel)
        {
            this.floatYAngel = floatYAngel;
        }
    }

//    class MyRunnable implements Runnable
//    {
//        @Override
//        public void run()
//        {
//            invalidate();
//            handler.postAtTime(this, longDotsMoveDuration);
//        }
//    }
}
