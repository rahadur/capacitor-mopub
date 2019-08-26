import {PluginListenerHandle} from '@capacitor/core';

declare global  {
  interface PluginRegistry {
    MoPub: MoPubPlugin;
  }
}

export interface MoPubPlugin {

  prepareBanner(options: AdOptions): Promise<{ value: boolean }>;

  // Show a banner Ad
  showBanner(): Promise<{ value: boolean }>;

  hideBanner(): Promise<{ value: boolean }>;

  resumeBanner(): Promise<{ value: boolean }>;

  // Destroy the banner, remove it from screen.
  removeBanner(): Promise<{ value: boolean }>;


  // Prepare interstitial banner
  prepareInterstitial(options: AdOptions): Promise<{ value: boolean }>;

  // Show interstitial ad when it’s ready
  showInterstitial(): Promise<{ value: boolean }>;


  // Prepare a reward video ad
  prepareRewardVideo(options: AdOptions): Promise<{ value: boolean }>;

  // Show a reward video ad
  showRewardVideo(): Promise<{ value: boolean }>;


  addListener(eventName: 'onAdLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onBannerFailed', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onBannerClicked', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onBannerExpanded', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onBannerCollapsed', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onBannerPrepared', listenerFunc: (info: any) => void): PluginListenerHandle;


  addListener(eventName: 'onInterstitialLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onInterstitialFailed', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onInterstitialShown', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onInterstitialClicked', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onInterstitialDismissed', listenerFunc: (info: any) => void): PluginListenerHandle;


  addListener(eventName: 'onRewardedVideoLoadSuccess', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoLoadFailure', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoStarted', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoPlaybackError', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoClicked', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoClosed', listenerFunc: (info: any) => void): PluginListenerHandle;
  addListener(eventName: 'onRewardedVideoCompleted', listenerFunc: (info: any) => void): PluginListenerHandle;
  
  
}



export interface AdOptions {
  adId: string;       // Banner ad ID (required)
  adSize?: AdSize;
  width?: number;
  height?: number;
  hasTabBar?: boolean;
  tabBarHeight?: number; // Height in Pixal
  isTesting?: boolean;
  autoShow?: boolean;
  autoRefresh?: boolean;
  userId?: string;
}


/*
* For more information
* Read: 
* https://developers.mopub.com/publishers/android/test/#option-1-test-using-a-guaranteed-line-item
* 
*/
export enum AdSize {

  MATCH_VIEW = 'MATCH_VIEW',

  HEIGHT_50 = 'HEIGHT_50',

  HEIGHT_90 = 'HEIGHT_90',

  HEIGHT_250 = 'HEIGHT_250',

  HEIGHT_280 = 'HEIGHT_280'
}