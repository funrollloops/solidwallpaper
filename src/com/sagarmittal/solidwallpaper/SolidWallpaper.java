/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sagarmittal.solidwallpaper;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/*
 * This animated wallpaper draws a rotating wireframe shape. It is similar to
 * example #1, but has a choice of 2 shapes, which are user selectable and
 * defined in resources instead of in code.
 */

public class SolidWallpaper extends WallpaperService {
  public static final String SHARED_PREFS_NAME="solid-wallpaper-settings";

  @Override
  public Engine onCreateEngine() {
    return new SolidColorEngine();
  }

  class SolidColorEngine extends Engine 
  implements SharedPreferences.OnSharedPreferenceChangeListener {
    private int color;

    SolidColorEngine() {
      SharedPreferences preferences = SolidWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
      preferences.registerOnSharedPreferenceChangeListener(this);
      onSharedPreferenceChanged(preferences, null);
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
      String color_hex = prefs.getString("color_key", "0");
      this.color = 0xff000000;
      try {
        this.color = this.color | Integer.parseInt(color_hex, 16);
      } finally {
      }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
      if (visible) {
        redraw();
      }
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      super.onSurfaceChanged(holder, format, width, height);
      redraw();
    }

    void redraw() {
      final SurfaceHolder holder = getSurfaceHolder();

      Canvas c = null;
      try {
        c = holder.lockCanvas();
        if (c != null) {
          c.drawColor(this.color);
        }
      } finally {
        if (c != null) holder.unlockCanvasAndPost(c);
      }
    }
  }
}
