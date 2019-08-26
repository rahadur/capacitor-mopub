package app.xplatform.capacitor;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;

import java.util.Set;

import app.xplatform.capacitor.capacitormopub.R;


@NativePlugin()
public class MoPub extends Plugin {

    private MoPubView mMoPubView;

    private MoPubInterstitial mInterstitial;

    private MoPubReward mRewardedVideo;

    private String rewardAdId;

    private boolean isBannerPrepare = false;

    private boolean isInterstitialLoaded = false;

    private boolean isRewardVideoLoaded = false;


    @PluginMethod()
    public void prepareBanner(final PluginCall call) {

        final String  adId   = call.getString("adId", "");
        String  adSize       = call.getString("adSize", "MATCH_VIEW");
        int     width        = call.getInt("width", 0);
        int     height       = call.getInt("height", 0);

        final boolean hasTabBar    = call.getBoolean("hasTabBar", false);
        final int     tabBarHeight = call.getInt("tabBarHeight", 56);
        final boolean autoShow = call.getBoolean("autoShow", false);

        boolean isTesting    = call.getBoolean("isTesting", false);
        boolean autoRefresh = call.getBoolean("autoRefresh", true);



        try {
            mMoPubView = (MoPubView)getBridge().getActivity().findViewById(R.id.moPub);

            // Check AD Unit Is null or not
            if (adId == null && adId.isEmpty()) {
                call.error("AD Unit Id can't be empty");
                return;
            } else {
                mMoPubView.setAdUnitId(adId);
            }

            // Set Banner Size
            switch (adSize) {
                case "HEIGHT_50":
                    mMoPubView.setAdSize(MoPubView.MoPubAdSize.HEIGHT_50);
                    break;
                case "HEIGHT_90":
                    mMoPubView.setAdSize(MoPubView.MoPubAdSize.HEIGHT_90);
                    break;
                case "HEIGHT_250":
                    mMoPubView.setAdSize(MoPubView.MoPubAdSize.HEIGHT_250);
                    break;

                case "HEIGHT_280":
                    mMoPubView.setAdSize(MoPubView.MoPubAdSize.HEIGHT_280);
                    break;

                default:
                    mMoPubView.setAdSize(MoPubView.MoPubAdSize.MATCH_VIEW);
                    break;
            }




            // Set Auto Refresh
            mMoPubView.setAutorefreshEnabled(autoRefresh);


            // Banner Ad Listener
            mMoPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
                @Override
                public void onBannerLoaded(MoPubView banner) {
                    notifyListeners("onAdLoaded", new JSObject().put("value", true));
                }

                @Override
                public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                    notifyListeners("onBannerFailed", new JSObject().put("errorCode", errorCode));
                }

                @Override
                public void onBannerClicked(MoPubView banner) {
                    notifyListeners("onBannerClicked", new JSObject().put("value", true));
                }

                @Override
                public void onBannerExpanded(MoPubView banner) {
                    notifyListeners("onBannerExpanded", new JSObject().put("value", true));
                }

                @Override
                public void onBannerCollapsed(MoPubView banner) {
                    notifyListeners("onBannerCollapsed", new JSObject().put("value", true));
                }
            });


            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Set Bottom margin for TabBar
                    CoordinatorLayout.LayoutParams mAdViewLayoutParams = new CoordinatorLayout.LayoutParams(
                            CoordinatorLayout.LayoutParams.MATCH_PARENT,
                            CoordinatorLayout.LayoutParams.WRAP_CONTENT
                    );
                    mAdViewLayoutParams.gravity = Gravity.BOTTOM;
                    if (hasTabBar) {
                        float density = getContext().getResources().getDisplayMetrics().density;
                        int margin = (int) (tabBarHeight * density);
                        mAdViewLayoutParams.setMargins(0, 0, 0, margin);
                        mMoPubView.setLayoutParams(mAdViewLayoutParams);
                    } else  {
                        mAdViewLayoutParams.setMargins(0, 0, 0, 0);
                        mMoPubView.setLayoutParams(mAdViewLayoutParams);
                    }

                    final SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(adId)
                            .withLogLevel(MoPubLog.LogLevel.DEBUG)
                            .build();

                    com.mopub.common.MoPub.initializeSdk(getBridge().getActivity(), sdkConfiguration, new SdkInitializationListener() {
                        @Override
                        public void onInitializationFinished() {
                            notifyListeners("onBannerPrepared", new JSObject().put("value", true));

                            if (autoShow) {
                                mMoPubView.loadAd();
                                call.success(new JSObject().put("value", true));
                            } else {
                                isBannerPrepare = true;
                                call.success(new JSObject().put("value", true));
                            }
                        }
                    });
                }
            });



        } catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }

    }


    @PluginMethod()
    public void showBanner(PluginCall call) {
        try {
            if (isBannerPrepare) {
                mMoPubView.loadAd();
                call.success(new JSObject().put("value", true));
            } else {
                call.error("Banner is not Prepare to show");
            }
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    @PluginMethod()
    public void hideBanner(final PluginCall call) {
        try {
            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMoPubView.setVisibility(View.INVISIBLE);
                    call.success(new JSObject().put("value", true));
                }
            });
        } catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    @PluginMethod()
    public void resumeBanner(final PluginCall call) {
        try {
            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMoPubView.setVisibility(View.VISIBLE);
                    call.success(new JSObject().put("value", true));
                }
            });
        } catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    @PluginMethod()
    public void removeBanner(final PluginCall call) {
        try {
            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMoPubView.destroy();
                    call.success(new JSObject().put("value", true));
                }
            });
        } catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }

    }




    @PluginMethod()
    public void prepareInterstitial(final PluginCall call) {

        final String  adId   = call.getString("adId", "");
        final boolean autoShow = call.getBoolean("autoShow", false);
        boolean       isTesting    = call.getBoolean("isTesting", false);

        try {
            mInterstitial =  new MoPubInterstitial(getBridge().getActivity(), adId);

            mInterstitial.setTesting(isTesting);

            mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
                @Override
                public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                    notifyListeners("onInterstitialLoaded", new JSObject().put("value", true));
                    if (autoShow) {
                        if (mInterstitial.isReady()) {
                            mInterstitial.show();
                            call.success(new JSObject().put("value", true));
                        } else {
                            call.error("Interstitial is not Ready to show");
                        }
                    } else {
                        isInterstitialLoaded = true;
                        call.success(new JSObject().put("value", true));
                    }
                }

                @Override
                public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                    notifyListeners("onInterstitialFailed", new JSObject().put("value", errorCode));
                }

                @Override
                public void onInterstitialShown(MoPubInterstitial interstitial) {
                    notifyListeners("onInterstitialShown", new JSObject().put("value", true));
                }

                @Override
                public void onInterstitialClicked(MoPubInterstitial interstitial) {
                    notifyListeners("onInterstitialClicked", new JSObject().put("value", true));
                }

                @Override
                public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                    notifyListeners("onInterstitialDismissed", new JSObject().put("value", true));
                }
            });


            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(adId)
                                                                .withLogLevel(MoPubLog.LogLevel.DEBUG)
                                                                .build();

                    com.mopub.common.MoPub.initializeSdk(getBridge().getActivity(), sdkConfiguration, new SdkInitializationListener() {
                        @Override
                        public void onInitializationFinished() {
                            mInterstitial.load();
                        }
                    });
                }
            });

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }


    }


    @PluginMethod()
    public void showInterstitial(PluginCall call) {
        try {
            if (isInterstitialLoaded && mInterstitial.isReady()) {
                mInterstitial.show();
                call.success(new JSObject().put("value", true));
            } else {
                call.error("Interstitial is not Prepare to show");
            }
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }




    @PluginMethod()
    public void prepareRewardVideo(final PluginCall call) {
        final String  adId       = call.getString("adId", "");
        final boolean autoShow   = call.getBoolean("autoShow", false);
        boolean       isTesting  = call.getBoolean("isTesting", false);
        String        userId     = call.getString("userId", "");

        rewardAdId = adId;


        try{
            // Check AD Unit Is null or not
            if (adId == null && adId.isEmpty()) {
                call.error("AD Unit Id can't be empty");
                return;
            } else {
                MoPubRewardedVideos.loadRewardedVideo(adId);
            }

            MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
                @Override
                public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
                    notifyListeners("onRewardedVideoLoadSuccess", new JSObject().put("value", true));
                    if (autoShow) {
                        if (MoPubRewardedVideos.hasRewardedVideo(adId)) {
                            MoPubRewardedVideos.showRewardedVideo(adId);
                            call.success(new JSObject().put("value", true));
                        } else {
                            call.error("RewardedVideos is not Ready to show");
                        }
                    } else {
                        isRewardVideoLoaded = true;
                        call.success(new JSObject().put("value", true));
                    }
                }

                @Override
                public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                    notifyListeners("onRewardedVideoLoadFailure", new JSObject().put("value", true));
                }

                @Override
                public void onRewardedVideoStarted(@NonNull String adUnitId) {
                    notifyListeners("onRewardedVideoStarted", new JSObject().put("value", true));
                }

                @Override
                public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                    notifyListeners("onRewardedVideoPlaybackError", new JSObject().put("value", true));
                }

                @Override
                public void onRewardedVideoClicked(@NonNull String adUnitId) {
                    notifyListeners("onRewardedVideoClicked", new JSObject().put("value", true));
                }

                @Override
                public void onRewardedVideoClosed(@NonNull String adUnitId) {
                    notifyListeners("onRewardedVideoClosed", new JSObject().put("value", true));
                }

                @Override
                public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
                    notifyListeners("onRewardedVideoCompleted", new JSObject().put("value", true));
                }
            });


            getBridge().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(adId)
                            .withLogLevel(MoPubLog.LogLevel.DEBUG).build();

                    com.mopub.common.MoPub.initializeSdk(getContext(), sdkConfiguration, new SdkInitializationListener() {
                        @Override
                        public void onInitializationFinished() {
                            Log.d("REWARD_VIDEO", "SDK Initialize");
                        }
                    });
                }
            });


        }catch (Exception ex){
            call.error(ex.getLocalizedMessage(), ex);
        }



    }


    @PluginMethod()
    public void showRewardVideo(PluginCall call) {
        try {
            if (isRewardVideoLoaded && MoPubRewardedVideos.hasRewardedVideo(rewardAdId)) {
                MoPubRewardedVideos.showRewardedVideo(rewardAdId);
                call.success(new JSObject().put("value", true));
            } else {
                call.error("RewardedVideos is not Prepare to show");
            }
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }
}
