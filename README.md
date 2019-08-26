<p align="center">
  <img width="50%" height="auto" src="https://media.mopub.com/media/filer_public/95/19/95193506-e968-42fd-94ad-70d79940864b/mopub_logo.png">
</p>

# Capacitor MoPub 💸

Capacitor **MoPub** is a native Twitter MoPub implementation for IOS & Android. Now you can use this package as a Ionic Capacitor Plugin in your App.


## Release Note 0.1.0:
 **NOTE:** This is the Initial release of this plugins. You may find some issue in this version. Please feel free to create an Issue.


## Supported Platform
- [x] Android
- [] iOS
- [] Electron 

## Other Plugins:

| Plugins  | Android            | iOS           |  Electron     | PWA           |
| :------------- | :------------- | :------------- | :------------- | :------------- |
| [AdMob](https://github.com/rahadur/capacitor-admob)     | ✅ | ❌  | ❌ | ❌ |  


## MoPub Demo App
### Screenshot
| Basic Banner | TabBar Banner | Interstitial | RewardVideo |
| ------------- | ------------- | --------------------- | ---------------------
| ![Basic MoPub Banner](https://i.imgur.com/ddEHMSL.png)  | ![Basic AdMob Banner](https://i.imgur.com/pFr2754.png)  | ![Basic AdMob Banner](https://i.imgur.com/Gh4fkjd.png) | ![Basic AdMob Banner](https://i.imgur.com/Kf2Ljns.png) |

### Download
Download Demo App from **[Here](https://github.com/rahadur/capacitor-mopub/tree/master/demo)**
```console
cd demo

npm install

ionic build

npx cap copy

npx cap sync 

npx cap update

npx cap open android

============== Or just use this command ===========

npm install & ionic build & npx cap copy & npx cap sync & npx cap update & npx cap open android
``````

## Installation
Use **MoPub** plugins in your app.
```console
 npm install --save capacitor-mopub
 ```


 ### 📌 Register MoPub to Capacitor

Open your Ionic Capacitor App in Android Studio, Now open **MainActivity.java** of your app and Register **MoPub** to Capacitor Plugins.


```java
// Other imports...
import app.xplatform.capacitor.MoPub;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      
      add(MoPub.class);  // Add MoPub as a Capacitor Plugin

    }});
  }
}
```


## 📌 BANNER

### prepareBanner(options: AdOptions): Promise<{ value: boolean }>

```typescript
import { Plugins } from '@capacitor/core';
import { AdOptions, AdSize } from 'capacitor-mopub';

const { MoPub } = Plugins;

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.scss']
})
export class HomeComponent implements OnInit {


    constructor() {}

    ngOnInit() {
      // Show Banner Ad
      const options: AdOptions = {
        adId: 'b195f8dd8ded45fe847ad89ed1d016da',
        adSize: AdSize.MATCH_VIEW,
        autoShow: true,
        autoRefresh: true
      }


      // Register to an Event Listener
      MoPub.prepareBanner(options)
        .then(
          (value) => { console.log(value); }, // true
          (error) => { console.error(error); } // show error
        );
    }
}
```

### hideBanner(): Promise<{ value: boolean }>

```typescript
// Hide the banner, remove it from screen, but can show it later

MoPub.hideBanner()
  .then(
    (value) => { console.log(value); }, // true
    (error) => { console.error(error); } // show error
  );
```


### resumeBanner(): Promise<{ value: boolean }>

```typescript
// Resume the banner, show it after hide

MoPub.resumeBanner().then(
    (value) => { console.log(value); }, // true
    (error) => { console.error(error); } // show error
);
```

### removeBanner(): Promise<{ value: boolean }>

```typescript
// Destroy the banner, remove it from screen.

MoPub.removeBanner()
  .then(
    (value) => { console.log(value); }, // true
    (error) => { console.error(error); } // show error
  );
```

### Event Listener
This following Event Listener can be called in **Banner AD**.
```typescript
addListener(eventName: 'onAdLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onBannerFailed', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onBannerClicked', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onBannerExpanded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onBannerCollapsed', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onBannerPrepared', listenerFunc: (info: any) => void): PluginListenerHandle;
```



## 📌 INTERSTITIAL

### prepareInterstitial(options: AdOptions): Promise<{ value: boolean }>
```typescript
import { Plugins } from '@capacitor/core';
import { AdOptions, AdSize } from 'capacitor-mopub';

const { MoPub } = Plugins;

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.scss']
})
export class HomeComponent implements OnInit {


