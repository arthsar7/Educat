
package ru.student.detected.educator.ui.activities;

import static androidx.core.splashscreen.SplashScreen.installSplashScreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.student.detected.page1.R;

public class MainActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		installSplashScreen(this);
		setContentView(R.layout.main);
	}
}
	
	