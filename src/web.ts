import { WebPlugin } from '@capacitor/core';
import { MoPubPlugin } from './definitions';

export class MoPubWeb extends WebPlugin implements MoPubPlugin {
  constructor() {
    super({
      name: 'MoPub',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const MoPub = new MoPubWeb();

export { MoPub };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(MoPub);