    constructor() {}

    ngOnInit() {
      // Interstitial Ad Optios
      const options: AdOptions = {
        adId: '24534e1901884e398f1253216226017e',
        autoShow: true,
        isTesting: true
      }

      // Prepare Interstial Ad
      MoPub.prepareInterstitial(options)
        .then(
          (value) => { console.log(value); }, // true
          (error) => { console.error(error); } // show error
        );
    }


    // Show Interstitial Ads
    showInterstitial() {
      
      MoPub.showInterstitial()
        .then(
          (value) => { console.log(value); }, // true
          (error) => { console.error(error); } // show error
        );
    }   
}
```

### showInterstitial(): Promise<{ value: boolean }>

```typescript
// Show interstitial ad when it’s ready

MoPub.showInterstitial()
  .then(
    (value) => { console.log(value); }, // true
    (error) => { console.error(error); } // show error 
  );
```


### Event Listener
This following Event Listener can be called in **Interstitial AD**
```typescript
addListener(eventName: 'onInterstitialLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialFailed', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialShown', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialClicked', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialDismissed', listenerFunc: (info: any) => void): PluginListenerHandle;
```


## 📌 RewardVideo

### prepareRewardVideo(options: AdOptions): Promise<{ value: boolean }>
```typescript
import { Plugins } from '@capacitor/core';
import { AdOptions, AdSize } from 'capacitor-mopub';

const { MoPub } = Plugins;

@Component({
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.scss']
})
export class HomeComponent implements OnInit {


    constructor() {}

    ngOnInit() {
      // RewardVideo Ad Optios
      const options: AdOptions = {
        adId: '920b6145fb1546cf8b5cf2ac34638bb7',
        autoShow: true,
        isTesting: true
      }

      // Prepare RewardVideo Ad
      MoPub.prepareRewardVideo(options)
        .then(
          (value) => { console.log(value); },
          (error) => { console.log(error); }
        );
    }


    // Show RewardVideo Ads
    showRewardVideo() {
      MoPub.showRewardVideo()
        .then(
          (value) => { console.log(value); }, // true
          (error) => { console.error(error); } // show error
        );
  }  
}
```

### showRewardVideoAd(): Promise<{ value: boolean }>

```typescript
// Show a RewardVideo AD

MoPub.showRewardVideo()
    .then(
      (value) => { console.log(value); }, // true
      (error) => { console.error(error); } // show error
    );
```

### Event Listener
This following Event Listener can be called in **RewardedVideo**
```typescript
addListener(eventName: 'onRewardedVideoLoadSuccess', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoLoadFailure', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoStarted', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoPlaybackError', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoClicked', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoClosed', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoCompleted', listenerFunc: (info: any) => void): PluginListenerHandle;
```

# API 

### 📌 AdOptions
```typescript
interface AdOptions {
  adId: string;       // Banner ad ID (required)
  adSize?: AdSize;
  hasTabBar?: boolean;
  tabBarHeight?: number; // Height in Pixal
  isTesting?: boolean;
  autoShow?: boolean;
  autoRefresh?: boolean;
  userId?: string;
}
```

### 📌 AdSize
```typescript
enum AdSize {

  MATCH_VIEW = 'MATCH_VIEW',

  HEIGHT_50 = 'HEIGHT_50',

  HEIGHT_90 = 'HEIGHT_90',

  HEIGHT_250 = 'HEIGHT_250',

  HEIGHT_280 = 'HEIGHT_280'
}
```

## Contributing

- 🌟 Star this repository
- 📋 Open issue for feature requests


## Roadmap
 - [Capacitor Plugins](https://capacitor.ionicframework.com/docs/plugins/)

 - [IOS](https://capacitor.ionicframework.com/docs/plugins/ios/)

 - [Android](https://capacitor.ionicframework.com/docs/plugins/android/)


## License

Capacitor AdMob is [MIT licensed](./LICENSE).