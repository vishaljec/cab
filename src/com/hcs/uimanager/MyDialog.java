package com.hcs.uimanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hcs.activities.R;

/**
 * @author amareshy
 *
 * @Description customized dialog
 */
public class MyDialog extends Dialog {

	public MyDialog(Context context, myOnClickListener myclick) {
		super(context);
		this.myListener = myclick;
	}

	public myOnClickListener myListener;

	/**
	 * @author amareshy
	 *
	 * @Description set listener for call backing on click
	 */
	public interface myOnClickListener {
		void onButtonClick();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.alert);

			Button btn = (Button) findViewById(R.id.btnAction);
			btn.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					myListener.onButtonClick();
				}
			});

			Button cancel = ((Button) findViewById(R.id.btnCancel));
			cancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cancel();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}