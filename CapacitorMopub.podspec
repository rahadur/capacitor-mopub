
  Pod::Spec.new do |s|
    s.name = 'CapacitorMopub'
    s.version = '0.0.1'
    s.summary = 'This is Ionic Capacitor native MoPub plugin for IOS & Android'
    s.license = 'MIT'
    s.homepage = 'https://github.com/rahadur/capacitor-mopub'
    s.author = 'Rahadur Rahman <get.rahadur@gmail.com>'
    s.source = { :git => 'https://github.com/rahadur/capacitor-mopub', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end