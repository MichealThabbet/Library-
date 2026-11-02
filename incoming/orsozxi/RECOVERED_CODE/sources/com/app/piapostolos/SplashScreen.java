package com.app.piapostolos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.GoogleApiAvailability;
import it.orsozoxi.android.orsonotes.R2;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/* loaded from: classes.dex */
public class SplashScreen extends Activity {
    private ImageView fifthNavImageView;
    private ImageView firstNavImageView;
    private ImageView fourthNavImageView;
    private ImageView secondNavImageView;
    private ImageView thirdNavImageView;
    Boolean isInternetPresent = false;
    private boolean showbuzz = false;
    private boolean firstTime = false;
    protected int splashTime = R2.id.standard;
    private boolean isFinish = false;
    private int counter = 0;
    private int waited = 0;

    static /* synthetic */ int access$012(SplashScreen splashScreen, int i) {
        int i2 = splashScreen.waited + i;
        splashScreen.waited = i2;
        return i2;
    }

    static /* synthetic */ int access$108(SplashScreen splashScreen) {
        int i = splashScreen.counter;
        splashScreen.counter = i + 1;
        return i;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(128);
        if (!PiApostolos.isPlay_Store() && isGooglePlayServicesAvailable(this)) {
            PiApostolos.activateFireBase(this);
        }
        katamarsReaders katamarsreaders = new katamarsReaders(this, false);
        Calendar calendar = Calendar.getInstance();
        if (katamarsreaders.splashEid.equals("kyama")) {
            findViewById(R.id.linear_splash).setBackgroundResource(R.drawable.kyama_eid);
        } else if (katamarsreaders.splashEid.equals("alam")) {
            findViewById(R.id.linear_splash).setBackgroundResource(R.drawable.alam_eid);
        } else if (katamarsreaders.splashEid.equals("a7d_shaaneen") && calendar.get(11) >= 13) {
            findViewById(R.id.linear_splash).setBackgroundResource(R.drawable.alam_eid);
        } else if (katamarsreaders.splashEid.equals("gotas")) {
            findViewById(R.id.linear_splash).setBackgroundResource(R.drawable.splash_gotas);
        } else if (katamarsreaders.splashEid.equals("melad")) {
            findViewById(R.id.linear_splash).setBackgroundResource(R.drawable.splash_melad);
        }
        initNavBuzz();
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (iIsGooglePlayServicesAvailable == 0) {
            return true;
        }
        googleApiAvailability.isUserResolvableError(iIsGooglePlayServicesAvailable);
        return false;
    }

    public void getHashCode() throws NoSuchAlgorithmException {
        try {
            Log.d("KeyHash:", getPackageName());
            for (Signature signature : getPackageManager().getPackageInfo(getPackageName(), 64).signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(messageDigest.digest(), 0));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
    }

    public void initNavBuzz() {
        this.firstNavImageView = (ImageView) findViewById(R.id.iv_nav1);
        this.secondNavImageView = (ImageView) findViewById(R.id.iv_nav2);
        this.thirdNavImageView = (ImageView) findViewById(R.id.iv_nav3);
        this.fourthNavImageView = (ImageView) findViewById(R.id.iv_nav4);
        this.fifthNavImageView = (ImageView) findViewById(R.id.iv_nav5);
    }

    @Override // android.app.Activity
    protected void onResume() {
        new Thread() { // from class: com.app.piapostolos.SplashScreen.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (SplashScreen.this.waited <= SplashScreen.this.splashTime) {
                    try {
                        SplashScreen.this.runOnUiThread(new Runnable() { // from class: com.app.piapostolos.SplashScreen.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (SplashScreen.this.waited != 0) {
                                    int i = SplashScreen.this.counter;
                                    if (i == 0) {
                                        SplashScreen.this.firstNavImageView.setImageResource(R.drawable.m_splash_nav_blue);
                                    } else if (i == 1) {
                                        SplashScreen.this.secondNavImageView.setImageResource(R.drawable.m_splash_nav_blue);
                                    } else if (i == 2) {
                                        SplashScreen.this.thirdNavImageView.setImageResource(R.drawable.m_splash_nav_blue);
                                    } else if (i == 3) {
                                        SplashScreen.this.fourthNavImageView.setImageResource(R.drawable.m_splash_nav_blue);
                                    } else if (i == 4) {
                                        SplashScreen.this.fifthNavImageView.setImageResource(R.drawable.m_splash_nav_blue);
                                    }
                                    SplashScreen.access$108(SplashScreen.this);
                                }
                            }
                        });
                        if (SplashScreen.this.counter < 4) {
                            sleep(700L);
                        }
                        SplashScreen.access$012(SplashScreen.this, R2.attr.layout_constraintBottom_creator);
                    } catch (InterruptedException unused) {
                        if (SplashScreen.this.isFinish) {
                            return;
                        }
                    } catch (Throwable th) {
                        if (!SplashScreen.this.isFinish) {
                            SplashScreen.this.jump();
                        }
                        throw th;
                    }
                }
                if (SplashScreen.this.isFinish) {
                    return;
                }
                SplashScreen.this.jump();
            }
        }.start();
        super.onResume();
    }

    public void jump() {
        startActivity(new Intent(this, (Class<?>) MainActivity.class));
        finish();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        this.isFinish = true;
        finish();
    }
}
