package imu.pcloud.app.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
@SuppressWarnings("deprecation")
public class ClipZoomImageView extends ImageView implements
		OnScaleGestureListener, OnTouchListener,
		ViewTreeObserver.OnGlobalLayoutListener

{

	public static float SCALE_MAX = 4.0f;
	private static float SCALE_MID = 2.0f;

	/**
	 * ��ʼ��ʱ�����ű��������ͼƬ���ߴ�����Ļ����ֵ��С��0
	 */
	private float initScale = 1.0f;
	private boolean once = true;

	/**
	 * ���ڴ�ž����9��ֵ
	 */
	private final float[] matrixValues = new float[9];

	/**
	 * ���ŵ����Ƽ��
	 */
	private ScaleGestureDetector mScaleGestureDetector = null;
	private final Matrix mScaleMatrix = new Matrix();

	/**
	 * ����˫�����
	 */
	private GestureDetector mGestureDetector;
	private boolean isAutoScale;

	private int mTouchSlop;

	private float mLastX;
	private float mLastY;

	private boolean isCanDrag;
	private int lastPointerCount;

	public ClipZoomImageView(Context context)
	{
		this(context, null);
	}

	public ClipZoomImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		setScaleType(ScaleType.MATRIX);
		mGestureDetector = new GestureDetector(context,
				new SimpleOnGestureListener()
				{
					@Override
					public boolean onDoubleTap(MotionEvent e)
					{
						if (isAutoScale == true)
							return true;

						float x = e.getX();
						float y = e.getY();
						if (getScale() < SCALE_MID)
						{
							ClipZoomImageView.this.postDelayed(
									new AutoScaleRunnable(SCALE_MID, x, y), 16);
							isAutoScale = true;
						} else
						{
							ClipZoomImageView.this.postDelayed(
									new AutoScaleRunnable(initScale, x, y), 16);
							isAutoScale = true;
						}

						return true;
					}
				});
		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		this.setOnTouchListener(this);
	}

	/**
	 * �Զ����ŵ�����
	 * 
	 * @author zhy
	 * 
	 */
	private class AutoScaleRunnable implements Runnable
	{
		static final float BIGGER = 1.07f;
		static final float SMALLER = 0.93f;
		private float mTargetScale;
		private float tmpScale;

		/**
		 * ���ŵ�����
		 */
		private float x;
		private float y;

		/**
		 * ����Ŀ������ֵ������Ŀ��ֵ�뵱ǰֵ���ж�Ӧ�÷Ŵ�����С
		 * 
		 * @param targetScale
		 */
		public AutoScaleRunnable(float targetScale, float x, float y)
		{
			this.mTargetScale = targetScale;
			this.x = x;
			this.y = y;
			if (getScale() < mTargetScale)
			{
				tmpScale = BIGGER;
			} else
			{
				tmpScale = SMALLER;
			}

		}

		@Override
		public void run()
		{
			// ��������
			mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
			checkBorder();
			setImageMatrix(mScaleMatrix);

			final float currentScale = getScale();
			// ���ֵ�ںϷ���Χ�ڣ���������
			if (((tmpScale > 1f) && (currentScale < mTargetScale))
					|| ((tmpScale < 1f) && (mTargetScale < currentScale)))
			{
				ClipZoomImageView.this.postDelayed(this, 16);
			} else
			// ����ΪĿ������ű���
			{
				final float deltaScale = mTargetScale / currentScale;
				mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
				checkBorder();
				setImageMatrix(mScaleMatrix);
				isAutoScale = false;
			}

		}
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector)
	{
		float scale = getScale();
		float scaleFactor = detector.getScaleFactor();

		if (getDrawable() == null)
			return true;

		/**
		 * ���ŵķ�Χ����
		 */
		if ((scale < SCALE_MAX && scaleFactor > 1.0f)
				|| (scale > initScale && scaleFactor < 1.0f))
		{
			/**
			 * ���ֵ��Сֵ�ж�
			 */
			if (scaleFactor * scale < initScale)
			{
				scaleFactor = initScale / scale;
			}
			if (scaleFactor * scale > SCALE_MAX)
			{
				scaleFactor = SCALE_MAX / scale;
			}
			/**
			 * �������ű���
			 */
			mScaleMatrix.postScale(scaleFactor, scaleFactor,
					detector.getFocusX(), detector.getFocusY());
			checkBorder();
			setImageMatrix(mScaleMatrix);
		}
		return true;

	}

	/**
	 * ���ݵ�ǰͼƬ��Matrix���ͼƬ�ķ�Χ
	 * 
	 * @return
	 */
	private RectF getMatrixRectF()
	{
		Matrix matrix = mScaleMatrix;
		RectF rect = new RectF();
		Drawable d = getDrawable();
		if (null != d)
		{
			rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rect);
		}
		return rect;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector)
	{
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector)
	{
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{

		if (mGestureDetector.onTouchEvent(event))
			return true;
		mScaleGestureDetector.onTouchEvent(event);

		float x = 0, y = 0;
		// �õ�������ĸ���
		final int pointerCount = event.getPointerCount();
		// �õ�����������x��y��ֵ
		for (int i = 0; i < pointerCount; i++)
		{
			x += event.getX(i);
			y += event.getY(i);
		}
		x = x / pointerCount;
		y = y / pointerCount;

		/**
		 * ÿ�������㷢���仯ʱ������mLasX , mLastY
		 */
		if (pointerCount != lastPointerCount)
		{
			isCanDrag = false;
			mLastX = x;
			mLastY = y;
		}

		lastPointerCount = pointerCount;
		switch (event.getAction())
		{
		case MotionEvent.ACTION_MOVE:
			float dx = x - mLastX;
			float dy = y - mLastY;

			if (!isCanDrag)
			{
				isCanDrag = isCanDrag(dx, dy);
			}
			if (isCanDrag)
			{
				if (getDrawable() != null)
				{

					RectF rectF = getMatrixRectF();
					// ������С����Ļ��ȣ����ֹ�����ƶ�
					if (rectF.width() <= getWidth() - mHorizontalPadding * 2)
					{
						dx = 0;
					}
					// ����߶�С����Ļ�߶ȣ����ֹ�����ƶ�
					if (rectF.height() <= getHeight() - mVerticalPadding * 2)
					{
						dy = 0;
					}
					mScaleMatrix.postTranslate(dx, dy);
					checkBorder();
					setImageMatrix(mScaleMatrix);
				}
			}
			mLastX = x;
			mLastY = y;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			lastPointerCount = 0;
			break;
		}

		return true;
	}

	/**
	 * ��õ�ǰ�����ű���
	 * 
	 * @return
	 */
	public final float getScale()
	{
		mScaleMatrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_X];
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	/**
	 * ˮƽ������View�ı߾�
	 */
	private int mHorizontalPadding;//������Ļ��Ե���ͼ���Ŀ��
	/**
	 * ��ֱ������View�ı߾�
	 */
	private int mVerticalPadding;//������Ļ�������ͼ���ĸ߶�

	@Override
	public void onGlobalLayout()
	{
		if (once)
		{
			Drawable d = getDrawable();
			if (d == null)
				return;
			// ��ֱ����ı߾�
			mVerticalPadding = (getHeight() - (getWidth() - 2 * mHorizontalPadding)) / 2;

			int width = getWidth();
			int height = getHeight();
			// �õ�ͼƬ�Ŀ�͸�
			int dw = d.getIntrinsicWidth();
			int dh = d.getIntrinsicHeight();
			float scale = 1.0f;
			if (dw <= getWidth() - mHorizontalPadding * 2&& dh >=getHeight() - mVerticalPadding * 2)
			{//���ͼƬ�Ŀ��<��ͼ����ȣ�����ͼƬ�߶�>��ͼ���߶�ʱ
				scale = (getWidth() * 1.0f - mHorizontalPadding * 2) / dw;
			}

			if (dh <= getHeight() - mVerticalPadding * 2&& dw >= getWidth() - mHorizontalPadding * 2)
			{//���ͼƬ�ĸ߶�<��ͼ���߶ȣ�����ͼƬ���>��ͼ�����ʱ
				scale = (getHeight() * 1.0f - mVerticalPadding * 2) / dh;
			}

			if (dw <= getWidth() - mHorizontalPadding * 2&& dh <= getHeight() - mVerticalPadding * 2)
			{//���ͼƬ�ĸ߶�<��ͼ���߶ȣ�����ͼƬ���<��ͼ�����ʱ
				float scaleW = (getWidth() * 1.0f - mHorizontalPadding * 2)/ dw;
				float scaleH = (getHeight() * 1.0f - mVerticalPadding * 2) / dh;
				scale = Math.max(scaleW, scaleH);
			}
			initScale = scale;
			SCALE_MID = initScale * 2;
			SCALE_MAX = initScale * 4;
			mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);//ƽ������Ļ����
			mScaleMatrix.postScale(scale, scale, getWidth() / 2,getHeight() / 2);//�������ű���
			// ͼƬ�ƶ�����Ļ����
			setImageMatrix(mScaleMatrix);
			once = false;
		}

	}

	/**
	 * ����ͼƬ�����ؼ��к��bitmap����
	 * 
	 * @return
	 */
	public Bitmap clip()
	{
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		draw(canvas);
		//��Ϊ�ü�Ϊ����������ȥ��getCircleBitmap��������
		return getCircleBitmap(Bitmap.createBitmap(bitmap, mHorizontalPadding,
				mVerticalPadding, getWidth() - 2 * mHorizontalPadding,
				getWidth() - 2 * mHorizontalPadding));
	}

	/**
	 * �߽���
	 */
	private void checkBorder()
	{

		RectF rect = getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;

		int width = getWidth();
		int height = getHeight();
		// ������ߴ�����Ļ������Ʒ�Χ ; �����0.001����Ϊ���ȶ�ʧ��������⣬�������һ���С����������ֱ�Ӽ���һ��0.01
		if (rect.width() + 0.01 >= width - 2 * mHorizontalPadding)
		{
			if (rect.left > mHorizontalPadding)
			{
				deltaX = -rect.left + mHorizontalPadding;
			}
			if (rect.right < width - mHorizontalPadding)
			{
				deltaX = width - mHorizontalPadding - rect.right;
			}
		}
		if (rect.height() + 0.01 >= height - 2 * mVerticalPadding)
		{
			if (rect.top > mVerticalPadding)
			{
				deltaY = -rect.top + mVerticalPadding;
			}
			if (rect.bottom < height - mVerticalPadding)
			{
				deltaY = height - mVerticalPadding - rect.bottom;
			}
		}
		mScaleMatrix.postTranslate(deltaX, deltaY);

	}

	/**
	 * �Ƿ����϶���Ϊ
	 * 
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean isCanDrag(float dx, float dy)
	{
		return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
	}

	public void setHorizontalPadding(int mHorizontalPadding)
	{
		this.mHorizontalPadding = mHorizontalPadding;
	}
	
	/**
	 * ������bitmapת��ΪԲ��bitmap
	 * @param bitmap
	 * @return
	 */
	private Bitmap getCircleBitmap(Bitmap bitmap) {  
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output); 
        final int color = 0xff424242; 
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        Paint paint =new Paint();
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color); 
        int x = bitmap.getWidth(); 
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint); 
        return output;
	}
}
