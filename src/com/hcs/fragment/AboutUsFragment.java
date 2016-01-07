package com.hcs.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hcs.activities.R;

public class AboutUsFragment extends Fragment {
	WebView webView;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragmnet_about_us, container,
				false);

	
		  //Get webview 
        webView = (WebView) rootView.findViewById(R.id.webView1);
         
        startWebView("http://www.homecabs.com/About.aspx");
		// Inflate the layout for this fragment
		return rootView;
	}
	
	
	
	 private void startWebView(String url) {
         
	        //Create new webview Client to show progress dialog
	        //When opening a url or click on link
	         
	        webView.setWebViewClient(new WebViewClient() {      
	            ProgressDialog progressDialog;
	          
	            //If you will not use this method url links are opeen in new brower not in webview
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	                view.loadUrl(url);
	                return true;
	            }
	        
	            //Show loader on url load
	            public void onLoadResource (WebView view, String url) {
	                if (progressDialog == null) {
	                    // in standard case YourActivity.this
	                    progressDialog = new ProgressDialog(getActivity());
	                    progressDialog.setMessage("Loading...");
	                    progressDialog.show();
	                }
	            }
	            public void onPageFinished(WebView view, String url) {
	                try{
	                if (progressDialog.isShowing()) {
	                    progressDialog.dismiss();
	                    progressDialog = null;
	                }
	                }catch(Exception exception){
	                    exception.printStackTrace();
	                }
	            }
	             
	        }); 
	          
	         // Javascript inabled on webview  
	        webView.getSettings().setJavaScriptEnabled(true); 
	         
	        // Other webview options
	        /*
	        webView.getSettings().setLoadWithOverviewMode(true);
	        webView.getSettings().setUseWideViewPort(true);
	        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	        webView.setScrollbarFadingEnabled(false);
	        webView.getSettings().setBuiltInZoomControls(true);
	        */
	         
	        /*
	         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
	         webview.loadData(summary, "text/html", null); 
	         */
	         
	        //Load url in webview
	        webView.loadUrl(url);
	          
	          
	    }
	     
	    // Open previous opened link from history on webview when back button pressed
	     
	    
	    // Detect when the back button is pressed
	    public void onBackPressed() {
	        if(webView.canGoBack()) {
	            webView.goBack();
	        } else {
	            // Let the system handle the back button
	            super.getActivity().onBackPressed();
	        }
	    }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
}
