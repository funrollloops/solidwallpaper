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

package com.example.android.livecubes.cube2;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/*
 * This animated wallpaper draws a rotating wireframe shape. It is similar to
 * example #1, but has a choice of 2 shapes, which are user selectable and
 * defined in resources instead of in code.
 */

public class CubeWallpaper2 extends WallpaperService {

  public static final String SHARED_PREFS_NAME="cube2settings";

  static class ThreeDPoint {
    float x;
    float y;
    float z;
  }

  static class ThreeDLine {
    int startPoint;
    int endPoint;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public Engine onCreateEngine() {
    return new CubeEngine();
  }

  class CubeEngine extends Engine 
  implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences mPrefs;
    private int color;

    CubeEngine() {
      mPrefs = CubeWallpaper2.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
      mPrefs.registerOnSharedPreferenceChangeListener(this);
      onSharedPreferenceChanged(mPrefs, null);
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
        drawFrame();
      }
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      super.onSurfaceChanged(holder, format, width, height);
      drawFrame();
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
      super.onSurfaceCreated(holder);
    }

    /*
     * Draw one frame of the animation. This method gets called repeatedly
     * by posting a delayed Runnable. You can do any drawing you want in
     * here. This example draws a wireframe cube.
     */
    void drawFrame() {
      final SurfaceHolder holder = getSurfaceHolder();

      Canvas c = null;
      try {
        c = holder.lockCanvas();
        if (c != null) {
          // draw something
          c.drawColor(this.color);
        }
      } finally {
        if (c != null) holder.unlockCanvasAndPost(c);
      }
    }
  }
}
